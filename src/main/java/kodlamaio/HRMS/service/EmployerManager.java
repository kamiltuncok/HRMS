package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.EmployerResponse;
import kodlamaio.HRMS.entities.concretes.Employer;
import kodlamaio.HRMS.entities.concretes.Role;
import kodlamaio.HRMS.mapper.EmployerMapper;
import kodlamaio.HRMS.repository.EmployerDao;
import kodlamaio.HRMS.repository.RoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployerManager implements EmployerService {

	private final EmployerDao employerDao;
	private final EmployerMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleDao roleDao;

	@Override
	public DataResult<List<EmployerResponse>> getAll() {
		var entities = this.employerDao.findAll();
		return new SuccessDataResult<>(mapper.toResponseList(entities), "Employers listed successfully.");
	}

	@Override
	public DataResult<EmployerResponse> add(EmployerRequest request) {
		Employer entity = mapper.toEntity(request);
		entity.setPassword(passwordEncoder.encode(request.password()));
		
		Role role = roleDao.findByRoleName(Role.EMPLOYER)
				.orElseThrow(() -> new RuntimeException("Role not found: EMPLOYER"));
		entity.setRole(role);
		
		var savedEntity = this.employerDao.save(entity);
		return new SuccessDataResult<>(mapper.toResponse(savedEntity), "Employer has been added successfully.");
	}

	@Override
	public DataResult<EmployerResponse> update(Long id, kodlamaio.HRMS.dto.EmployerUpdateRequest request) {
		return this.employerDao.findById(id)
				.<DataResult<EmployerResponse>>map(existing -> {
					// Mutate the managed entity so this is an UPDATE. Building a new entity
					// and setId(id) makes Spring Data treat the null @Version as "new" and
					// INSERT with an existing id -> duplicate key ("Bu kayıt zaten mevcut").
					existing.setCompanyName(request.companyName());
					existing.setWebAddress(request.webAddress());
					existing.setPhoneNumber(request.phoneNumber());
					existing.setEmail(request.email());
					// Only touch the profile image when provided, so account edits (which
					// don't send it) don't wipe an existing photo.
					if (request.profileImageUrl() != null) {
						existing.setProfileImageUrl(request.profileImageUrl());
					}

					var savedEntity = this.employerDao.save(existing);
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
