package udla.application.admin;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import udla.common.data.shared.id.ApplicationId;
import udla.domain.admin.Application;
import udla.domain.admin.ApplicationRepository;
import udla.infra.common.persistence.MyBatisIdBasedEntityRepository;
import udla.infra.tunnel.db.admin.ApplicationDO;
import udla.infra.tunnel.db.admin.ApplicationTunnel;
import udla.infra.tunnel.db.mapper.admin.ApplicationMapper;

@Repository
public class ApplicationRepositoryImpl
    extends MyBatisIdBasedEntityRepository<
        ApplicationTunnel, ApplicationMapper, ApplicationDO, Application, ApplicationId>
    implements ApplicationRepository {

  @Getter(AccessLevel.PROTECTED)
  private ApplicationConverter converter;

  public ApplicationRepositoryImpl(
      ApplicationTunnel applicationTunnel,
      ApplicationMapper mapper,
      ApplicationConverter converter) {
    super(applicationTunnel, mapper);
    this.converter = converter;
  }
}
