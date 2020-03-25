package udla.application.account.accesscontrol;

import java.util.List;
import udla.application.account.role.RoleDetail;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserId;
import udla.domain.account.Permission;
import udla.domain.account.Role;

public interface AccessControlQueryService {

  List<RoleDetail> findRoles(TenantId tenantId);

  List<Role> findUserRoles(TenantId tenantId, UserId userId);

  /**
   * 获取用户权限
   *
   * <ul>
   *   <li>用户的角色权限
   *   <li>对用户直接设定的权限，可能禁用掉来自用户角色的权限
   * </ul>
   */
  List<Permission> findUserPermissions(TenantId tenantId, UserId userId);

  List<Permission> findRolePermissions(TenantId tenantId, Role role);
}
