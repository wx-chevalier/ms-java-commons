package udla.domain.admin;

import udla.common.data.shared.id.ApplicationId;
import udla.domain.shared.IdBasedEntityRepository;

public interface ApplicationRepository
    extends IdBasedEntityRepository<ApplicationId, Application> {}
