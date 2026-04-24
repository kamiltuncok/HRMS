package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileManager implements FileService {

    private static final String UPLOAD_DIR = "uploads/profiles/";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Override
    public DataResult<String> uploadProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new ErrorDataResult<>("File is empty.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return new ErrorDataResult<>("File size exceeds the 5MB limit.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/jpg") || contentType.equals("image/png") || contentType.equals("image/webp"))) {
            return new ErrorDataResult<>("Invalid file type. Only JPG, PNG, and WEBP images are allowed.");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = ".png";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String fileName = UUID.randomUUID().toString() + extension;
            Path filePath = uploadPath.resolve(fileName);
            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/profiles/")
                    .path(fileName)
                    .toUriString();

            return new SuccessDataResult<>(fileDownloadUri, "Image uploaded successfully.");
        } catch (IOException e) {
            return new ErrorDataResult<>("Could not save the image: " + e.getMessage());
        }
    }
}
