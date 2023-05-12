package kodlamaio.HRMS.dataaccess.abstracts;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.JobExperince;

public interface JobExperinceDao extends JpaRepository<JobExperince, Integer> {
	
	List<JobExperince> findAllByEmployee_IdOrderByDateOfEnd(int employeeId);
	
	List<JobExperince> findAllByEmployee_IdOrderByDateOfEndDesc(int employeeId);
}
