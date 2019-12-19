package udla.infra.common.exception;

import org.springframework.http.HttpStatus;
import udla.common.data.code.ApiErrorCode;

/**
 * ForbiddenException.
 *
 * @author lotuc
 */
public class ForbiddenException extends BaseBizException {

  public ForbiddenException() {
    super(HttpStatus.FORBIDDEN);
  }

  public ForbiddenException(String message) {
    super(message, HttpStatus.FORBIDDEN);
  }

  public ForbiddenException(String message, Throwable cause) {
    super(message, cause, HttpStatus.FORBIDDEN);
  }

  public ForbiddenException(String message, ApiErrorCode code) {
    super(message, HttpStatus.FORBIDDEN);
    this.setCode(code);
  }
}
