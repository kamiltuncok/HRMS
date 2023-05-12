package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.ResumeService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.ResumeDao;
import kodlamaio.HRMS.entities.concretes.Photo;
import kodlamaio.HRMS.entities.concretes.Resume;

@Service
public class ResumeManager implements ResumeService{
	
	private ResumeDao resumeDao;
	
	@Autowired
	public ResumeManager(ResumeDao resumeDao) {
		super();
		this.resumeDao = resumeDao;
	}

	@Override
	public DataResult<List<Resume>> getAll() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<Resume>>(this.resumeDao.findAll(),"Cv listelendi");
	}

	@Override
	public DataResult<Resume> add(Resume resume) {

		this.resumeDao.save(resume);
		return new SuccessDataResult<Resume>("Cv eklendi");
	}

	@Override
	public Result delete(Resume entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<Resume> update(Resume entity) {

		Resume current=this.resumeDao.getById(entity.getResumeId());
		
		current.setAdress(entity.getAdress());
		current.setBirthDate(entity.getBirthDate());
		current.setPhone(entity.getPhone());
		current.setGithub(entity.getGithub());
		current.setLinkedin(entity.getLinkedin());
		this.resumeDao.save(current);
		return new SuccessDataResult<Resume>(null,"güncellendi");
	}

	@Override
	public DataResult<Resume> getResumeByJobSeeker_id(int jsId) {
		
		return new SuccessDataResult<Resume>(this.resumeDao.getResumeByJobSeeker_id(jsId),"Cv görüntülendi");
	}

	@Override
	public Photo getUserPhoto(int id) {

		Resume userResume=this.resumeDao.getById(id);
		return userResume.getPhoto();
	}

	@Override
	public Result updateResumePhoto(int id, Photo photo) throws Exception{
		try {
			
			Resume current=this.resumeDao.getResumeByJobSeeker_id(id);
			if(current == null) {
				return new ErrorResult("Cv bulunamadı");
			}
			current.setPhoto(photo);
			this.resumeDao.save(current);
			return new SuccessResult("Fotoğraf Yüklendi");
		}catch(Exception e) {
			return new ErrorResult("Fotoğraf yüklenirken hata oluştu");
		}
	}

	@Override
	public Result deleteResumePhoto(int id) throws Exception{
		
		Resume current=this.resumeDao.getResumeByJobSeeker_id(id);
		if(current == null) {
			return new ErrorResult("Özgeçmiş kaydı bulunamadı.");
		}
		current.setPhoto(null);
		this.resumeDao.save(current);
		return new SuccessResult("Fotoğraf kaldırıldı.");
	}

}
