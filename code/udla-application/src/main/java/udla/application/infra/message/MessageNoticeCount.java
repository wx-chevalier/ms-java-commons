package udla.application.infra.message;

import lombok.Data;

@Data
public class MessageNoticeCount {

  private long readCount;

  private long unreadCount;
}
