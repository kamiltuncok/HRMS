package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kodlamaio.HRMS.entities.concretes.Resume;
import kodlamaio.HRMS.service.ResumeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@CrossOrigin
public class ResumesController extends BaseController {

    private final ResumeService resumeService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return Ok(() -> this.resumeService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Resume resume) {
        return Ok(() -> this.resumeService.add(resume));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Resume resume) {
        return Ok(() -> this.resumeService.update(resume));
    }

    @GetMapping("/getbyjobseekerid")
    public ResponseEntity<?> getResumeByJobSeeker_id(@RequestParam Long jobSeekerId) {
        return Ok(() -> this.resumeService.getResumeByJobSeeker_id(jobSeekerId));
    }
}
