package kodlamaio.HRMS.repository;

import kodlamaio.HRMS.entities.concretes.VerificationCodeEmployer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeEmployerDao extends JpaRepository<VerificationCodeEmployer, Long> {
}
