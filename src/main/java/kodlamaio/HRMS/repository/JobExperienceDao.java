package kodlamaio.HRMS.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.JobExperience;

public interface JobExperienceDao extends JpaRepository<JobExperience, Long> {
    List<JobExperience> findByResume_IdOrderByEndDateDesc(Long resumeId);

    List<JobExperience> findByResume_IdOrderByEndDateAsc(Long resumeId);
}
