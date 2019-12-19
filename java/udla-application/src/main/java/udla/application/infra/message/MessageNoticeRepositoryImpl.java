package udla.application.infra.message;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udla.common.data.infra.notice.NoticeSendChannel;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.EntityType;
import udla.common.data.shared.id.BaseEntityId;
import udla.common.data.shared.id.MessageNoticeId;
import udla.common.data.shared.id.UserId;
import udla.domain.admin.ApplicationRepository;
import udla.domain.infra.message.MessageNotice;
import udla.domain.infra.message.MessageNoticeRepository;
import udla.infra.common.persistence.MyBatisIdBasedEntityRepository;
import udla.infra.tunnel.db.infra.message.MessageNoticeDO;
import udla.infra.tunnel.db.infra.message.MessageNoticeTunnel;
import udla.infra.tunnel.db.mapper.infra.message.MessageNoticeMapper;
import udla.infra.tunnel.db.shared.BaseDO;

@Slf4j
@Service
public class MessageNoticeRepositoryImpl
    extends MyBatisIdBasedEntityRepository<
        MessageNoticeTunnel, MessageNoticeMapper, MessageNoticeDO, MessageNotice, MessageNoticeId>
    implements MessageNoticeRepository {

  @Getter(AccessLevel.PROTECTED)
  private MessageNoticeConverter converter;

  public MessageNoticeRepositoryImpl(
      MessageNoticeTunnel messageTypeTunnel,
      MessageNoticeMapper mapper,
      MessageNoticeConverter converter,
      ApplicationRepository applicationRepository) {
    super(messageTypeTunnel, mapper);
    this.converter = converter;
  }

  @Override
  @Transactional
  public boolean exists(
      NoticeType noticeType, NoticeSendChannel channel, BaseEntityId entityId, UserId userId) {

    Long entityLongId = Optional.ofNullable(entityId).map(BaseEntityId::getId).orElse(null);
    EntityType entityType =
        Optional.ofNullable(entityId).map(BaseEntityId::getEntityType).orElse(null);

    Wrapper<MessageNoticeDO> countWrapper =
        new LambdaQueryWrapper<MessageNoticeDO>()
            .eq(MessageNoticeDO::getType, noticeType)
            .eq(MessageNoticeDO::getSendChannel, channel)
            .eq(Objects.nonNull(entityLongId), MessageNoticeDO::getEntityId, entityLongId)
            .eq(Objects.nonNull(entityType), MessageNoticeDO::getEntityType, entityType)
            .eq(MessageNoticeDO::getUserId, userId.getId())
            .ge(BaseDO::getCreatedAt, LocalDateTime.now().plusHours(-1));
    return getTunnel().count(countWrapper) != 0;
  }
}
