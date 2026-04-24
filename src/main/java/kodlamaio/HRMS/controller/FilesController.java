package kodlamaio.HRMS.controller;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
@RequiredArgsConstructor
public class FilesController extends BaseController {

    private final FileService fileService;

    @PostMapping("/upload-profile-image")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        return Ok(() -> this.fileService.uploadProfileImage(file));
    }
}
