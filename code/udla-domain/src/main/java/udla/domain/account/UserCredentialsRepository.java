package udla.domain.account;

import java.util.Optional;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserCredentialsId;
import udla.common.data.shared.id.UserId;
import udla.domain.shared.IdBasedEntityRepository;

public interface UserCredentialsRepository
    extends IdBasedEntityRepository<UserCredentialsId, UserCredentials> {

  Optional<UserCredentials> findByUserId(UserId userId);

  Optional<UserCredentials> findByUserId(TenantId tenantId, UserId userId);

  Optional<UserCredentials> findByActivateToken(TenantId tenantId, String activateToken);

  Optional<UserCredentials> findByResetToken(TenantId tenantId, String resetToken);

  boolean removeByUserId(TenantId tenantId, UserId userId);
}
