package udla.infra.tunnel.db.task;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import udla.common.data.common.process.TaskStatus;
import udla.domain.task.TaskType;
import udla.infra.tunnel.db.shared.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("infra_task")
@NoArgsConstructor
@AllArgsConstructor
public class TaskDO extends BaseDO<TaskDO> {

  private Instant willAbortAt;

  private Instant startAt;

  private short percent;

  private TaskStatus status;

  private TaskType type;

  private String extraInfo;
}
