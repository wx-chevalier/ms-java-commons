package udla.infra.validator;

import java.util.Objects;
import udla.common.data.shared.id.*;

public abstract class CrudObjectValidator<D> {

  public void validateDataImpl(TenantId tenantId, D data) {}

  public void validateCreate(TenantId tenantId, D data) {}

  public void validateUpdate(TenantId tenantId, D data) {}

  protected boolean isSameData(D existentData, D actualData) {
    return Objects.equals(existentData, actualData);
  }
}
