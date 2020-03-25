package udla.infra.tunnel.db.mapper.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import udla.common.data.account.Authority;
import udla.infra.tunnel.db.account.UserDO;

@Component
public interface UserMapper extends BaseMapper<UserDO> {

  IPage<UserDO> find(
      IPage<UserDO> page,
      @Param("tenantId") Long tenantId,
      @Param("authority") Authority authority,
      @Param("roleId") Long roleId,
      @Param("key") String key);
}
