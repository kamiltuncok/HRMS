package kodlamaio.HRMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.Resume;

public interface ResumeDao extends JpaRepository<Resume, Long> {

	Resume findByJobSeeker_UserId(Long jsId);
}
