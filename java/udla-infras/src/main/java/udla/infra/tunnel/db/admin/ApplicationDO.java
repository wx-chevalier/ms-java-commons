package udla.infra.tunnel.db.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import udla.infra.tunnel.db.shared.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("admin_wx_application")
public class ApplicationDO extends BaseDO<ApplicationDO> {

  private String name;
}
