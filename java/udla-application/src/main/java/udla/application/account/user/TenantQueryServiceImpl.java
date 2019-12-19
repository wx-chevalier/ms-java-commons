package udla.application.account.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.HashSet;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udla.application.page.PageNumberLinkConverter;
import udla.common.data.account.Authority;
import udla.common.data.code.ApiErrorCode;
import udla.common.data.page.PageNumBasedPageLink;
import udla.common.data.shared.id.CompanyId;
import udla.common.data.shared.id.TenantId;
import udla.common.data.shared.id.UserId;
import udla.domain.account.*;
import udla.domain.event.EventBus;
import udla.domain.event.message.admin.tenant.TenantEvent;
import udla.domain.infra.area.Area;
import udla.domain.infra.area.AreaRepository;
import udla.domain.shared.DomainService;
import udla.infra.common.exception.NotAcceptException;
import udla.infra.converter.PageConverter;
import udla.infra.tunnel.db.account.TenantDO;
import udla.infra.tunnel.db.account.TenantTunnel;

@Slf4j
@Service
public class TenantQueryServiceImpl implements TenantQueryService, DomainService {

  private TenantTunnel tenantTunnel;

  private TenantRepository tenantRepository;

  private UserRepository userRepository;

  private UserCommandService userCommandService;

  private TenantConverter tenantConverter;

  private AreaRepository areaRepository;

  private PageNumberLinkConverter pageNumberLinkConverter;

  @Getter private EventBus eventBus;

  public TenantQueryServiceImpl(
      EventBus eventBus,
      TenantTunnel tenantTunnel,
      TenantRepository tenantRepository,
      UserRepository userRepository,
      UserCommandService userCommandService,
      TenantConverter tenantConverter,
      AreaRepository areaRepository,
      PageNumberLinkConverter pageNumberLinkConverter) {
    this.eventBus = eventBus;
    this.tenantTunnel = tenantTunnel;
    this.tenantRepository = tenantRepository;
    this.userRepository = userRepository;
    this.userCommandService = userCommandService;
    this.tenantConverter = tenantConverter;
    this.areaRepository = areaRepository;
    this.pageNumberLinkConverter = pageNumberLinkConverter;
  }

  @Override
  @Transactional
  public Page<TenantDetail> findAll(PageNumBasedPageLink link, String searchText, String areaCode) {

    Pageable pageable = pageNumberLinkConverter.convertTo(link);

    IPage<TenantDO> allTenant = tenantTunnel.getAllTenant(pageable, searchText, areaCode);

    return PageConverter.toPage(allTenant, pageable)
        .map(tenantConverter::convertFrom)
        .map(this::find);
  }

  private TenantDetail find(Tenant tenant) {
    // 获取租户管理员用户
    Optional<User> user = userRepository.findAdmin(tenant.getId());

    // 获取区域
    Optional<Area> area = areaRepository.findByCode(tenant.getAreaCode());

    return new TenantDetail(tenant, user.orElse(null), area.orElse(null), 0);
  }

  @Override
  public Optional<TenantDetail> findById(TenantId tenantId) {
    return tenantRepository.findById(tenantId).map(this::find);
  }

  @Override
  @Transactional
  public Optional<TenantDetail> save(
      Tenant tenant, UserId currentUserId, String username, String password) {
    boolean exists = tenantRepository.exists(tenant.getName());
    if (exists) {
      throw new NotAcceptException("创建租户失败，租户用户名已被占用", ApiErrorCode.RESOURCE_EXIST);
    }
    tenant.setCompanyId(new CompanyId(1L));
    Tenant newTenant = tenantRepository.save(tenant);

    exists = userRepository.exists(username);
    if (exists) {
      throw new NotAcceptException("创建租户管理员失败,管理员名称已被占用", ApiErrorCode.RESOURCE_EXIST);
    }

    User user = new User(username, Authority.TENANT_ADMIN, username, currentUserId);
    TenantId tenantId = newTenant.getId();
    user.setTenantId(tenantId);
    userCommandService.addUser(tenantId, currentUserId, new HashSet<>(0), user, password);

    getEventBus().send(TenantEvent.createEvent(tenantId, tenant.getName(), user.getId()));

    return this.findById(tenantId);
  }
}
