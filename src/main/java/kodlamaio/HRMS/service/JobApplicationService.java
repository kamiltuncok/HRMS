package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.dto.JobApplicationRequest;
import kodlamaio.HRMS.dto.JobApplicationResponse;

import java.util.List;

public interface JobApplicationService {
    DataResult<JobApplicationResponse> add(JobApplicationRequest request);
    DataResult<List<JobApplicationResponse>> getAll();
    DataResult<List<JobApplicationResponse>> getByJobSeekerId(Long jobSeekerId);
}
