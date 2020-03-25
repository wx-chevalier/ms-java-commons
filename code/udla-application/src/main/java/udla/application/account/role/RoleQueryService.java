package udla.application.account.role;

import io.reactivex.rxjava3.core.Single;
import java.util.List;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserId;
import udla.domain.account.Role;

public interface RoleQueryService {

  /** 通过用户ID查询角色列表 */
  Single<List<Role>> findRoleByUserId(TenantId tenantId, UserId userId);
}
