package kodlamaio.HRMS.controller;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.service.*;
import kodlamaio.HRMS.entities.concretes.School;
import kodlamaio.HRMS.dto.SchoolRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/schools")
@RequiredArgsConstructor
public class SchoolsController extends BaseController {

	private final SchoolService schoolService;

	@GetMapping(value = "/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.schoolService.getAll());
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@RequestBody SchoolRequest schoolRequest) {
		return Ok(() -> this.schoolService.add(schoolRequest));
	}

	@PostMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestParam Long id) {
		return Ok(() -> this.schoolService.delete(id));
	}

	@GetMapping(value = "/getallbyresumeidgraduatedate")
	public ResponseEntity<?> findByResume_IdOrderByGraduateDateAsc(@RequestParam Long resumeId) {
		return Ok(() -> this.schoolService.findByResume_IdOrderByGraduateDateAsc(resumeId));
	}

	@GetMapping(value = "/getallbyresumeidgraduatedatedesc")
	public ResponseEntity<?> findByResume_IdOrderByGraduateDateDesc(@RequestParam Long resumeId,
			@RequestParam Direction direction) {
		return Ok(() -> this.schoolService.findByResume_IdOrderByGraduateDateDesc(resumeId, direction));
	}
}
