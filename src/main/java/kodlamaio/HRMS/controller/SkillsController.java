package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.service.*;
import kodlamaio.HRMS.entities.concretes.Skill;
import kodlamaio.HRMS.dto.SkillRequest;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/skills")
@RequiredArgsConstructor
public class SkillsController extends BaseController {

	private final SkillService skillService;

	@GetMapping(value = "/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.skillService.getAll());
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@RequestBody SkillRequest skillRequest) {
		return Ok(() -> this.skillService.add(skillRequest));
	}

	@PostMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestParam Long id) {
		return Ok(() -> this.skillService.delete(id));
	}
}
