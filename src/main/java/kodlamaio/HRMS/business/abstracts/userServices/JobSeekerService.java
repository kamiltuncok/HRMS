package kodlamaio.HRMS.business.abstracts.userServices;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobSeeker;

public interface JobSeekerService extends CrudService<JobSeeker> {
	
	Result isNull(JobSeeker jobSeeker) throws Exception;
	Result validate(JobSeeker jobSeeker) throws Exception;
}
