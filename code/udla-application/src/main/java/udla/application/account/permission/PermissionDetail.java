package udla.application.account.permission;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import udla.domain.account.Permission;
import udla.domain.admin.Application;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionDetail extends Permission {

  private Application application;

  private String directory;

  public PermissionDetail(Permission permission, Application application, String directory) {
    super(permission.getTenantId(), permission.getName());
    BeanUtils.copyProperties(permission, this);
    this.application = application;
    this.directory = directory;
  }
}
