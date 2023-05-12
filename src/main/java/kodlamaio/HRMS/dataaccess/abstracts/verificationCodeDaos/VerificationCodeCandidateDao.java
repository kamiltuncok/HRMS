package kodlamaio.HRMS.dataaccess.abstracts.verificationCodeDaos;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.verificationCodes.VerificationCodeCandidate;


public interface VerificationCodeCandidateDao extends JpaRepository<VerificationCodeCandidate,Integer> {

}
