package kodlamaio.HRMS.dataaccess.abstracts.verificationCodeDaos;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.verificationCodes.VerificationCodeEmployer;


public interface VerificationCodeEmployerDao extends JpaRepository<VerificationCodeEmployer,Integer> {

}
