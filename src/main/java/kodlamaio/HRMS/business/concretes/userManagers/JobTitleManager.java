package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.JobTitleService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.JobTitleDao;
import kodlamaio.HRMS.entities.concretes.JobTitle;

@Service
public class JobTitleManager implements JobTitleService {

	private JobTitleDao jobTitleDao;

	@Autowired
	public JobTitleManager(JobTitleDao jobTitleDao) {
		super();
		this.jobTitleDao = jobTitleDao;
	}

	@Override
	public DataResult<List<JobTitle>> getAll() {

		return new SuccessDataResult<List<JobTitle>>(jobTitleDao.findAll(), "Ünvanlar Listelendi");

	}

	public boolean isPositionSavedBefore(JobTitle jobTitle) {
		if (jobTitleDao.findByTitleContaining(jobTitle.getTitle()).size() != 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Result add(JobTitle jobTitle) {
		if (isPositionSavedBefore(jobTitle)) {
			jobTitleDao.save(jobTitle);
			return new SuccessResult("İş Pozisyonu sisteme eklendi");

		} else {
			return new ErrorResult("Lütfen kayıtlı olmayan bir iş pozisyonu giriniz");
		}

	}

}



