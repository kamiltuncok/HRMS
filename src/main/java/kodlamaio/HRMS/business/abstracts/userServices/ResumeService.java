package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.Photo;
import kodlamaio.HRMS.entities.concretes.Resume;


public interface ResumeService{
	
	DataResult<List<Resume>> getAll();
	DataResult<Resume> getResumeByJobSeeker_id(int jsId);
	Result add(Resume resume);
	Result delete(Resume resume);
	Result update(Resume resume);
	public Photo getUserPhoto(int id);
	public Result updateResumePhoto(int id, Photo photo) throws Exception;
	public Result deleteResumePhoto(int id) throws Exception;
}
