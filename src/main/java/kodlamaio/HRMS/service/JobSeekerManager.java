package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.JobSeekerResponse;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.Role;
import kodlamaio.HRMS.mapper.JobSeekerMapper;
import kodlamaio.HRMS.repository.JobSeekerDao;
import kodlamaio.HRMS.repository.RoleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobSeekerManager implements JobSeekerService {

	private final JobSeekerDao jobSeekerDao;
	private final JobSeekerMapper mapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleDao roleDao;

	@Override
	public DataResult<List<JobSeekerResponse>> getAll() {
		var entities = this.jobSeekerDao.findAll();
		return new SuccessDataResult<>(mapper.toResponseList(entities), "Job seekers listed successfully.");
	}

	@Override
	public DataResult<JobSeekerResponse> add(JobSeekerRequest request) {
		JobSeeker entity = mapper.toEntity(request);
		entity.setPassword(passwordEncoder.encode(request.password()));
		
		Role role = roleDao.findByRoleName(Role.JOBSEEKER)
				.orElseThrow(() -> new RuntimeException("Role not found: JOBSEEKER"));
		entity.setRole(role);
		
		var savedEntity = this.jobSeekerDao.save(entity);
		return new SuccessDataResult<>(mapper.toResponse(savedEntity), "Job seeker added successfully.");
	}

	@Override
	public DataResult<JobSeekerResponse> update(Long id, kodlamaio.HRMS.dto.JobSeekerUpdateRequest request) {
		return this.jobSeekerDao.findById(id)
				.<DataResult<JobSeekerResponse>>map(existing -> {
					// Mutate the managed entity so this is an UPDATE. Building a new entity
					// and setId(id) makes Spring Data treat the null @Version as "new" and
					// INSERT with an existing id -> duplicate key ("Bu kayıt zaten mevcut").
					existing.setFirstName(request.firstName());
					existing.setLastName(request.lastName());
					existing.setBirthDate(request.birthDate());
					existing.setEmail(request.email());
					existing.setPhoneNumber(request.phoneNumber());
					// Only touch the profile image when provided, so account edits (which
					// don't send it) don't wipe an existing photo.
					if (request.profileImageUrl() != null) {
						existing.setProfileImageUrl(request.profileImageUrl());
					}
					var savedEntity = this.jobSeekerDao.save(existing);
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
