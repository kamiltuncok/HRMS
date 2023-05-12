package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.JobSeekerService;
import kodlamaio.HRMS.core.business.BusinessRules;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.JobSeekerDao;
import kodlamaio.HRMS.entities.concretes.JobSeeker;

@Service
public class JobSeekerManager implements JobSeekerService {
	
	private JobSeekerDao jobSeekerDao;
	
	@Autowired
	public JobSeekerManager(JobSeekerDao jobSeekerDao) {
		super();
		this.jobSeekerDao = jobSeekerDao;
	}

	@Override
	public DataResult<List<JobSeeker>> getAll() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<JobSeeker>>(this.jobSeekerDao.findAll(),"İş arayan listelendi.");
	}

	@Override
	public DataResult<JobSeeker> add(JobSeeker jobSeeker) {
		JobSeeker js= this.jobSeekerDao.save(jobSeeker);
		return new SuccessDataResult<JobSeeker>(js,"İş arayan eklendi");
		
	}

	@Override
	public Result isNull(JobSeeker jobSeeker) {
		
		if(jobSeeker.getFirstName().isBlank() || jobSeeker.getLastName().isBlank()
			|| jobSeeker.getUser().getEmail().isBlank() || jobSeeker.getUser().getPassword().isBlank())
		{
			return new ErrorResult("Tüm alanlar zorunludur!");
		}
		return new SuccessResult();
	}

	@Override
	public Result delete(JobSeeker entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<JobSeeker> update(JobSeeker entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result validate(JobSeeker jobSeeker) throws Exception {
		Result result=BusinessRules.Run(isNull(jobSeeker));
		if(result!=null) {
			return result;
		}
		return new SuccessResult();
	}

}
