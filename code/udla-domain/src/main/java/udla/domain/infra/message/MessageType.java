package udla.domain.infra.message;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import udla.common.data.infra.message.MessageTemplate;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.infra.notice.NoticeTypeKind;
import udla.common.data.shared.HasName;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.MessageTypeId;
import udla.domain.admin.Application;
import udla.domain.shared.IdBasedEntity;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageType extends IdBasedEntity<MessageTypeId, MessageType> implements HasName {

  private NoticeType key;

  private String name;

  private NoticeTypeKind kind;

  private Application app;

  private MessageTemplate template;

  public MessageType(
      MessageTypeId id,
      NoticeType key,
      TenantId tenantId,
      Application app,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      String name,
      String kind,
      MessageTemplate template) {
    super(id, tenantId, createdAt, updatedAt);
    this.name = name;
    this.key = key;
    this.kind = NoticeTypeKind.valueOf(kind);
    this.app = app;
    this.template = template;
  }

  public MessageType(
      TenantId tenantId, MessageTypeId id, NoticeType key, String name, String kind) {
    super(id, tenantId);
    this.key = key;
    this.name = name;
    this.kind = NoticeTypeKind.valueOf(kind);
  }
}
