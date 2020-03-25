package udla.common.data.mq.file;

import java.io.Serializable;
import lombok.Data;
import udla.common.data.shared.id.*;
import udla.common.data.shared.id.FileId;

@Data
public class FileAttributeRetrievalMessage implements Serializable {

  private static final long serialVersionUID = 5166279968208820628L;

  private TenantId tenantId;

  private FileId fileId;
}
