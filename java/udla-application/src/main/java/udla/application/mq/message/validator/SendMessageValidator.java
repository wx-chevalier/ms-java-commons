package udla.application.mq.message.validator;

import udla.common.data.shared.id.BaseEntityId;

public interface SendMessageValidator {
  /**
   * 校验
   *
   * @param baseEntityId 资源ID
   */
  boolean validate(BaseEntityId baseEntityId);
}
