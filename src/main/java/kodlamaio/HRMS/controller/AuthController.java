package kodlamaio.HRMS.controller;

import jakarta.validation.Valid;
import kodlamaio.HRMS.dto.AuthResponse;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.ForgotPasswordRequest;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.ResetPasswordRequest;
import kodlamaio.HRMS.dto.UserForLoginRequest;
import kodlamaio.HRMS.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController extends BaseController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/registerforjobseeker")
	public ResponseEntity<?> registerForJobSeeker(@Valid @RequestBody JobSeekerRequest jobSeekerRequest) {
		return Ok(() -> this.authService.registerForJobSeeker(jobSeekerRequest));
	}

	@PostMapping("/registerforemployer")
	public ResponseEntity<?> registerForEmployer(@Valid @RequestBody EmployerRequest employerRequest) {
		return Ok(() -> this.authService.registerForEmployer(employerRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserForLoginRequest loginRequest) {
		return Ok(() -> this.authService.login(loginRequest));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
		return Ok(() -> this.authService.forgotPassword(request));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
		return Ok(() -> this.authService.resetPassword(request));
	}

	@GetMapping("/validate-reset-token")
	public ResponseEntity<?> validateResetToken(@RequestParam String token) {
		return Ok(() -> this.authService.validateResetToken(token));
	}
}

