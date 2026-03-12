package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.service.*;
import kodlamaio.HRMS.entities.concretes.JobTitle;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobtitles")
@CrossOrigin
@RequiredArgsConstructor
public class JobTitlesController extends BaseController {

	private final JobTitleService jobTitleService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> jobTitleService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody JobTitle jobTitle) {
		return Ok(() -> jobTitleService.add(jobTitle));
	}
}
