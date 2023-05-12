package kodlamaio.HRMS.core.adapters.abstracts;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kodlamaio.HRMS.core.utilities.results.DataResult;

public interface CloudService {

	DataResult<Map<String, String>> uploadPhoto(MultipartFile multipartFile);
	
	//delete metotunu da bir ara öğrenip yaz
}
