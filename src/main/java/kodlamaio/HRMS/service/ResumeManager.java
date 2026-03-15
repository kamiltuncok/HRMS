package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.ResumeDao;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.entities.concretes.Photo;
import kodlamaio.HRMS.entities.concretes.Resume;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeManager implements ResumeService {

	private final ResumeDao resumeDao;

	@Override
	public DataResult<List<Resume>> getAll() {
		return new SuccessDataResult<>(this.resumeDao.findAll(), "Resumes listed successfully.");
	}

	@Override
	public DataResult<Resume> add(Resume resume) {
		this.resumeDao.save(resume);
		return new SuccessDataResult<>(resume, "Resume has been added successfully.");
	}

	@Override
	public Result delete(Resume entity) {
		this.resumeDao.delete(entity);
		return new SuccessResult("Resume has been deleted successfully.");
	}

	@Override
	public DataResult<Resume> update(Resume entity) {
		return this.resumeDao.findById(entity.getId())
				.<DataResult<Resume>>map(current -> {
					current.setAddress(entity.getAddress());
					current.setBirthDate(entity.getBirthDate());
					current.setPhone(entity.getPhone());
					current.setGithubUrl(entity.getGithubUrl());
					current.setLinkedinUrl(entity.getLinkedinUrl());
					current.setSummary(entity.getSummary());
					current.setPortfolioUrl(entity.getPortfolioUrl());
					this.resumeDao.save(current);
					return new SuccessDataResult<>(current, "Resume has been updated successfully.");
				})
				.orElseGet(() -> new ErrorDataResult<>("Resume not found."));
	}

	public DataResult<Resume> getResumeByJobSeeker_id(Long jsId) {
		Resume resume = this.resumeDao.findByJobSeeker_Id(jsId);
		return new SuccessDataResult<>(resume, "Resume retrieved successfully.");
	}

	@Override
	public Photo getUserPhoto(Long id) {
		return this.resumeDao.findById(id)
				.map(Resume::getPhoto)
				.orElse(null);
	}

	@Override
	public Result updateResumePhoto(Long id, Photo photo) {
		Resume current = this.resumeDao.findByJobSeeker_Id(id);
		if (current == null) {
			return new ErrorResult("Resume not found.");
		}
		current.setPhoto(photo);
		this.resumeDao.save(current);
		return new SuccessResult("Photo uploaded successfully.");
	}

	@Override
	public Result deleteResumePhoto(Long id) {
		Resume current = this.resumeDao.findByJobSeeker_Id(id);
		if (current == null) {
			return new ErrorResult("Resume not found.");
		}
		current.setPhoto(null);
		this.resumeDao.save(current);
		return new SuccessResult("Photo has been removed successfully.");
	}
}
