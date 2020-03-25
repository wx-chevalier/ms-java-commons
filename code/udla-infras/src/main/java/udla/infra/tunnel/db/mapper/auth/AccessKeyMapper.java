package udla.infra.tunnel.db.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import udla.infra.tunnel.db.auth.AccessKeyDO;

@Component
public interface AccessKeyMapper extends BaseMapper<AccessKeyDO> {}
