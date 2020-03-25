package udla.domain.wechat;

import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserId;

public interface WechatQrCodeTicketRepository {

  WechatQrCodeTicket getBoundQrCode(TenantId tenantId, UserId userId);
}
