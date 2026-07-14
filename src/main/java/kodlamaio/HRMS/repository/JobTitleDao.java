package kodlamaio.HRMS.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.JobTitle;

import java.util.Optional;

public interface JobTitleDao extends JpaRepository<JobTitle, Long> {
	Optional<JobTitle> findByTitle(String title);
	List<JobTitle> findByTitleContaining(String title);
	List<JobTitle> findByCategory_Id(Long categoryId);
}
