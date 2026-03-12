package kodlamaio.HRMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.JobSeeker;

public interface JobSeekerDao extends JpaRepository<JobSeeker, Long> {
	// JobSeeker getByJobAdvertisement_Id(int JobAdvertId);
}
