package udla.application.infra.filestore;

import java.io.UncheckedIOException;
import java.util.Optional;
import org.springframework.stereotype.Component;
import udla.common.data.common.AbstractConverter;
import udla.common.data.infra.filestore.FileInfoSerializer;
import udla.common.data.infra.filestore.FileStoreInfo;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.FileId;
import udla.domain.infra.filestore.StoredFile;
import udla.infra.common.exception.DataValidationException;
import udla.infra.tunnel.db.infra.file.FileDO;

@Component
public class StoredFileConverter extends AbstractConverter<StoredFile, FileDO> {

  @Override
  public FileDO convertTo(StoredFile file) {
    String storeInfo = FileInfoSerializer.instance().toJson(file.getStoreInfo());
    return new FileDO()
        .setId(Optional.ofNullable(file.getId()).map(FileId::getId).orElse(null))
        .setTenantId(file.getTenantId().getId())
        .setName(file.getName())
        .setFileMd5(file.getMd5())
        .setSize(file.getSize())
        .setSaveType(file.getSaveType())
        .setFileStoreInfo(storeInfo);
  }

  @Override
  public StoredFile convertFrom(FileDO fileDO) {
    try {
      FileStoreInfo fileStoreInfo =
          FileInfoSerializer.instance().fromJson(fileDO.getFileStoreInfo());
      return new StoredFile(
          new FileId(fileDO.getId()),
          new TenantId(fileDO.getTenantId()),
          fileDO.getCreatedAt(),
          fileDO.getUpdatedAt(),
          fileDO.getName(),
          fileDO.getFileMd5(),
          fileDO.getSize(),
          fileDO.getSaveType(),
          fileStoreInfo);
    } catch (UncheckedIOException e) {
      throw new DataValidationException(
          "Corrupted file " + fileDO.getId() + ": " + e.getMessage(), e);
    }
  }
}
