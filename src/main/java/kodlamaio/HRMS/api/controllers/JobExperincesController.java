package kodlamaio.HRMS.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.business.abstracts.userServices.JobExperinceService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobExperince;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/jobexperinces")
public class JobExperincesController {

	private JobExperinceService jobExperinceService;

	@Autowired
	public JobExperincesController(JobExperinceService jobExperinceService) {
		super();
		this.jobExperinceService = jobExperinceService;
	}
	
	@GetMapping(value = "/getall")
	public DataResult<List<JobExperince>> getAll(){
		return this.jobExperinceService.getAll();
	}
	
	@PostMapping(value = "/add")
	public Result add(@RequestBody JobExperince jobExperince) {
		return this.jobExperinceService.add(jobExperince);
	}
	
	
	@GetMapping(value = "/getallbyemployeeiddateofend")
	public DataResult<List<JobExperince>> findAllByResume_IdOrderByDateOfEnd(@RequestParam int employeeId){
		return this.jobExperinceService.findAllByEmployee_IdOrderByDateOfEnd(employeeId);
	}
	
	@GetMapping(value = "/getallbyemployeeiddateofenddesc")
	public DataResult<List<JobExperince>> findAllByResume_IdOrderByDateOfEndDesc(@RequestParam int employeeId, @RequestParam Direction direction){
		return this.jobExperinceService.findAllByEmployee_IdOrderByDateOfEndDesc(employeeId, direction);
	}
}
