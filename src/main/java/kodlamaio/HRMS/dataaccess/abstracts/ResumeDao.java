package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.Resume;

public interface ResumeDao extends JpaRepository<Resume, Integer>{
	
	Resume getResumeByJobSeeker_id(int jsId);
}
