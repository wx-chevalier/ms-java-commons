package udla.infra.tunnel.db.mapper.infra.validatecode;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import udla.infra.tunnel.db.infra.verificationcode.VerificationCodeDO;

@Component
public interface ValidateCodeMapper extends BaseMapper<VerificationCodeDO> {}
