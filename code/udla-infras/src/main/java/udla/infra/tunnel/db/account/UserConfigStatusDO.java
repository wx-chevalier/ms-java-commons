package udla.infra.tunnel.db.account;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import udla.common.data.account.UserConfigKey;
import udla.common.data.account.UserConfigStatus;
import udla.infra.tunnel.db.shared.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("u_user_config_status")
public class UserConfigStatusDO extends BaseDO<UserConfigStatusDO> {

  private Long userId;

  @TableField(keepGlobalFormat = true)
  private UserConfigKey key;

  private String value;

  private UserConfigStatus status;
}
