package udla.application.account.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import udla.domain.account.Tenant;
import udla.domain.account.User;
import udla.domain.infra.area.Area;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantDetail extends Tenant {

  private User tenantAdminUser;

  private Area area;

  private Integer utkPrinterCount;

  public TenantDetail(Tenant tenant, User tenantAdminUser, Area area, Integer utkPrinterCount) {
    BeanUtils.copyProperties(tenant, this);
    this.tenantAdminUser = tenantAdminUser;
    this.area = area;
    this.utkPrinterCount = utkPrinterCount;
  }
}
