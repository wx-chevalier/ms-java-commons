package udla.api.config.security.model;

import lombok.Data;
import udla.common.data.account.Authority;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.EntityId;
import udla.common.data.shared.id.UserId;

@Data
public class SecurityUser {
  private UserId id;

  private TenantId tenantId;

  private Authority authority;

  private String username;

  // access key
  private String accessKey;

  private EntityId entityId;
}
