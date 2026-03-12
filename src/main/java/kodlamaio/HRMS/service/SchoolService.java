package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.data.domain.Sort.Direction;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.School;

public interface SchoolService {

	DataResult<List<School>> getAll();

	Result add(School school);

	DataResult<List<School>> findByResume_IdOrderByGraduateDateAsc(Long resumeId);

	DataResult<List<School>> findByResume_IdOrderByGraduateDateDesc(Long resumeId, Direction direction);
}
