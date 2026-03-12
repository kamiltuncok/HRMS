package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.UserForLoginRequest;

public interface AuthService {
	Result registerForJobSeeker(JobSeekerRequest request) throws Exception;

	Result registerForEmployer(EmployerRequest request) throws Exception;

	Result login(UserForLoginRequest loginRequest);
}
