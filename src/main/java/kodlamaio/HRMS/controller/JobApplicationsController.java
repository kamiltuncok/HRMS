package kodlamaio.HRMS.controller;

import kodlamaio.HRMS.dto.JobApplicationRequest;
import kodlamaio.HRMS.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobapplications")
@CrossOrigin
@RequiredArgsConstructor
public class JobApplicationsController extends BaseController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody JobApplicationRequest jobApplicationRequest) {
        return Ok(() -> this.jobApplicationService.add(jobApplicationRequest));
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return Ok(() -> this.jobApplicationService.getAll());
    }

    @GetMapping("/getbyjobseekerid")
    public ResponseEntity<?> getByJobSeekerId(@RequestParam Long jobSeekerId) {
        return Ok(() -> this.jobApplicationService.getByJobSeekerId(jobSeekerId));
    }
}
