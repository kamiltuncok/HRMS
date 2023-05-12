package kodlamaio.HRMS.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.School;

public interface SchoolDao extends JpaRepository<School, Integer> {
	
	List<School> findAllByEmployee_IdOrderByGraduateDate(int employeeId);
	
	List<School> findAllByEmployee_IdOrderByGraduateDateDesc(int employeeId);
	
	
}
