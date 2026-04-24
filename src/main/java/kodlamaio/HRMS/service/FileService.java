package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    DataResult<String> uploadProfileImage(MultipartFile file);
}
