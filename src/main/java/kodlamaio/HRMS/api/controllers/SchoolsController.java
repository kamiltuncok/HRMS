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

import kodlamaio.HRMS.business.abstracts.userServices.SchoolService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.School;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/schools")
public class SchoolsController {

	private SchoolService schoolService;

	@Autowired
	public SchoolsController(SchoolService schoolService) {
		super();
		this.schoolService = schoolService;
	}
	
	@GetMapping(value = "/getall")
	public DataResult<List<School>> getAll(){
		return this.schoolService.getAll();
	}
	
	@PostMapping(value = "/add")
	public Result add(@RequestBody School school) {
		return this.schoolService.add(school);
	}
	
	@GetMapping(value = "/getallbyemployeeidgraduatedate")
	public DataResult<List<School>> findAllByEmployee_IdOrderByGraduateDate(@RequestParam int employeeId){
		return this.schoolService.findAllByEmployee_IdOrderByGraduateDate(employeeId);
	}
	
	@GetMapping(value = "/getallbyemployeeidgraduatedatedesc")
	public DataResult<List<School>> findAllByEmployee_IdOrderByGraduateDateDesc(@RequestParam int employeeId, @RequestParam Direction direction){
		return this.schoolService.findAllByEmployee_IdOrderByGraduateDateDesc(employeeId, direction);
	}
	
}

