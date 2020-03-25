package udla.common.data.shared.id;

import udla.common.data.shared.EntityType;

public class VerificationCodeId extends BaseEntityId {

  public VerificationCodeId(Long id) {
    super(id);
  }

  @Override
  public EntityType getEntityType() {
    return EntityType.VERIFICATION_CODE;
  }
}
