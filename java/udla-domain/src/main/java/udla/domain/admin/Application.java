package udla.domain.admin;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import udla.common.data.shared.HasName;
import udla.common.data.shared.id.*;
import udla.domain.shared.IdBasedEntity;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends IdBasedEntity<ApplicationId, Application> implements HasName {

  private String name;

  public Application(
      ApplicationId id,
      TenantId tenantId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      String name) {
    super(id, tenantId, createdAt, updatedAt);
    this.name = name;
  }

  public Application(ApplicationId id, TenantId tenantId, String name) {
    super(id, tenantId);
    this.name = name;
  }
}
