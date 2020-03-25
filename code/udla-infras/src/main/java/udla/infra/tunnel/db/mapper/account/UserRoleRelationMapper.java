package udla.infra.tunnel.db.mapper.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import udla.infra.tunnel.db.account.RoleDO;
import udla.infra.tunnel.db.account.UserRoleRelationDO;

@Component
public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelationDO> {

  List<RoleDO> getRoleByUserId(@Param("userId") Long userId);
}
