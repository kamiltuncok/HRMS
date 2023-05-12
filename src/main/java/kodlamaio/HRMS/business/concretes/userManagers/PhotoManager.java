package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kodlamaio.HRMS.business.abstracts.userServices.PhotoService;
import kodlamaio.HRMS.core.adapters.abstracts.CloudService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.PhotoDao;
import kodlamaio.HRMS.entities.concretes.Photo;
import lombok.var;

@Service
public class PhotoManager implements PhotoService {

	private PhotoDao photoDao;
	private CloudService cloudService;
	
	

	@Autowired
	public PhotoManager(PhotoDao photoDao, CloudService cloudService) {
		super();
		this.photoDao = photoDao;
		this.cloudService = cloudService;
	}

	@Override
	public DataResult<List<Photo>> getAll() {
		
		return new SuccessDataResult<List<Photo>>(this.photoDao.findAll(), "Photos listed");
	}

	@Override
	public Result add(Photo photo, MultipartFile multipartFile) {
		
		var result = this.cloudService.uploadPhoto(multipartFile).getData();
		
		photo.setPhotoUrl(result.get("url"));
		
		this.photoDao.save(photo);
		return new SuccessResult("Photo added");
	}


	
}
