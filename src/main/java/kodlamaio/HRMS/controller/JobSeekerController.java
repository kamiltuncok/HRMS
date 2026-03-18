package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.service.JobSeekerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobseekers")
@CrossOrigin
@RequiredArgsConstructor
public class JobSeekerController extends BaseController {

	private final JobSeekerService jobSeekerService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.jobSeekerService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody JobSeekerRequest jobSeekerRequest) {
		return Ok(() -> this.jobSeekerService.add(jobSeekerRequest));
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestParam Long id, @Valid @RequestBody kodlamaio.HRMS.dto.JobSeekerUpdateRequest jobSeekerRequest) {
		return Ok(() -> this.jobSeekerService.update(id, jobSeekerRequest));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Long id) {
		return Ok(() -> this.jobSeekerService.delete(id));
	}

	@GetMapping("/getbyid")
	public ResponseEntity<?> getById(@RequestParam Long id) {
		return Ok(() -> this.jobSeekerService.getJobSeekerById(id));
	}
}
