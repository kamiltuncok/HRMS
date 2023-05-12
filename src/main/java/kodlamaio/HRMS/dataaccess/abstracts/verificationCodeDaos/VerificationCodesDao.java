package kodlamaio.HRMS.dataaccess.abstracts.verificationCodeDaos;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.verificationCodes.VerificationCode;

public interface VerificationCodesDao extends JpaRepository<VerificationCode,Integer> {

}
