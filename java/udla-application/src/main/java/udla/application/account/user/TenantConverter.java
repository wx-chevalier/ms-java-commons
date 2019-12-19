package udla.application.account.user;

import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.BaseEntityId;
import udla.common.data.shared.id.CompanyId;
import udla.domain.account.Tenant;
import udla.infra.tunnel.db.account.TenantDO;

@Component
public class TenantConverter extends AbstractConverter<Tenant, TenantDO> {

  @Override
  public TenantDO convertTo(Tenant tenant) {
    return new TenantDO()
        .setName(tenant.getName())
        .setAreaCode(tenant.getAreaCode())
        .setCompanyId(convertNullable(tenant.getCompanyId(), BaseEntityId::getId))
        .setTenantId(TenantId.NULL_TENANT_ID.getId());
  }

  @Override
  public Tenant convertFrom(TenantDO tenantDO) {
    return new Tenant(
        new TenantId(tenantDO.getId()),
        TenantId.create(tenantDO.getTenantId()),
        tenantDO.getCreatedAt(),
        tenantDO.getUpdatedAt(),
        tenantDO.getName(),
        new CompanyId(tenantDO.getCompanyId()),
        tenantDO.getAreaCode());
  }
}
