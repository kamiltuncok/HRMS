package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.service.CityService;
import kodlamaio.HRMS.entities.concretes.City;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin
@RequiredArgsConstructor
public class CitiesController extends BaseController {

	private final CityService cityService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.cityService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody City city) {
		return Ok(() -> this.cityService.add(city));
	}
}
