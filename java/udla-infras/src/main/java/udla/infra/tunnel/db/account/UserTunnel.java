package udla.infra.tunnel.db.account;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import udla.common.data.account.Authority;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.RoleId;
import udla.infra.converter.PageConverter;
import udla.infra.tunnel.db.Helper;
import udla.infra.tunnel.db.mapper.account.UserMapper;
import udla.infra.tunnel.db.shared.BaseDO;

@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserTunnel extends ServiceImpl<UserMapper, UserDO> {

  public IPage<UserDO> find(
      TenantId tenantId, RoleId roleId, String key, Authority authority, Pageable pageable) {
    log.info("find {} {} {} {}", tenantId, roleId, key, pageable);
    IPage<UserDO> page = PageConverter.toIPage(pageable);
    return this.baseMapper.find(
        page, tenantId.getId(), authority, roleId == null ? null : roleId.getId(), key);
  }

  public Optional<UserDO> findByUsername(TenantId tenantId, String username) {
    return Optional.ofNullable(
        getOne(
            new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getTenantId, tenantId.getId())
                .eq(UserDO::getUsername, username)
                .or()
                .eq(UserDO::getEmail, username)
                .or()
                .eq(UserDO::getPhoneNumber, username)));
  }

  public List<UserDO> findTenantAdminUser(Set<Long> tenantIds) {
    Wrapper<UserDO> queryWrapper =
        Helper.getQueryWrapper(UserDO.class)
            .eq(UserDO::getAuthority, Authority.TENANT_ADMIN)
            .in(BaseDO::getTenantId, tenantIds)
            .isNull(BaseDO::getDeletedAt);
    return this.baseMapper.selectList(queryWrapper);
  }
}
