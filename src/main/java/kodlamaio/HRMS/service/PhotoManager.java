package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kodlamaio.HRMS.core.adapters.abstracts.CloudService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.PhotoDao;
import kodlamaio.HRMS.entities.concretes.Photo;

@Service
public class PhotoManager implements PhotoService {

	private final PhotoDao photoDao;
	private final CloudService cloudService;

	@Autowired
	public PhotoManager(PhotoDao photoDao, CloudService cloudService) {
		this.photoDao = photoDao;
		this.cloudService = cloudService;
	}

	@Override
	public DataResult<List<Photo>> getAll() {
		return new SuccessDataResult<>(this.photoDao.findAll(), "Photos listed successfully.");
	}

	@Override
	public Result add(Photo photo, MultipartFile multipartFile) {
		var result = this.cloudService.uploadPhoto(multipartFile).getData();
		photo.setPhotoUrl(result.get("url"));
		this.photoDao.save(photo);
		return new SuccessResult("Photo has been added successfully.");
	}
}
