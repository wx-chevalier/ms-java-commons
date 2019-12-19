package udla.api.rest.common.message.converter;

import java.util.List;
import java.util.stream.Collectors;
import udla.api.rest.common.message.dto.RoleMessageConfigUpdateReq;
import udla.common.data.shared.id.MessageTypeId;
import udla.domain.infra.message.RoleMessageConfig;

public class RoleMessageConfigConverter {

  public static RoleMessageConfig toRoleMessageConfig(RoleMessageConfigUpdateReq req) {
    List<MessageTypeId> messageTypeIds =
        req.getMessageTypeIds().stream().map(MessageTypeId::new).collect(Collectors.toList());
    return new RoleMessageConfig(messageTypeIds, req.getSendChannelList(), req.getInterval());
  }
}
