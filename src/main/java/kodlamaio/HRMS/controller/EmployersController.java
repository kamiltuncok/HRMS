package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.service.EmployerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin
@RequiredArgsConstructor
public class EmployersController extends BaseController {

	private final EmployerService employerService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.employerService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody EmployerRequest employerRequest) {
		return Ok(() -> this.employerService.add(employerRequest));
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestParam Long id, @Valid @RequestBody EmployerRequest employerRequest) {
		return Ok(() -> this.employerService.update(id, employerRequest));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Long id) {
		return Ok(() -> this.employerService.delete(id));
	}

	@GetMapping("/getbyid")
	public ResponseEntity<?> getById(@RequestParam Long id) {
		return Ok(() -> this.employerService.getEmployerById(id));
	}
}
