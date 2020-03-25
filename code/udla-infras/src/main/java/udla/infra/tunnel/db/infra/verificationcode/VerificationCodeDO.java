package udla.infra.tunnel.db.infra.verificationcode;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import udla.common.data.infra.notice.NoticeSendChannel;
import udla.common.data.infra.notice.NoticeType;
import udla.infra.tunnel.db.shared.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("infra_verification_code")
public class VerificationCodeDO extends BaseDO<VerificationCodeDO> {

  private static final long serialVersionUID = 1L;

  private Long userId;

  private String code;

  private NoticeType type;

  private NoticeSendChannel channel;

  private String sendDst;

  private LocalDateTime verifiedAt;

  private LocalDateTime sentAt;

  private LocalDateTime expireAt;
}
