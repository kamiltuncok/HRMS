package kodlamaio.HRMS.api.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.JobAdvertisementService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;

@RestController
@RequestMapping("/api/jobadvertisements")
@CrossOrigin
public class JobAdvertisementsController extends BaseController {
	
	private JobAdvertisementService jobAdvertisementService;

	
	public JobAdvertisementsController(JobAdvertisementService jobAdvertisementService) {
		super();
		this.jobAdvertisementService = jobAdvertisementService;
	}

	@GetMapping("/getactivejobadvertisement")
	public DataResult<List<JobAdvertisement>> getByStatus() {
		return this.jobAdvertisementService.getActiveJobAdvertisement();
	}

	@GetMapping("/getactivejobadvertisementbydate")
	DataResult<List<JobAdvertisement>> getByStatusTrueOrderByCreateDateDesc() {
		return this.jobAdvertisementService.getActiveJobAdvertisementOrderedByDesc();
	}

	@GetMapping("/getactivejobadvertisementbycompany")
	DataResult<List<JobAdvertisement>> getByStatusTrueAndEmployer_CompanyName(String companyName) {
		return this.jobAdvertisementService.getActiveJobAdvertisementWithSpecificCompany(null);
	}

	@PostMapping("/updatejobadvertisementstatusbyid")
	@Transactional
	public Result UpdateJobAdvertisementSetStatusForEmployer_Id(@RequestParam int jobAdvertisementId) {
		return this.jobAdvertisementService.updateJobAdvertisementStatus(jobAdvertisementId);
	}
	
	@GetMapping("/getjobadvertisementbyid")
	DataResult<JobAdvertisement> getJobAdvertisementById(@RequestParam int jobAdvertisementId) {
		return this.jobAdvertisementService.getJobAdvertisementById(jobAdvertisementId);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Ok(()-> this.jobAdvertisementService.getAll());
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody JobAdvertisement jobAdvertisement){
		return Ok(()->this.jobAdvertisementService.add(jobAdvertisement));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody JobAdvertisement jobAdvertisement){
		return Ok(()->this.jobAdvertisementService.update(jobAdvertisement));
	}
	
}
