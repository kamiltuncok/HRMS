package kodlamaio.HRMS.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.JobSeekerService;

@RequestMapping("/api/jobseekrs")
@RestController
@CrossOrigin
public class JobSeekerController extends BaseController{
	
	private JobSeekerService jobSeekerService;
	
	@Autowired
	public JobSeekerController(JobSeekerService jobSeekerService) {
		super();
		this.jobSeekerService = jobSeekerService;
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		 return Ok(()->this.jobSeekerService.getAll());
		 
	}
	
	
}