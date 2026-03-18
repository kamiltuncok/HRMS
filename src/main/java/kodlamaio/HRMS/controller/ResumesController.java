package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kodlamaio.HRMS.dto.ResumeRequest;
import kodlamaio.HRMS.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> add(@RequestBody ResumeRequest resumeRequest) {
        return Ok(() -> this.resumeService.add(resumeRequest));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ResumeRequest resumeRequest) {
        return Ok(() -> this.resumeService.update(resumeRequest));
    }

    @PostMapping("/uploadphoto")
    public ResponseEntity<?> uploadPhoto(@RequestParam Long jobSeekerId, @RequestParam MultipartFile file) {
        return Ok(() -> this.resumeService.uploadPhoto(jobSeekerId, file));
    }

    @PostMapping("/uploadcv")
    public ResponseEntity<?> uploadCv(@RequestParam Long jobSeekerId, @RequestParam MultipartFile file) {
        return Ok(() -> this.resumeService.uploadCv(jobSeekerId, file));
    }

    @GetMapping("/getbyjobseekerid")
    public ResponseEntity<?> getResumeByJobSeeker_id(@RequestParam Long jobSeekerId) {
        return Ok(() -> this.resumeService.getResumeByJobSeeker_id(jobSeekerId));
    }
}
