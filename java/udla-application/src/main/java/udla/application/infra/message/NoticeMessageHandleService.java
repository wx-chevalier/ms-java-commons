package udla.application.infra.message;

import java.util.Map;
import javax.validation.constraints.NotNull;
import udla.common.data.infra.notice.NoticeSendChannel;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.mq.notice.SendNoticeMessage;
import udla.common.data.shared.id.BaseEntityId;
import udla.common.data.shared.id.UserId;

public interface NoticeMessageHandleService {

  void handle(@NotNull SendNoticeMessage msg);

  void send(
      NoticeSendChannel channel,
      UserId userId,
      NoticeType type,
      BaseEntityId entityId,
      Map<String, String> param);

  void send(
      NoticeSendChannel channel,
      String dest,
      NoticeType type,
      BaseEntityId entityId,
      Map<String, String> param);
}
