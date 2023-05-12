package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobExperince;

public interface JobExperinceService {

	DataResult<List<JobExperince>> getAll();
	Result add(JobExperince jobExperince);
	
	DataResult<List<JobExperince>> findAllByEmployee_IdOrderByDateOfEnd(int employeeId);
	
	DataResult<List<JobExperince>> findAllByEmployee_IdOrderByDateOfEndDesc(int employeeId, Direction direction);
}
