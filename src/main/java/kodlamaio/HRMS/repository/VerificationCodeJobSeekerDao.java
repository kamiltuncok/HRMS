package kodlamaio.HRMS.repository;

import kodlamaio.HRMS.entities.concretes.VerificationCodeJobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeJobSeekerDao extends JpaRepository<VerificationCodeJobSeeker, Long> {
}
