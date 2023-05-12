package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.SchoolService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.SchoolDao;
import kodlamaio.HRMS.entities.concretes.School;

@Service
public class SchoolManager implements SchoolService {

	private SchoolDao schoolDao;

	@Autowired
	public SchoolManager(SchoolDao schoolDao) {
		super();
		this.schoolDao = schoolDao;
	}

	@Override
	public DataResult<List<School>> getAll() {
		
		return new SuccessDataResult<List<School>>(this.schoolDao.findAll(),"Schools listed");
	}

	@Override
	public Result add(School school) {
		this.schoolDao.save(school);
		return new SuccessResult("School added");
	}
	
	@Override
	public DataResult<List<School>> findAllByEmployee_IdOrderByGraduateDate(int employeeId) {
		
		return new SuccessDataResult<List<School>>(this.schoolDao.findAllByEmployee_IdOrderByGraduateDate(employeeId), "Listed Jobseeker's experinces");
	}

	@Override
	public DataResult<List<School>> findAllByEmployee_IdOrderByGraduateDateDesc(int employeeId, Direction direction) {
		
		return new SuccessDataResult<List<School>>(this.schoolDao.findAllByEmployee_IdOrderByGraduateDateDesc(employeeId), "Listed the experinces order by DESC");
	}
}

