package udla.common.data.shared.id;

import udla.common.data.shared.EntityType;

public class UserCredentialsId extends BaseEntityId {

  public UserCredentialsId(Long id) {
    super(id);
  }

  @Override
  public EntityType getEntityType() {
    return EntityType.USER_CREDENTIALS;
  }
}
