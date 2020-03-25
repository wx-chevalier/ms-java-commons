package udla.application.infra.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.id.BaseEntityId;
import udla.infra.tunnel.db.infra.message.MessageNoticeTunnel;

@Slf4j
@Service
public class MessageNoticeQueryServiceImpl implements MessageNoticeQueryService {

  private MessageNoticeTunnel messageNoticeTunnel;

  public MessageNoticeQueryServiceImpl(MessageNoticeTunnel messageNoticeTunnel) {
    this.messageNoticeTunnel = messageNoticeTunnel;
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isSendMsgAboutWorkOrder(BaseEntityId entityId, NoticeType noticeType) {
    int count = messageNoticeTunnel.count(entityId, noticeType);
    return count != 0;
  }
}
