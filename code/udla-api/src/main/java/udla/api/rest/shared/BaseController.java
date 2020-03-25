package udla.api.rest.shared;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import udla.api.config.properties.ApplicationProperties;
import udla.api.config.security.model.SecurityUser;
import udla.application.account.accesscontrol.AccessControlQueryService;
import udla.application.account.user.TenantQueryService;
import udla.application.account.user.UserQueryService;
import udla.application.admin.ApplicationQueryService;
import udla.application.admin.PermissionSettingQueryService;
import udla.application.admin.config.AdminConfigQueryService;
import udla.application.infra.area.AreaQueryService;
import udla.application.infra.filestore.StoredFileQueryService;
import udla.application.infra.message.MessageTypeQueryService;
import udla.application.infra.message.NotifyService;
import udla.application.infra.message.RoleMessageConfigQueryService;
import udla.application.infra.notice.email.EmailService;
import udla.application.infra.notice.site.SiteMessageService;
import udla.application.infra.notice.sms.SmsService;
import udla.common.data.shared.id.TenantId;
import udla.common.data.shared.id.UserId;
import udla.domain.account.AccessControlCommandService;
import udla.domain.account.UserCommandService;
import udla.domain.infra.filestore.StoredFileCommandService;
import udla.domain.infra.message.RoleMessageConfigCommandService;
import udla.domain.infra.verificationcode.VerificationCodeCommandService;
import udla.infra.common.exception.UnAuthorizedException;

@Slf4j
public class BaseController {

  @Autowired protected UserCommandService userCommandService;

  @Autowired protected UserQueryService userQueryService;

  @Autowired protected VerificationCodeCommandService verificationCodeCommandService;

  @Autowired protected StoredFileCommandService storedFileCommandService;

  @Autowired protected StoredFileQueryService storedFileQueryService;

  // ===============================通知相关=======================================

  @Autowired protected MessageTypeQueryService messageTypeQueryService;

  @Autowired protected SmsService smsService;

  @Autowired protected EmailService emailService;

  @Autowired protected SiteMessageService siteMessageService;

  @Autowired protected NotifyService notifyService;

  @Autowired protected RoleMessageConfigCommandService roleMessageConfigCommandService;

  @Autowired protected RoleMessageConfigQueryService roleMessageConfigQueryService;

  // ===============================权限相关=======================================

  @Autowired protected PermissionSettingQueryService permissionSettingQueryService;

  @Autowired protected AccessControlCommandService accessControlCommandService;

  @Autowired protected AccessControlQueryService accessControlQueryService;

  // ===============================应用相关=======================================

  @Autowired protected ApplicationQueryService applicationQueryService;

  @Autowired protected ApplicationProperties applicationProperties;

  // ===============================管理员配置=======================================

  @Autowired protected AdminConfigQueryService adminConfigQueryService;

  @Autowired protected TenantQueryService tenantQueryService;

  // ===============================通用相关=======================================

  @Autowired protected AreaQueryService areaQueryService;

  protected Optional<SecurityUser> tryGetCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
      return Optional.of((SecurityUser) authentication.getPrincipal());
    } else {
      throw new UnAuthorizedException();
    }
  }

  protected SecurityUser getCurrentUser() {
    return tryGetCurrentUser().orElseThrow(UnAuthorizedException::new);
  }

  protected UserId getCurrentUserId() {
    return getCurrentUser().getId();
  }

  protected TenantId getTenantId() {
    return getCurrentUser().getTenantId();
  }
}
