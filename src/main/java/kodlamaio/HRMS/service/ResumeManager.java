package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.ResumeDao;
import kodlamaio.HRMS.dto.ResumeRequest;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.Photo;
import kodlamaio.HRMS.entities.concretes.Resume;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResumeManager implements ResumeService {

	private final ResumeDao resumeDao;
	private final PhotoService photoService;

	@Autowired
	public ResumeManager(ResumeDao resumeDao, PhotoService photoService) {
		this.resumeDao = resumeDao;
		this.photoService = photoService;
	}

	@Override
	public DataResult<List<Resume>> getAll() {
		return new SuccessDataResult<>(this.resumeDao.findAll(), "Resumes listed successfully.");
	}

	@Override
	public DataResult<Resume> add(ResumeRequest request) {
		Resume resume = new Resume();
		if (request.id() != null) {
			resume.setId(request.id());
		}
		mapDtoToEntity(request, resume);
		
		JobSeeker js = new JobSeeker();
		js.setId(request.jobSeekerId());
		resume.setJobSeeker(js);
		
		this.resumeDao.save(resume);
		return new SuccessDataResult<>(resume, "Resume has been added successfully.");
	}

	@Override
	public Result delete(Resume entity) {
		this.resumeDao.delete(entity);
		return new SuccessResult("Resume has been deleted successfully.");
	}

	@Override
	public DataResult<Resume> update(ResumeRequest request) {
		return this.resumeDao.findById(request.id())
				.<DataResult<Resume>>map(current -> {
					mapDtoToEntity(request, current);
					this.resumeDao.save(current);
					return new SuccessDataResult<>(current, "Resume has been updated successfully.");
				})
				.orElseGet(() -> new ErrorDataResult<>("Resume not found."));
	}

	private void mapDtoToEntity(ResumeRequest request, Resume resume) {
		resume.setBirthDate(request.birthDate());
		resume.setPhone(request.phone());
		resume.setGithubUrl(request.githubUrl());
		resume.setLinkedinUrl(request.linkedinUrl());
		resume.setSummary(request.summary());
		resume.setPortfolioUrl(request.portfolioUrl());
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

	@Override
	public DataResult<String> uploadPhoto(Long jobSeekerId, MultipartFile file) {
		Resume resume = this.resumeDao.findByJobSeeker_Id(jobSeekerId);
		if (resume == null) {
			return new ErrorDataResult<>("Resume not found for job seeker ID: " + jobSeekerId);
		}

		Photo photo = new Photo();
		var uploadResult = this.photoService.add(photo, file);
		if (!uploadResult.isSuccess()) {
			return new ErrorDataResult<>("Photo upload failed.");
		}

		resume.setPhoto(photo);
		this.resumeDao.save(resume);
		
		return new SuccessDataResult<>(photo.getPhotoUrl(), "Photo uploaded successfully.");
	}

	private final String UPLOAD_DIR = "uploads/cv/";

	@Override
	public DataResult<String> uploadCv(Long jobSeekerId, MultipartFile file) {
		Resume resume = this.resumeDao.findByJobSeeker_Id(jobSeekerId);
		if (resume == null) {
			return new ErrorDataResult<>("Resume not found for job seeker ID: " + jobSeekerId);
		}

		if (file.isEmpty() || file.getContentType() == null || !file.getContentType().equals("application/pdf")) {
			return new ErrorDataResult<>("Invalid file. Only PDF files are allowed.");
		}

		try {
			Path uploadPath = Paths.get(UPLOAD_DIR);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			if (resume.getCvFilePath() != null) {
				Path oldFile = Paths.get(resume.getCvFilePath());
				Files.deleteIfExists(oldFile);
			}

			String fileName = UUID.randomUUID().toString() + ".pdf";
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			resume.setCvFileName(file.getOriginalFilename());
			resume.setCvFilePath(filePath.toString());
			resume.setCvUploadDate(LocalDateTime.now());
			
			this.resumeDao.save(resume);

			return new SuccessDataResult<>("CV uploaded successfully.");
		} catch (IOException e) {
			return new ErrorDataResult<>("Could not save the file: " + e.getMessage());
		}
	}

	@Override
	public Resource downloadCv(Long jobSeekerId) {
		Resume resume = this.resumeDao.findByJobSeeker_Id(jobSeekerId);
		if (resume == null || resume.getCvFilePath() == null) {
			return null;
		}

		try {
			Path file = Paths.get(resume.getCvFilePath());
			Resource resource = new UrlResource(file.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
