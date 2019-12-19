package udla.application.account.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import udla.common.data.page.PageNumBasedPageLink;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.RoleId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {
  private TenantId tenantId;

  private String searchText;

  private RoleId roleId;

  private PageNumBasedPageLink pageLink;
}
