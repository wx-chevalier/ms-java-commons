package udla.application.wechat;

import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.domain.wechat.WechatAccessToken;
import udla.infra.tunnel.db.infra.wechat.WechatAccessTokenDO;

@Component
public class WechatAccessTokenConverter
    extends AbstractConverter<WechatAccessToken, WechatAccessTokenDO> {

  @Override
  public WechatAccessTokenDO convertTo(WechatAccessToken wechatAccessToken) {
    return new WechatAccessTokenDO()
        .setId(wechatAccessToken.getId())
        .setAccessToken(wechatAccessToken.getAccessToken())
        .setExpiresIn(wechatAccessToken.getExpiresIn())
        .setCreatedAt(wechatAccessToken.getCreatedAt())
        .setDeletedAt(wechatAccessToken.getDeletedAt());
  }

  @Override
  public WechatAccessToken convertFrom(WechatAccessTokenDO wechatAccessTokenDO) {
    return new WechatAccessToken()
        .setId(wechatAccessTokenDO.getId())
        .setAccessToken(wechatAccessTokenDO.getAccessToken())
        .setExpiresIn(wechatAccessTokenDO.getExpiresIn())
        .setCreatedAt(wechatAccessTokenDO.getCreatedAt())
        .setDeletedAt(wechatAccessTokenDO.getDeletedAt());
  }
}
