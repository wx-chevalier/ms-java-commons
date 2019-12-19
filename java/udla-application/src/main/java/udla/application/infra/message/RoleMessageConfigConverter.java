package udla.application.infra.message;

import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.common.data.shared.id.BaseEntityId;
import udla.domain.infra.message.RoleMessageConfig;
import udla.infra.tunnel.db.infra.message.RoleMessageConfigDO;

@Component
public class RoleMessageConfigConverter
    extends AbstractConverter<RoleMessageConfig, RoleMessageConfigDO> {

  @Override
  public RoleMessageConfigDO convertTo(RoleMessageConfig config) {
    return new RoleMessageConfigDO()
        .setRoleId(convertNullable(config.getRoleId(), BaseEntityId::getId))
        .setCreatorId(convertNullable(config.getCreatorId(), BaseEntityId::getId));
  }

  @Override
  public RoleMessageConfig convertFrom(RoleMessageConfigDO roleMessageConfigDO) {
    return super.convertFrom(roleMessageConfigDO);
  }
}
