package udla.application.infra.filestore;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import udla.common.data.infra.filestore.LocalFileStoreInfo;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.FileId;
import udla.domain.infra.filestore.StoredFile;

public interface StoredFileQueryService {

  StoredFileDetail findFileDetail(StoredFile storedFile);

  Optional<StoredFileDetail> findFileDetail(TenantId tenantId, FileId fileId);

  StoredFileDetail findFileDetail(TenantId tenantId, StoredFile storedFile);

  List<StoredFileDetail> findFileDetailByIds(TenantId tenantId, Collection<FileId> fileIds);

  File findLocalFile(TenantId tenantId, LocalFileStoreInfo storeInfo);
}
