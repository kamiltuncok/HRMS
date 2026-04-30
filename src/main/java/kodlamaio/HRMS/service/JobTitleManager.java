package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.JobTitleDao;
import kodlamaio.HRMS.entities.concretes.JobTitle;
import kodlamaio.HRMS.dto.JobTitleResponse;
import kodlamaio.HRMS.mapper.JobTitleMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobTitleManager implements JobTitleService {

	private final JobTitleDao jobTitleDao;
	private final JobTitleMapper jobTitleMapper;

	@Override
	public DataResult<List<JobTitleResponse>> getAll() {
		return new SuccessDataResult<>(jobTitleMapper.toResponseList(jobTitleDao.findAll()), "Job titles have been listed successfully.");
	}

	public boolean isPositionSavedBefore(JobTitle jobTitle) {
		return !jobTitleDao.findByTitleContaining(jobTitle.getTitle()).isEmpty();
	}

	@Override
	public Result add(JobTitle jobTitle) {
		if (isPositionSavedBefore(jobTitle)) {
			return new ErrorResult("This job position already exists in the system.");
		}

		jobTitleDao.save(jobTitle);
		return new SuccessResult("Job position has been added successfully.");
	}

	@Override
	public DataResult<List<JobTitleResponse>> getByCategory(Long categoryId) {
		var jobTitles = jobTitleDao.findByCategory_Id(categoryId);
		return new SuccessDataResult<>(jobTitleMapper.toResponseList(jobTitles), "Job titles for category listed successfully.");
	}
}
