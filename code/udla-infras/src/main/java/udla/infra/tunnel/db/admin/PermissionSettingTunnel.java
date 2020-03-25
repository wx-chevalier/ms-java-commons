package udla.infra.tunnel.db.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import udla.common.data.shared.id.*;
import udla.infra.tunnel.db.Helper;
import udla.infra.tunnel.db.mapper.admin.PermissionSettingMapper;
import udla.infra.tunnel.db.shared.BaseDO;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PermissionSettingTunnel
    extends ServiceImpl<PermissionSettingMapper, PermissionSettingDO> {

  /**
   * 获取指定租户下指定应用的权限
   *
   * <p>若应用ID为空则返回全部权限列表
   */
  public List<PermissionSettingDO> list(TenantId tenantId, ApplicationId appId) {
    Wrapper<PermissionSettingDO> queryWrapper =
        Helper.getQueryWrapper(PermissionSettingDO.class)
            .eq(BaseDO::getTenantId, tenantId.getId())
            .eq(Objects.nonNull(appId), PermissionSettingDO::getApplicationId, appId.getId())
            .isNull(BaseDO::getDeletedAt)
            .orderByDesc(PermissionSettingDO::getDirectory)
            .orderByDesc(BaseDO::getCreatedAt);
    return this.baseMapper.selectList(queryWrapper);
  }
}
