package udla.infra.tunnel.db.account;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import udla.infra.tunnel.db.mapper.account.UserTenantRelationMapper;

@Component
public class UserTenantRelationTunnel
    extends ServiceImpl<UserTenantRelationMapper, UserTenantRelationDO> {}
