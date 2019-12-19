package udla.domain.wechat;

public interface WechatAccessTokenRepository {

  WechatAccessToken getLatest();

  void save(WechatAccessToken wechatAccessToken);
}
