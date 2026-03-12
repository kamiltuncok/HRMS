package kodlamaio.HRMS.service;

import java.util.List;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.JobSeekerResponse;

public interface JobSeekerService {
	DataResult<List<JobSeekerResponse>> getAll();

	DataResult<JobSeekerResponse> add(JobSeekerRequest request);

	DataResult<JobSeekerResponse> update(Long id, JobSeekerRequest request);

	Result delete(Long id);

	Result validate(JobSeekerRequest request) throws Exception;

	DataResult<JobSeekerResponse> getJobSeekerById(Long jobSeekerId);
}
