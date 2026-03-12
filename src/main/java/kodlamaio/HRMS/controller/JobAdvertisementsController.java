package kodlamaio.HRMS.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kodlamaio.HRMS.service.*;
import kodlamaio.HRMS.dto.JobAdvertisementRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobadvertisements")
@CrossOrigin
@RequiredArgsConstructor
public class JobAdvertisementsController extends BaseController {

	private final JobAdvertisementService jobAdvertisementService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAll() {
		return Ok(() -> this.jobAdvertisementService.getAll());
	}

	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody JobAdvertisementRequest jobAdvertisementRequest) {
		return Ok(() -> this.jobAdvertisementService.add(jobAdvertisementRequest));
	}

	@GetMapping("/getactive")
	public ResponseEntity<?> getActive() {
		return Ok(() -> this.jobAdvertisementService.getActiveJobAdvertisement());
	}

	@GetMapping("/getactivebydesc")
	public ResponseEntity<?> getActiveByDesc() {
		return Ok(() -> this.jobAdvertisementService.getActiveJobAdvertisementOrderedByDesc());
	}

	@GetMapping("/getactivebycompany")
	public ResponseEntity<?> getActiveByCompany(@RequestParam String companyName) {
		return Ok(() -> this.jobAdvertisementService.getActiveJobAdvertisementWithSpecificCompany(companyName));
	}

	@PutMapping("/updatestatus")
	public ResponseEntity<?> updateStatus(@RequestParam Long id) {
		return Ok(() -> this.jobAdvertisementService.updateJobAdvertisementStatus(id));
	}

	@GetMapping("/getbyid")
	public ResponseEntity<?> getById(@RequestParam Long id) {
		return Ok(() -> this.jobAdvertisementService.getJobAdvertisementById(id));
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestParam Long id, @Valid @RequestBody JobAdvertisementRequest request) {
		return Ok(() -> this.jobAdvertisementService.update(id, request));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam Long id) {
		return Ok(() -> this.jobAdvertisementService.delete(id));
	}
}
