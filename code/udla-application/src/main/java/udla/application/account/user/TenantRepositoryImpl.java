package udla.application.account.user;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import udla.common.data.shared.id.*;
import udla.domain.account.Tenant;
import udla.domain.account.TenantRepository;
import udla.infra.common.persistence.MyBatisIdBasedEntityRepository;
import udla.infra.tunnel.db.account.TenantDO;
import udla.infra.tunnel.db.account.TenantTunnel;
import udla.infra.tunnel.db.mapper.account.TenantMapper;

@Repository
public class TenantRepositoryImpl
    extends MyBatisIdBasedEntityRepository<TenantTunnel, TenantMapper, TenantDO, Tenant, TenantId>
    implements TenantRepository {

  @Getter(AccessLevel.PROTECTED)
  private TenantConverter converter;

  public TenantRepositoryImpl(
      TenantTunnel tenantTunnel, TenantMapper mapper, TenantConverter converter) {
    super(tenantTunnel, mapper);
    this.converter = converter;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Tenant> findAll() {
    return getTunnel().getAllTenant().stream()
        .map(converter::convertFrom)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public boolean exists(String name) {
    return getTunnel().getByName(name) != null;
  }
}
