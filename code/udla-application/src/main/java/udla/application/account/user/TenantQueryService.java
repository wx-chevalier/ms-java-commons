package udla.application.account.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import udla.common.data.page.PageNumBasedPageLink;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserId;
import udla.domain.account.Tenant;

public interface TenantQueryService {

  Page<TenantDetail> findAll(PageNumBasedPageLink link, String searchText, String location);

  Optional<TenantDetail> findById(TenantId tenantId);

  Optional<TenantDetail> save(
      Tenant tenant, UserId currentUserId, String username, String password);
}
