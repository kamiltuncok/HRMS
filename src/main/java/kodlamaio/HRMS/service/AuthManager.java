package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.dto.AuthResponse;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.UserForLoginRequest;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService {

	private final JobSeekerService jobSeekerService;
	private final EmployerService employerService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public Result registerForJobSeeker(JobSeekerRequest request) throws Exception {
		return this.jobSeekerService.add(request);
	}

	@Override
	public Result registerForEmployer(EmployerRequest request) throws Exception {
		return this.employerService.add(request);
	}

	@Override
	public DataResult<AuthResponse> login(UserForLoginRequest loginRequest) {
		var auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.email(),
						loginRequest.password()));

		User user = (User) auth.getPrincipal();

		String token = jwtService.generateToken(user);
		var response = new AuthResponse(token, user.getEmail(), user.getRole().getRoleName());

		return new SuccessDataResult<>(response, "Login successful");
	}
}
