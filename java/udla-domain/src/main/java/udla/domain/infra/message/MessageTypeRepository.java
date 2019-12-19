package udla.domain.infra.message;

import java.util.Optional;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.id.MessageTypeId;
import udla.domain.shared.IdBasedEntityRepository;

public interface MessageTypeRepository extends IdBasedEntityRepository<MessageTypeId, MessageType> {

  Optional<MessageType> getByNoticeType(NoticeType type);
}
