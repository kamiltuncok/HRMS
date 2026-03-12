package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.EmployerResponse;
import kodlamaio.HRMS.entities.concretes.Employer;
import kodlamaio.HRMS.mapper.EmployerMapper;
import kodlamaio.HRMS.repository.EmployerDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployerManager implements EmployerService {

	private final EmployerDao employerDao;
	private final EmployerMapper mapper;

	@Override
	public DataResult<List<EmployerResponse>> getAll() {
		var entities = this.employerDao.findAll();
		return new SuccessDataResult<>(mapper.toResponseList(entities), "Employers listed successfully.");
	}

	@Override
	public DataResult<EmployerResponse> add(EmployerRequest request) {
		Employer entity = mapper.toEntity(request);
		var savedEntity = this.employerDao.save(entity);
		return new SuccessDataResult<>(mapper.toResponse(savedEntity), "Employer has been added successfully.");
	}

	@Override
	public DataResult<EmployerResponse> update(Long id, EmployerRequest request) {
		return this.employerDao.findById(id)
				.<DataResult<EmployerResponse>>map(existing -> {
					Employer updatedEntity = mapper.toEntity(request);
					updatedEntity.setId(id);
					var savedEntity = this.employerDao.save(updatedEntity);
					return new SuccessDataResult<>(mapper.toResponse(savedEntity),
							"Employer has been updated successfully.");
				})
				.orElseGet(() -> new ErrorDataResult<>("Employer not found."));
	}

	@Override
	public Result delete(Long id) {
		if (!this.employerDao.existsById(id)) {
			return new ErrorResult("Employer not found.");
		}
		this.employerDao.deleteById(id);
		return new SuccessResult("Employer has been deleted successfully.");
	}

	@Override
	public Result validate(EmployerRequest request) throws Exception {
		if (request.companyName().isBlank()) {
			return new ErrorResult("Company name cannot be blank.");
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<EmployerResponse> getEmployerById(Long employerId) {
		return this.employerDao.findById(employerId)
				.<DataResult<EmployerResponse>>map(entity -> new SuccessDataResult<>(mapper.toResponse(entity),
						"Employer retrieved successfully."))
				.orElseGet(() -> new ErrorDataResult<>("Employer not found."));
	}
}
