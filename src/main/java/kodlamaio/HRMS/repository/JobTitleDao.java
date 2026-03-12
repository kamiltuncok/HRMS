package kodlamaio.HRMS.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.JobTitle;

public interface JobTitleDao extends JpaRepository<JobTitle, Long> {
	List<JobTitle> findByTitleContaining(String title);
}
