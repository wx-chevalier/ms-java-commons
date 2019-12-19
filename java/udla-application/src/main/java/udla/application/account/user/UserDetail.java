package udla.application.account.user;

import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import udla.application.account.permission.PermissionDetail;
import udla.application.infra.filestore.StoredFileDetail;
import udla.domain.account.Role;
import udla.domain.account.Tenant;
import udla.domain.account.User;
import udla.domain.wechat.WechatUser;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetail extends User {

  private User creator;

  private Tenant tenant;

  private WechatUser wechatUser;

  private StoredFileDetail avatar;

  private Collection<PermissionDetail> permissions;

  private Collection<Role> roles;

  public UserDetail(
      User user,
      User creator,
      Tenant tenant,
      StoredFileDetail avatar,
      Collection<PermissionDetail> permissions,
      Collection<Role> roles) {
    super();
    BeanUtils.copyProperties(user, this);
    this.creator = creator;
    this.tenant = tenant;
    this.avatar = avatar;
    this.permissions = permissions;
    this.roles = roles;
  }

  public UserDetail(
      User user,
      User creator,
      Tenant tenant,
      StoredFileDetail avatar,
      Collection<PermissionDetail> permissions,
      Collection<Role> roles,
      WechatUser wechatUser) {
    this(user, creator, tenant, avatar, permissions, roles);
    this.wechatUser = wechatUser;
  }
}
