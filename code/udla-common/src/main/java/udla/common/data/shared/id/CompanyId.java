package udla.common.data.shared.id;

import udla.common.data.shared.EntityType;

public class CompanyId extends BaseEntityId {

  public CompanyId(Long id) {
    super(id);
  }

  @Override
  public EntityType getEntityType() {
    return EntityType.COMPANY;
  }
}
