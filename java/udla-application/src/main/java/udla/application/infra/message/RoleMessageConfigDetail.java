package udla.application.infra.message;

import java.util.Collection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import udla.common.data.infra.notice.NoticeSendChannel;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.RoleMessageConfigId;
import udla.domain.infra.message.MessageType;
import udla.domain.shared.IdBasedEntity;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleMessageConfigDetail
    extends IdBasedEntity<RoleMessageConfigId, RoleMessageConfigDetail> {

  private Collection<MessageType> messageTypes;

  private Collection<NoticeSendChannel> sendChannels;

  private Integer sendInterval;

  public RoleMessageConfigDetail(
      TenantId tenantId,
      Collection<MessageType> messageTypes,
      Collection<NoticeSendChannel> sendChannels,
      Integer sendInterval) {
    super(tenantId);
    this.messageTypes = messageTypes;
    this.sendChannels = sendChannels;
    this.sendInterval = sendInterval;
  }
}
