package kodlamaio.HRMS.service;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.dto.JobAdvertisementRequest;
import kodlamaio.HRMS.dto.JobAdvertisementResponse;

public interface JobAdvertisementService {
	DataResult<List<JobAdvertisementResponse>> getAll();

	DataResult<JobAdvertisementResponse> add(JobAdvertisementRequest request);

	DataResult<JobAdvertisementResponse> update(Long id, JobAdvertisementRequest request);

	Result delete(Long id);

	DataResult<List<JobAdvertisementResponse>> getActiveJobAdvertisement();

	DataResult<List<JobAdvertisementResponse>> getActiveJobAdvertisementOrderedByDesc();

	DataResult<List<JobAdvertisementResponse>> getActiveJobAdvertisementWithSpecificCompany(String companyName);

	Result updateJobAdvertisementStatus(Long jobAdvertisementId);

	DataResult<JobAdvertisementResponse> getJobAdvertisementById(Long jobAdvertisementId);
}
