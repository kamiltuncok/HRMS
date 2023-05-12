package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.JobExperinceService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.JobExperinceDao;
import kodlamaio.HRMS.entities.concretes.JobExperince;

@Service
public class JobExperinceManager implements JobExperinceService {

	private JobExperinceDao jobExperinceDao;

	@Autowired
	public JobExperinceManager(JobExperinceDao jobExperinceDao) {
		super();
		this.jobExperinceDao = jobExperinceDao;
	}

	@Override
	public DataResult<List<JobExperince>> getAll() {
		
		return new SuccessDataResult<List<JobExperince>>(this.jobExperinceDao.findAll(), "Job experinces listed");
	}

	@Override
	public Result add(JobExperince jobExperince) {
		this.jobExperinceDao.save(jobExperince);
		return new SuccessResult("Job experince added");
	}

	@Override
	public DataResult<List<JobExperince>> findAllByEmployee_IdOrderByDateOfEnd(int employeeId) {
		
		return new SuccessDataResult<List<JobExperince>>(this.jobExperinceDao.findAllByEmployee_IdOrderByDateOfEnd(employeeId), "Listed Jobseeker's experinces");
	}

	@Override
	public DataResult<List<JobExperince>> findAllByEmployee_IdOrderByDateOfEndDesc(int employeeId, Direction direction) {
		
		return new SuccessDataResult<List<JobExperince>>(this.jobExperinceDao.findAllByEmployee_IdOrderByDateOfEndDesc(employeeId), "Listed the experinces order by DESC");
	}
	
	
	
}
