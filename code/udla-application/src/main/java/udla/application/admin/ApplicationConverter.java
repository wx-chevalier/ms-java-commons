package udla.application.admin;

import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.common.data.shared.id.*;
import udla.domain.admin.Application;
import udla.infra.tunnel.db.admin.ApplicationDO;

@Component
public class ApplicationConverter extends AbstractConverter<Application, ApplicationDO> {

  @Override
  public ApplicationDO convertTo(Application application) {
    return new ApplicationDO()
        .setId(application.getId().getId())
        .setTenantId(application.getTenantId().getId())
        .setCreatedAt(application.getCreatedAt())
        .setUpdatedAt(application.getUpdatedAt())
        .setName(application.getName());
  }

  @Override
  public Application convertFrom(ApplicationDO applicationDO) {
    return new Application(
        new ApplicationId(applicationDO.getId()),
        new TenantId(applicationDO.getTenantId()),
        applicationDO.getCreatedAt(),
        applicationDO.getUpdatedAt(),
        applicationDO.getName());
  }
}
