package udla.application.infra.message;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.MessageTypeId;
import udla.domain.admin.Application;
import udla.domain.admin.ApplicationRepository;
import udla.domain.infra.message.MessageType;
import udla.domain.infra.message.MessageTypeRepository;
import udla.infra.common.exception.NotFoundException;
import udla.infra.common.persistence.MyBatisIdBasedEntityRepository;
import udla.infra.tunnel.db.infra.message.MessageTypeDO;
import udla.infra.tunnel.db.infra.message.MessageTypeTunnel;
import udla.infra.tunnel.db.mapper.infra.message.MessageTypeMapper;

@Slf4j
@Service
public class MessageTypeRepositoryImpl
    extends MyBatisIdBasedEntityRepository<
        MessageTypeTunnel, MessageTypeMapper, MessageTypeDO, MessageType, MessageTypeId>
    implements MessageTypeRepository {

  @Getter(AccessLevel.PROTECTED)
  private MessageTypeConverter converter;

  private ApplicationRepository applicationRepository;

  public MessageTypeRepositoryImpl(
      MessageTypeTunnel messageTypeTunnel,
      MessageTypeMapper mapper,
      MessageTypeConverter converter,
      ApplicationRepository applicationRepository) {
    super(messageTypeTunnel, mapper);
    this.converter = converter;
    this.applicationRepository = applicationRepository;
  }

  @Override
  public Optional<MessageType> getByNoticeType(NoticeType type) {

    MessageTypeDO messageTypeDO = this.getTunnel().getByNoticeType(type);
    if (messageTypeDO == null) {
      throw new NotFoundException("查询错误,消息类型不存在");
    }

    // 查询应用信息
    Application app = null;

    if (messageTypeDO.getAppId() != null) {
      app =
          applicationRepository
              .findById(TenantId.NULL_TENANT_ID, new ApplicationId(messageTypeDO.getAppId()))
              .orElseThrow(() -> new NotFoundException("应用信息不存在"));
    }

    Application finalApp = app;
    return Optional.of(messageTypeDO)
        .map(converter::convertFrom)
        .map(messageType -> messageType.setApp(finalApp));
  }
}
