package kodlamaio.HRMS.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.School;

public interface SchoolDao extends JpaRepository<School, Long> {

	List<School> findByResume_IdOrderByGraduateDateAsc(Long resumeId);

	List<School> findByResume_IdOrderByGraduateDateDesc(Long resumeId);
}
