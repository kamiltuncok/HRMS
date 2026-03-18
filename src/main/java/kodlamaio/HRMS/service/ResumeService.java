package kodlamaio.HRMS.service;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.dto.ResumeRequest;
import kodlamaio.HRMS.entities.concretes.Photo;
import kodlamaio.HRMS.entities.concretes.Resume;

public interface ResumeService {

	DataResult<List<Resume>> getAll();

	DataResult<Resume> getResumeByJobSeeker_id(Long jsId);

	DataResult<Resume> add(ResumeRequest resumeRequest);

	Result delete(Resume resume);

	DataResult<Resume> update(ResumeRequest resumeRequest);

	Photo getUserPhoto(Long id);

	Result updateResumePhoto(Long id, Photo photo);

	Result deleteResumePhoto(Long id);

	DataResult<String> uploadPhoto(Long jobSeekerId, org.springframework.web.multipart.MultipartFile file);

	DataResult<String> uploadCv(Long jobSeekerId, org.springframework.web.multipart.MultipartFile file);
}
