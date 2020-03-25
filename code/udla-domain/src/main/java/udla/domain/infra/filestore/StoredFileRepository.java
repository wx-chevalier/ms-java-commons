package udla.domain.infra.filestore;

import java.util.Collection;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.FileId;
import udla.domain.shared.IdBasedEntityRepository;

public interface StoredFileRepository extends IdBasedEntityRepository<FileId, StoredFile> {

  /** 断定文件均存在 */
  boolean assertExist(TenantId tenantId, Collection<FileId> fileIds);
}
