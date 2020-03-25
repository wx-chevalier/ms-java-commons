package udla.application.account.permission;

import org.springframework.stereotype.Component;
import udla.common.data.account.PermissionStatusEnum;
import udla.common.data.common.AbstractConverter;
import udla.common.data.shared.id.*;
import udla.domain.account.Permission;
import udla.infra.tunnel.db.account.PermissionDO;

@Component
public class PermissionConverter extends AbstractConverter<Permission, PermissionDO> {

  @Override
  public PermissionDO convertTo(Permission permission) {
    return new PermissionDO()
        .setName(permission.getName())
        .setTenantId(permission.getTenantId().getId())
        .setNickname(permission.getNickname())
        .setStatus(
            permission.isDisabled() ? PermissionStatusEnum.DISABLE : PermissionStatusEnum.ENABLE);
  }

  @Override
  public Permission convertFrom(PermissionDO permissionDO) {
    return new Permission(
        new TenantId(permissionDO.getTenantId()),
        permissionDO.getNickname(),
        permissionDO.getName(),
        permissionDO.getStatus() == PermissionStatusEnum.DISABLE);
  }
}
