package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dto.JobAdvertisementRequest;
import kodlamaio.HRMS.dto.JobAdvertisementResponse;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import kodlamaio.HRMS.mapper.JobAdvertisementMapper;
import kodlamaio.HRMS.repository.JobAdvertisementDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobAdvertisementManager implements JobAdvertisementService {

	private final JobAdvertisementDao jobAdvertisementDao;
	private final JobAdvertisementMapper mapper;

	@Override
	public DataResult<List<JobAdvertisementResponse>> getAll() {
		var entities = this.jobAdvertisementDao.findAll();
		return new SuccessDataResult<>(mapper.toResponseList(entities), "Job advertisements listed successfully.");
	}

	@Override
	public DataResult<JobAdvertisementResponse> add(JobAdvertisementRequest request) {
		JobAdvertisement entity = mapper.toEntity(request);
		var savedEntity = this.jobAdvertisementDao.save(entity);
		return new SuccessDataResult<>(mapper.toResponse(savedEntity), "Job advertisement added successfully.");
	}

	@Override
	public DataResult<JobAdvertisementResponse> update(Long id, JobAdvertisementRequest request) {
		return this.jobAdvertisementDao.findById(id)
				.<DataResult<JobAdvertisementResponse>>map(existing -> {
					JobAdvertisement updatedEntity = mapper.toEntity(request);
					updatedEntity.setId(id);
					var savedEntity = this.jobAdvertisementDao.save(updatedEntity);
					return new SuccessDataResult<>(mapper.toResponse(savedEntity),
							"Job advertisement updated successfully.");
				})
				.orElseGet(() -> new ErrorDataResult<>("Job advertisement not found."));
	}

	@Override
	public Result delete(Long id) {
		if (!this.jobAdvertisementDao.existsById(id)) {
			return new ErrorResult("Job advertisement not found.");
		}
		this.jobAdvertisementDao.deleteById(id);
		return new SuccessResult("Job advertisement deleted successfully.");
	}

	@Override
	public DataResult<List<JobAdvertisementResponse>> getActiveJobAdvertisement() {
		return new SuccessDataResult<>(mapper.toResponseList(this.jobAdvertisementDao.findByStatusTrue()),
				"Active job advertisements listed.");
	}

	@Override
	public DataResult<List<JobAdvertisementResponse>> getActiveJobAdvertisementOrderedByDesc() {
		return new SuccessDataResult<>(
				mapper.toResponseList(this.jobAdvertisementDao.findByStatusTrueOrderByStartDateDesc()),
				"Active job advertisements listed by date.");
	}

	@Override
	public DataResult<List<JobAdvertisementResponse>> getActiveJobAdvertisementWithSpecificCompany(String companyName) {
		return new SuccessDataResult<>(
				mapper.toResponseList(this.jobAdvertisementDao.findByStatusTrueAndEmployer_CompanyName(companyName)),
				"Job advertisements listed by company.");
	}

	@Override
	public Result updateJobAdvertisementStatus(Long jobAdvertisementId) {
		return this.jobAdvertisementDao.findById(jobAdvertisementId)
				.<Result>map(ad -> {
					this.jobAdvertisementDao.updateJobAdvertisementStatus(jobAdvertisementId);
					return new SuccessResult("Job advertisement status updated.");
				})
				.orElseGet(() -> new ErrorResult("Job advertisement not found."));
	}

	@Override
	public DataResult<JobAdvertisementResponse> getJobAdvertisementById(Long jobAdvertisementId) {
		return this.jobAdvertisementDao.findById(jobAdvertisementId)
				.<DataResult<JobAdvertisementResponse>>map(
						entity -> new SuccessDataResult<>(mapper.toResponse(entity), "Job advertisement found."))
				.orElseGet(() -> new ErrorDataResult<>("Job advertisement not found."));
	}
}
