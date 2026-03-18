package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kodlamaio.HRMS.dto.JobExperienceRequest;
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
    public ResponseEntity<?> add(@RequestBody JobExperienceRequest jobExperienceRequest) {
        return Ok(() -> this.jobExperienceService.add(jobExperienceRequest));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        return Ok(() -> this.jobExperienceService.delete(id));
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
