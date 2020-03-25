package udla.domain.infra.verificationcode;

import java.util.Optional;
import udla.common.data.infra.notice.NoticeType;
import udla.common.data.shared.id.VerificationCodeId;
import udla.domain.shared.IdBasedEntityRepository;

public interface VerificationCodeRepository
    extends IdBasedEntityRepository<VerificationCodeId, VerificationCode> {

  Optional<VerificationCode> findValidCodeByPhoneNumber(String phoneNumber, String code);

  Optional<VerificationCode> findValidCodeByEmail(String email, String code);

  Optional<VerificationCode> findValidCode(String sendDst, String code, NoticeType type);
}
