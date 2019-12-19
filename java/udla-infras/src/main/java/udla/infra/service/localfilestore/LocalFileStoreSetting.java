package udla.infra.service.localfilestore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalFileStoreSetting {

  private String filePath;

  private String baseUrl;
}
