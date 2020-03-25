package udla.api.rest.shared.converter;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import udla.api.rest.shared.dto.envelope.PaginationMeta;
import udla.api.rest.shared.dto.envelope.ResponseEnvelope;

public class PageConverter {
  public static <T> ResponseEnvelope<List<T>> toResponseEntity(Page<T> value) {
    Pageable pageable = value.getPageable();
    return ResponseEnvelope.createOk(
        value.getContent(),
        new PaginationMeta()
            .setPageNum(pageable.getPageNumber())
            .setTotalElements(value.getTotalElements())
            .setTotalPages(value.getTotalPages()));
  }
}
