package udla.application.admin;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.PermissionSettingId;
import udla.domain.admin.PermissionSetting;
import udla.domain.admin.PermissionSettingRepository;
import udla.infra.common.persistence.MyBatisIdBasedEntityRepository;
import udla.infra.tunnel.db.admin.PermissionSettingDO;
import udla.infra.tunnel.db.admin.PermissionSettingTunnel;
import udla.infra.tunnel.db.mapper.admin.PermissionSettingMapper;

@Repository
public class PermissionSettingRepositoryImpl
    extends MyBatisIdBasedEntityRepository<
        PermissionSettingTunnel,
        PermissionSettingMapper,
        PermissionSettingDO,
        PermissionSetting,
        PermissionSettingId>
    implements PermissionSettingRepository {

  @Getter(AccessLevel.PROTECTED)
  private PermissionSettingConverter converter;

  public PermissionSettingRepositoryImpl(
      PermissionSettingTunnel permissionSettingTunnel,
      PermissionSettingMapper mapper,
      PermissionSettingConverter converter) {
    super(permissionSettingTunnel, mapper);
    this.converter = converter;
  }

  @Override
  public List<PermissionSetting> findByAppId(TenantId tenantId, ApplicationId appId) {
    List<PermissionSettingDO> permissionSettingDOS = getTunnel().list(tenantId, appId);
    return permissionSettingDOS.stream().map(converter::convertFrom).collect(Collectors.toList());
  }
}
