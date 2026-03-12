package kodlamaio.HRMS.service;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.Photo;
import kodlamaio.HRMS.entities.concretes.Resume;

public interface ResumeService {

	DataResult<List<Resume>> getAll();

	DataResult<Resume> getResumeByJobSeeker_id(Long jsId);

	DataResult<Resume> add(Resume resume);

	Result delete(Resume resume);

	DataResult<Resume> update(Resume resume);

	Photo getUserPhoto(Long id);

	Result updateResumePhoto(Long id, Photo photo);

	Result deleteResumePhoto(Long id);
}
