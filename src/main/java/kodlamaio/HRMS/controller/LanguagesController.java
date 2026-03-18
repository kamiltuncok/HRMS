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
import kodlamaio.HRMS.entities.concretes.Language;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/languages")
@RequiredArgsConstructor
public class LanguagesController extends BaseController {

	private final LanguageService languageService;

	@GetMapping(value = "/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.languageService.getAll());
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@RequestBody Language language) {
		return Ok(() -> this.languageService.add(language));
	}

	@PostMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestParam Long id) {
		return Ok(() -> this.languageService.delete(id));
	}
}
