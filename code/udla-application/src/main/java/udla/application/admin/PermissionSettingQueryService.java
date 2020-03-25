package udla.application.admin;

import java.util.List;
import udla.common.data.shared.id.*;

public interface PermissionSettingQueryService {

  /** 查询某个租户的某个应用的权限详情信息 */
  List<PermissionSettingDetail> findByAppId(TenantId tenantId, ApplicationId appId);
}
