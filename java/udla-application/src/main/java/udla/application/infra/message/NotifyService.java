package udla.application.infra.message;

import static udla.common.data.infra.notice.NoticeSendChannel.SITE;
import static udla.common.data.infra.notice.NoticeSendChannel.WECHAT;
import static udla.common.data.mq.MessageQueueName.SEND_MESSAGE;

import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import udla.common.data.infra.message.MessageTemplate;
import udla.common.data.infra.notice.NoticeSendChannel;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.mq.notice.SendUserMessage;
import udla.common.data.shared.id.BaseEntityId;
import udla.common.data.shared.id.MessageTypeId;
import udla.common.data.shared.id.TenantId;
import udla.common.data.shared.id.UserId;
import udla.domain.account.User;
import udla.domain.account.UserRepository;
import udla.domain.admin.Application;
import udla.domain.infra.message.MessageNotice;
import udla.domain.infra.message.MessageNoticeRepository;
import udla.domain.infra.message.MessageType;
import udla.domain.infra.message.MessageTypeRepository;
import udla.infra.common.exception.NotFoundException;
import udla.infra.common.util.StringUtil;

@Slf4j
@Service
public class NotifyService {

  @Autowired private UserRepository userRepository;

  @Autowired private AmqpTemplate amqpTemplate;

  @Autowired private MessageNoticeRepository messageNoticeRepository;

  @Autowired private MessageTypeRepository messageTypeRepository;

  protected Set<UserId> findUserIdsByTenant(TenantId tenantId) {
    List<User> users = userRepository.find(tenantId);
    if (CollectionUtils.isEmpty(users)) {
      return new HashSet<>(0);
    }
    return users.stream().map(User::getId).collect(Collectors.toSet());
  }

  protected void sendMsgWithAllChannel(
      Set<UserId> userIds, Map<String, String> param, NoticeType type) {
    userIds.stream()
        .filter(Objects::nonNull)
        .forEach(userId -> sendMsgWithAllChannel(userId, null, param, type));
  }

  protected void sendMsgWithAllChannel(
      Set<UserId> userIds, BaseEntityId entityId, Map<String, String> param, NoticeType type) {
    userIds.stream()
        .filter(Objects::nonNull)
        .forEach(userId -> sendMsgWithAllChannel(userId, entityId, param, type));
  }

  /** 通过所有的渠道发送消息 */
  private void sendMsgWithAllChannel(
      UserId userId, BaseEntityId entityId, Map<String, String> param, NoticeType type) {
    SendUserMessage msg = new SendUserMessage();
    for (NoticeSendChannel channel : NoticeSendChannel.supportSendChannel()) {
      msg.setUserId(userId)
          .setNoticeType(type)
          .setParam(param)
          .setChannel(channel)
          .setBaseEntityId(entityId);
      boolean exist = messageNoticeExist(type, channel, entityId, userId);
      if (exist) {
        return;
      }

      if (msg.getChannel() != WECHAT) {
        saveMessage(type, channel, param, entityId, userId);
      }
      if (msg.getChannel() != SITE) {
        amqpTemplate.convertAndSend(SEND_MESSAGE, msg);
      }
    }
  }

  private void saveMessage(
      NoticeType noticeType,
      NoticeSendChannel channel,
      Map<String, String> param,
      BaseEntityId entityId,
      UserId userId) {

    MessageType messageType =
        messageTypeRepository
            .getByNoticeType(noticeType)
            .orElseThrow(
                () -> new NotFoundException(String.format("消息类型type=[%s]不存在", noticeType)));

    Application app = messageType.getApp();
    String siteMessageContent;
    if (channel == SITE) {
      String siteMessageTemplate =
          Optional.of(messageType)
              .map(MessageType::getTemplate)
              .map(MessageTemplate::getSiteMessageTemplate)
              .orElseThrow(() -> new NotFoundException("站内信模板不存在"));

      siteMessageContent = StringUtil.replaceAll(siteMessageTemplate, param);
    } else {
      siteMessageContent = param.toString();
    }

    // 持久化消息发送记录
    MessageNotice notice =
        new MessageNotice(
            messageType.getKind(),
            noticeType,
            channel,
            Optional.ofNullable(app).map(Application::getId).orElse(null),
            siteMessageContent,
            noticeType.getDesc(),
            entityId == null ? null : entityId.getId().toString(),
            entityId == null ? null : entityId.getEntityType(),
            userId);
    messageNoticeRepository.save(TenantId.NULL_TENANT_ID, notice);
  }

  /** 判断同等类型，同等渠道，同等关联资源的发给同一用户的消息是否存在 */
  private boolean messageNoticeExist(
      NoticeType noticeType, NoticeSendChannel channel, BaseEntityId entityId, UserId userId) {
    return messageNoticeRepository.exists(noticeType, channel, entityId, userId);
  }

  /** 发送自定义消息 */
  @Transactional
  public void sendCustomerMessage(
      MessageTypeId messageTypeId,
      Set<UserId> userIds,
      Set<NoticeSendChannel> channels,
      Map<String, String> param) {
    Optional<MessageType> messageType =
        messageTypeRepository.findById(TenantId.NULL_TENANT_ID, messageTypeId);
    if (!messageType.isPresent()) {
      throw new NotFoundException("发送消息失败，消息类型不存在");
    }
    NoticeType noticeType = messageType.map(MessageType::getKey).orElse(null);

    SendUserMessage msg = new SendUserMessage();
    for (UserId userId : userIds) {
      for (NoticeSendChannel channel : channels) {
        msg.setUserId(userId).setNoticeType(noticeType).setParam(param).setChannel(channel);
        boolean exist = messageNoticeExist(noticeType, channel, null, userId);
        if (exist) {
          return;
        }
        if (msg.getChannel() != SITE) {
          amqpTemplate.convertAndSend(SEND_MESSAGE, msg);
        }
        saveMessage(noticeType, channel, param, null, userId);
      }
    }
  }
}
