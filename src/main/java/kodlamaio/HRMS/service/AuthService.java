package kodlamaio.HRMS.service;

import java.util.Map;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.ForgotPasswordRequest;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.ResetPasswordRequest;
import kodlamaio.HRMS.dto.UserForLoginRequest;

public interface AuthService {
	Result registerForJobSeeker(JobSeekerRequest request) throws Exception;

	Result registerForEmployer(EmployerRequest request) throws Exception;

	Result login(UserForLoginRequest loginRequest);

	Result forgotPassword(ForgotPasswordRequest request);

	Result resetPassword(ResetPasswordRequest request);

	DataResult<Map<String, Boolean>> validateResetToken(String token);
}
