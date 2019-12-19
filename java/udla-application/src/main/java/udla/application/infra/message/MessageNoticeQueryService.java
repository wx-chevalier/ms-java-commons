package udla.application.infra.message;

import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.id.BaseEntityId;

/** 消息通知服务 */
public interface MessageNoticeQueryService {

  boolean isSendMsgAboutWorkOrder(BaseEntityId baseEntityId, NoticeType noticeType);
}
