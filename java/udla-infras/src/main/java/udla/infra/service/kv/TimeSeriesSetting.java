package udla.infra.service.kv;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSeriesSetting {

  private long maxTsIntervals;
}
