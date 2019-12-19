package udla.domain.infra.area;

import java.util.Optional;
import udla.common.data.shared.id.AreaId;
import udla.domain.shared.IdBasedEntityRepository;

public interface AreaRepository extends IdBasedEntityRepository<AreaId, Area> {

  Optional<Area> findByCode(String code);
}
