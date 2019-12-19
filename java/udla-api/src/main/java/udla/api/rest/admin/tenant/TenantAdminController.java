package udla.api.rest.admin.tenant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import udla.api.rest.shared.BaseController;
import udla.api.rest.shared.dto.envelope.ResponseEnvelope;
import udla.application.admin.PermissionSettingDetail;
import udla.common.data.shared.id.*;
import udla.domain.admin.Application;

@Api(tags = "租户系统管理")
@RestController("/admin/tenant")
public class TenantAdminController extends BaseController {

  @GetMapping("/wx_application")
  @ApiOperation("获取租户下所有的应用列表")
  public ResponseEnvelope<List<Application>> getTenantUfcApplications() {
    return ResponseEnvelope.createOk(applicationQueryService.getByTenantId(getTenantId()));
  }

  @GetMapping("/permission_setting")
  @ApiOperation("获取租户下所有的权限列表")
  public ResponseEnvelope<List<PermissionSettingDetail>> getTenantPermissionSetting(String appId) {
    List<PermissionSettingDetail> permissionSettingDetails =
        permissionSettingQueryService.findByAppId(getTenantId(), ApplicationId.create(appId));
    return ResponseEnvelope.createOk(permissionSettingDetails);
  }
}
