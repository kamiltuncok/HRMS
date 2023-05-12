package kodlamaio.HRMS.api.controllers;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.EmployerService;

import kodlamaio.HRMS.entities.concretes.users.Employer;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin
public class EmployersController extends BaseController {
	private EmployerService employerService;

	@Autowired
	public EmployersController(EmployerService employerService) {
		super();
		this.employerService = employerService;
	}

	/*@PostMapping("/save")
	public Result save(@RequestBody Employer employer) {
		return employerService.save(employer);
	}
	*/
	
	@GetMapping("/getall")
	 public ResponseEntity<?> getAll(){
		
		return Ok(()->this.employerService.getAll());
	}
	
	@GetMapping("/getemployer")
	public ResponseEntity<?> getById(int employerId){
		
		return Ok(()->this.employerService.getEmployerById(employerId));
	}
	
	
	@PutMapping("/updateemployer")
	public ResponseEntity<?> update(@RequestBody Employer employer){
		return Ok(()->this.employerService.update(employer));
	}
	

}
