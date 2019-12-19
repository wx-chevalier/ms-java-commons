package udla.common.data.shared.id;

import udla.common.data.shared.EntityType;

public class AreaId extends BaseEntityId {

  private static final long serialVersionUID = -7698487243689876701L;

  public AreaId(Long id) {
    super(id);
  }

  @Override
  public EntityType getEntityType() {
    return EntityType.AREA;
  }
}
