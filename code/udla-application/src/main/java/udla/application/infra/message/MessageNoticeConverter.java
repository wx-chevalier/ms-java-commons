package udla.application.infra.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.common.data.shared.id.ApplicationId;
import udla.common.data.shared.id.BaseEntityId;
import udla.common.data.shared.id.UserId;
import udla.domain.infra.message.MessageNotice;
import udla.infra.tunnel.db.infra.message.MessageNoticeDO;

@Slf4j
@Component
public class MessageNoticeConverter extends AbstractConverter<MessageNotice, MessageNoticeDO> {

  @Override
  public MessageNotice convertFrom(MessageNoticeDO messageNoticeDO) {
    return new MessageNotice(
        messageNoticeDO.getKind(),
        messageNoticeDO.getType(),
        messageNoticeDO.getSendChannel(),
        convertNullable(messageNoticeDO.getAppId(), ApplicationId::new),
        messageNoticeDO.getContent(),
        messageNoticeDO.getTitle(),
        messageNoticeDO.getIsRead(),
        convertNullable(messageNoticeDO.getEntityId(), String::valueOf),
        messageNoticeDO.getEntityType(),
        convertNullable(messageNoticeDO.getUserId(), UserId::new));
  }

  @Override
  public MessageNoticeDO convertTo(MessageNotice messageNotice) {
    return new MessageNoticeDO()
        .setAppId(convertNullable(messageNotice.getApp(), BaseEntityId::getId))
        .setKind(messageNotice.getKind())
        .setSendChannel(messageNotice.getChannel())
        .setContent(messageNotice.getContent())
        .setEntityId(convertNullable(messageNotice.getEntityId(), Long::valueOf))
        .setEntityType(messageNotice.getEntityType())
        .setTitle(messageNotice.getTitle())
        .setType(messageNotice.getType())
        .setUserId(convertNullable(messageNotice.getUserId(), BaseEntityId::getId));
  }
}
