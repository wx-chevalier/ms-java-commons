package udla.domain.task;

import java.util.Collection;
import udla.common.data.shared.id.TaskId;
import udla.domain.shared.IdBasedEntityRepository;

public interface TaskRepository<T extends AbstractTask<T>>
    extends IdBasedEntityRepository<TaskId, T> {

  Collection<T> findByType(TaskType taskType);
}
