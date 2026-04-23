package kodlamaio.HRMS.repository;

import kodlamaio.HRMS.entities.concretes.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationDao extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByJobSeeker_Id(Long jobSeekerId);
    List<JobApplication> findByJobAdvertisement_Employer_Id(Long employerId);
}
