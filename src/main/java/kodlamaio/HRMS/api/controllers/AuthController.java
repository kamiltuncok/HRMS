package kodlamaio.HRMS.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.AuthService;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.users.Employer;
import kodlamaio.HRMS.entities.dtos.UserForLoginDto;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
public class AuthController extends BaseController{
	
	private AuthService authService;
	
	@Autowired
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/registerforjobseeker")
	public ResponseEntity<?> registerForJobSeeker(@RequestBody JobSeeker jobSeeker) {
		return Ok(()->this.authService.registerForJobSeeker(jobSeeker));
		
	}
	@PostMapping("/registerforemployer")
	public ResponseEntity<?> registerForEmployer(@RequestBody Employer employer) {
		return Ok(()->this.authService.registerForEmployer(employer));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserForLoginDto loginDto) {
		return Ok(()->this.authService.login(loginDto));
		
	}
	
}
