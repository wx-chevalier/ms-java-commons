package udla.api.config.properties;

import lombok.Data;
import udla.api.config.ApplicationDefaults;

@Data
public class TimeSeriesProperties {
  private long maxTsIntervals = ApplicationDefaults.maxTsIntervals;
}
