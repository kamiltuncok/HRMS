package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.SchoolDao;
import kodlamaio.HRMS.entities.concretes.School;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolManager implements SchoolService {

	private final SchoolDao schoolDao;

	@Override
	public DataResult<List<School>> getAll() {
		return new SuccessDataResult<>(this.schoolDao.findAll(), "Schools have been listed successfully.");
	}

	@Override
	public Result add(School school) {
		this.schoolDao.save(school);
		return new SuccessResult("School has been added successfully.");
	}

	@Override
	public Result delete(Long id) {
		this.schoolDao.deleteById(id);
		return new SuccessResult("School has been deleted successfully.");
	}

	@Override
	public DataResult<List<School>> findByResume_IdOrderByGraduateDateAsc(Long resumeId) {
		return new SuccessDataResult<>(this.schoolDao.findByResume_IdOrderByGraduateDateAsc(resumeId),
				"Schools ordered by graduation date (ASC) listed successfully.");
	}

	@Override
	public DataResult<List<School>> findByResume_IdOrderByGraduateDateDesc(Long resumeId, Direction direction) {
		return new SuccessDataResult<>(this.schoolDao.findByResume_IdOrderByGraduateDateDesc(resumeId),
				"Schools ordered by graduation date (DESC) listed successfully.");
	}
}
