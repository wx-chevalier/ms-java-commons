package udla.api.rest.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import udla.api.rest.shared.BaseController;
import udla.domain.auth.AccessKey;
import udla.domain.auth.AccessKeyRepository;

@RestController
public class AccessKeyController extends BaseController {

  private AccessKeyRepository accessKeyRepository;

  public AccessKeyController(AccessKeyRepository accessKeyRepository) {
    this.accessKeyRepository = accessKeyRepository;
  }

  @PostMapping("/auth/access_key")
  public AccessKey createAccessKey(@RequestBody AccessKeyCreateRequest request) {
    AccessKey accessKey =
        AccessKey.create(
            getTenantId(), Long.parseLong(request.getEntityId()), request.getEntityType());
    accessKeyRepository.save(accessKey);
    return accessKey;
  }
}
