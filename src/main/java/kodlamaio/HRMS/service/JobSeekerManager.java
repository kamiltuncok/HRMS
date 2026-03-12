package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.JobSeekerResponse;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.mapper.JobSeekerMapper;
import kodlamaio.HRMS.repository.JobSeekerDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerManager implements JobSeekerService {

	private final JobSeekerDao jobSeekerDao;
	private final JobSeekerMapper mapper;

	@Override
	public DataResult<List<JobSeekerResponse>> getAll() {
		var entities = this.jobSeekerDao.findAll();
		return new SuccessDataResult<>(mapper.toResponseList(entities), "Job seekers listed successfully.");
	}

	@Override
	public DataResult<JobSeekerResponse> add(JobSeekerRequest request) {
		JobSeeker entity = mapper.toEntity(request);
		var savedEntity = this.jobSeekerDao.save(entity);
		return new SuccessDataResult<>(mapper.toResponse(savedEntity), "Job seeker added successfully.");
	}

	@Override
	public DataResult<JobSeekerResponse> update(Long id, JobSeekerRequest request) {
		return this.jobSeekerDao.findById(id)
				.<DataResult<JobSeekerResponse>>map(existing -> {
					JobSeeker updatedEntity = mapper.toEntity(request);
					updatedEntity.setId(id);
					var savedEntity = this.jobSeekerDao.save(updatedEntity);
					return new SuccessDataResult<>(mapper.toResponse(savedEntity), "Job seeker updated successfully.");
				})
				.orElseGet(() -> new ErrorDataResult<>("Job seeker not found."));
	}

	@Override
	public Result delete(Long id) {
		if (!this.jobSeekerDao.existsById(id)) {
			return new ErrorResult("Job seeker not found.");
		}
		this.jobSeekerDao.deleteById(id);
		return new SuccessResult("Job seeker deleted successfully.");
	}

	@Override
	public Result validate(JobSeekerRequest request) throws Exception {
		if (request.identityNumber().length() != 11) {
			return new ErrorResult("Identity number must be 11 characters.");
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<JobSeekerResponse> getJobSeekerById(Long jobSeekerId) {
		return this.jobSeekerDao.findById(jobSeekerId)
				.<DataResult<JobSeekerResponse>>map(
						entity -> new SuccessDataResult<>(mapper.toResponse(entity), "Job seeker found."))
				.orElseGet(() -> new ErrorDataResult<>("Job seeker not found."));
	}
}
