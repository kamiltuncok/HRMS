package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.Photo;

public interface PhotoService {

	DataResult<List<Photo>> getAll();
	
	Result add(Photo photo,MultipartFile multipartFile);
	
	
}
