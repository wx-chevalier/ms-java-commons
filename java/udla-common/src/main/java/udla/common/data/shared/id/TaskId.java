package udla.common.data.shared.id;

import udla.common.data.shared.EntityType;

public class TaskId extends BaseEntityId {

  public TaskId(Long id) {
    super(id);
  }

  @Override
  public EntityType getEntityType() {
    return EntityType.TASK;
  }
}
