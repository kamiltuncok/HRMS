package kodlamaio.HRMS.repository;

import kodlamaio.HRMS.entities.concretes.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationDao extends JpaRepository<JobApplication, Long> {
}
