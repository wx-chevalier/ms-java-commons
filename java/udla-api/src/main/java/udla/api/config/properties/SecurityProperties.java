package udla.api.config.properties;

import lombok.Data;
import udla.api.config.security.JwtTokenConfig;

/**
 * SecurityProperties.
 *
 * @author lotuc
 */
@Data
public class SecurityProperties {
  JwtTokenConfig jwt;
}
