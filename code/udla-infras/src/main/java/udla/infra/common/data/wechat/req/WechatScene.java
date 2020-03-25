package udla.infra.common.data.wechat.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.UserId;
import udla.infra.common.exception.NotAcceptException;
import udla.infra.converter.JsonConverter;

@Data
public class WechatScene {

  @JsonProperty("scene_str")
  private String sceneStr;

  private WechatScene(String sceneStr) {
    this.sceneStr = sceneStr;
  }

  public static WechatScene bound(TenantId tenantId, UserId userId) {
    if (userId == null) {
      throw new NotAcceptException("微信场景代码不能为空");
    }
    Map<String, String> param = new HashMap<>(2);
    param.put("scene", WechatSceneEnum.BOUND.name());
    param.put("userId", userId.idStr());
    param.put("tenantId", tenantId.idStr());
    return new WechatScene(JsonConverter.toJSONString(param));
  }
}
