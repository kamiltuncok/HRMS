package kodlamaio.HRMS.dataaccess.abstracts;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.JobTitle;

public interface JobTitleDao extends JpaRepository<JobTitle,Integer> {
	public List<JobTitle> findByTitleContaining(String title);
}
