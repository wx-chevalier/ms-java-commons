package udla.application.admin;

import java.util.List;
import udla.common.data.shared.id.*;
import udla.domain.admin.Application;

public interface ApplicationQueryService {

  /** 获取指定租户的可用的应用列表 */
  List<Application> getByTenantId(TenantId tenantId);
}
