package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;


import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobTitle;

public interface JobTitleService {
	public DataResult<List<JobTitle>> getAll();

	public Result add(JobTitle jobTitle);
}
