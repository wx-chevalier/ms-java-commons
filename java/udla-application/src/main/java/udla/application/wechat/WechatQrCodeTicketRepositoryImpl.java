package udla.application.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserId;
import udla.domain.wechat.WechatAccessToken;
import udla.domain.wechat.WechatAccessTokenRepository;
import udla.domain.wechat.WechatQrCodeTicket;
import udla.domain.wechat.WechatQrCodeTicketRepository;
import udla.infra.common.data.wechat.req.WechatActionInfo;
import udla.infra.common.data.wechat.req.WechatQrCodeReq;
import udla.infra.common.data.wechat.req.WechatScene;
import udla.infra.tunnel.wechat.WechatTunnel;

@Slf4j
@Service
public class WechatQrCodeTicketRepositoryImpl implements WechatQrCodeTicketRepository {

  private WechatAccessTokenRepository wechatAccessTokenRepository;

  public WechatQrCodeTicketRepositoryImpl(WechatAccessTokenRepository wechatAccessTokenRepository) {
    this.wechatAccessTokenRepository = wechatAccessTokenRepository;
  }

  @Override
  public WechatQrCodeTicket getBoundQrCode(TenantId tenantId, UserId userId) {
    WechatAccessToken latestToken = wechatAccessTokenRepository.getLatest();
    WechatQrCodeReq req =
        new WechatQrCodeReq(new WechatActionInfo(WechatScene.bound(tenantId, userId)));
    return WechatTunnel.getQrCodeTicket(latestToken.getAccessToken(), req);
  }
}
