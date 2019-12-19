package udla.api.rest.account.user;

import org.springframework.stereotype.Component;
import udla.api.config.security.model.SecurityUser;
import udla.common.data.common.AbstractConverter;
import udla.domain.account.User;

@Component
public class SecurityUserConverter extends AbstractConverter<User, SecurityUser> {

  @Override
  public SecurityUser convertTo(User user) {
    return new SecurityUser()
        .setId(user.getId())
        .setTenantId(user.getTenantId())
        .setAuthority(user.getAuthority())
        .setUsername(user.getUsername());
  }
}
