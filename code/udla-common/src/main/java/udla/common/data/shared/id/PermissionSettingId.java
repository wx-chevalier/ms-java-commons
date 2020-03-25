package udla.common.data.shared.id;

import udla.common.data.shared.EntityType;

public class PermissionSettingId extends BaseEntityId {

  public PermissionSettingId(Long id) {
    super(id);
  }

  @Override
  public EntityType getEntityType() {
    return EntityType.PERMISSION_SETTING;
  }
}
