package udla.infra.tunnel.db.infra.tag;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import udla.common.data.shared.EntityType;
import udla.infra.tunnel.db.shared.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("infra_tag_entity_relation")
public class TagEntityRelationDO extends BaseDO<TagEntityRelationDO> {

  private Long tagId;

  private Long entityId;

  private EntityType entityType;

  private Long creatorId;
}
