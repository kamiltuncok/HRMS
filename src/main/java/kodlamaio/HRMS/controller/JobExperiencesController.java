package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kodlamaio.HRMS.entities.concretes.JobExperience;
import kodlamaio.HRMS.service.JobExperienceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobexperiences")
@RequiredArgsConstructor
@CrossOrigin
public class JobExperiencesController extends BaseController {

    private final JobExperienceService jobExperienceService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return Ok(() -> this.jobExperienceService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody JobExperience jobExperience) {
        return Ok(() -> this.jobExperienceService.add(jobExperience));
    }

    @GetMapping("/getallbyresumeidasc")
    public ResponseEntity<?> findByResume_IdOrderByEndDateAsc(@RequestParam Long resumeId) {
        return Ok(() -> this.jobExperienceService.findByResume_IdOrderByEndDateAsc(resumeId));
    }

    @GetMapping("/getallbyresumeiddesc")
    public ResponseEntity<?> findByResume_IdOrderByEndDateDesc(@RequestParam Long resumeId) {
        return Ok(() -> this.jobExperienceService.findByResume_IdOrderByEndDateDesc(resumeId));
    }
}
