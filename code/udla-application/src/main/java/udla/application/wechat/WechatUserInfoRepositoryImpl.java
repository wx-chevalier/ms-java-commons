package udla.application.wechat;

import java.util.Optional;
import org.springframework.stereotype.Service;
import udla.common.data.shared.id.UserId;
import udla.domain.wechat.WechatUser;
import udla.domain.wechat.WechatUserInfoRepository;
import udla.infra.tunnel.db.infra.wechat.WechatUserDO;
import udla.infra.tunnel.db.infra.wechat.WechatUserTunnel;

@Service
public class WechatUserInfoRepositoryImpl implements WechatUserInfoRepository {

  private WechatUserTunnel wechatUserTunnel;

  private WechatUserConverter wechatUserConverter;

  public WechatUserInfoRepositoryImpl(
      WechatUserTunnel wechatUserTunnel, WechatUserConverter wechatUserConverter) {
    this.wechatUserTunnel = wechatUserTunnel;
    this.wechatUserConverter = wechatUserConverter;
  }

  @Override
  public Optional<WechatUser> getByOpenId(String openId) {
    return Optional.ofNullable(wechatUserTunnel.getByOpenId(openId))
        .map(wechatUserConverter::convertFrom);
  }

  @Override
  public WechatUser save(WechatUser user) {
    WechatUserDO userDO = wechatUserConverter.convertTo(user);
    if (user.getId() != null) {
      wechatUserTunnel.updateById(userDO);
    } else {
      wechatUserTunnel.save(userDO);
    }
    return getByOpenId(user.getOpenId()).orElse(null);
  }

  @Override
  public Optional<WechatUser> getByUserId(UserId userId) {
    return Optional.ofNullable(wechatUserTunnel.getByUserId(userId.getId()))
        .map(wechatUserConverter::convertFrom);
  }
}
