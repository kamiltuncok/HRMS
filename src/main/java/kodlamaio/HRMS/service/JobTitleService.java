package kodlamaio.HRMS.service;

import java.util.List;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobTitle;

import kodlamaio.HRMS.dto.JobTitleResponse;

public interface JobTitleService {
	DataResult<List<JobTitleResponse>> getAll();

	Result add(JobTitle jobTitle);

	DataResult<List<JobTitleResponse>> getByCategory(Long categoryId);
}
