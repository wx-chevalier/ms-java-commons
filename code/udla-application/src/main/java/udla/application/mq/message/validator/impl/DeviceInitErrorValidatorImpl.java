package udla.application.mq.message.validator.impl;

import lombok.extern.slf4j.Slf4j;
import udla.application.mq.message.validator.SendMessageValidator;
import udla.common.data.shared.id.BaseEntityId;

/** 设备初始化错误校验 */
@Slf4j
public class DeviceInitErrorValidatorImpl implements SendMessageValidator {
  @Override
  public boolean validate(BaseEntityId baseEntityId) {
    return true;
  }
}
