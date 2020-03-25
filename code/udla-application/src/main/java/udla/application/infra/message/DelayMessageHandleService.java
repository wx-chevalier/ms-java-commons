package udla.application.infra.message;

import javax.validation.constraints.NotNull;
import udla.common.data.mq.notice.DelaySendNoticeMessage;

public interface DelayMessageHandleService {

  /** 处理延时消息 */
  void handle(@NotNull DelaySendNoticeMessage msg);
}
