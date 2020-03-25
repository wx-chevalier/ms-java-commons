package udla.domain.infra.message;

import udla.common.data.infra.notice.NoticeSendChannel;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.id.BaseEntityId;
import udla.common.data.shared.id.MessageNoticeId;
import udla.common.data.shared.id.UserId;
import udla.domain.shared.IdBasedEntityRepository;

public interface MessageNoticeRepository
    extends IdBasedEntityRepository<MessageNoticeId, MessageNotice> {

  /** 检查消息是够已存在 */
  boolean exists(
      NoticeType noticeType, NoticeSendChannel channel, BaseEntityId entityId, UserId userId);
}
