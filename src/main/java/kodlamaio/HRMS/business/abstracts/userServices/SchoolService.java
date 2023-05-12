package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.School;

public interface SchoolService {

	DataResult<List<School>> getAll();
	Result add(School school);
	
	DataResult<List<School>> findAllByEmployee_IdOrderByGraduateDate(int employeeId);
	
	DataResult<List<School>> findAllByEmployee_IdOrderByGraduateDateDesc(int employeeId, Direction direction);
}
