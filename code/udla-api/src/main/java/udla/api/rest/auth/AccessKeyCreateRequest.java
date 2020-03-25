package udla.api.rest.auth;

import lombok.Data;
import udla.common.data.shared.EntityType;

@Data
public class AccessKeyCreateRequest {

  private String entityId;

  private EntityType entityType;
}
