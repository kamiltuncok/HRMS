package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.JobAdvertisementService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;

import kodlamaio.HRMS.dataaccess.abstracts.JobAdvertisementDao;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;

@Service
public class JobAdvertisementManager implements JobAdvertisementService {
	private JobAdvertisementDao jobAdvertisementDao;
	
	@Autowired
	public JobAdvertisementManager(JobAdvertisementDao jobAdvertisementDao) {
		super();
		this.jobAdvertisementDao = jobAdvertisementDao;
	}
	
	@Override
	public DataResult<List<JobAdvertisement>> getAll() throws Exception {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.findAll(),"İş ilanları listelendi");
	}

	@Override
	public DataResult<JobAdvertisement> add(JobAdvertisement jobAdvertisement) throws Exception {
		JobAdvertisement jobAdvert=this.jobAdvertisementDao.save(jobAdvertisement);
		return new SuccessDataResult<JobAdvertisement>(jobAdvert,"İş ilanı eklendi");
	}

	@Override
	public Result delete(JobAdvertisement entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DataResult<JobAdvertisement> update(JobAdvertisement entity) throws Exception {
		// TODO Auto-generated method stub
		JobAdvertisement current=this.jobAdvertisementDao.getById(entity.getId());
		if(current==null) {
			new ErrorDataResult<JobAdvertisement>("İş ilanı bulunamadı");
		}
		current.setCity(entity.getCity());
		current.setEmployer(entity.getEmployer());
		current.setJobPosition(entity.getJobPosition());
		current.setTypeOfWork(entity.getTypeOfWork());
		current.setStartDate(entity.getStartDate());
		current.setEndDate(entity.getEndDate());
		current.setJobPosition(entity.getJobPosition());
		current.setMinSalary(entity.getMinSalary());
		current.setMaxSalary(entity.getMaxSalary());
		current.setFreePositionAmount(entity.getFreePositionAmount());
		current.setDescription(entity.getDescription());
		this.jobAdvertisementDao.save(current);
		return new SuccessDataResult<JobAdvertisement>(null,"İş ilanı güncellendi");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getActiveJobAdvertisement() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByStatusTrue(), "Active job advertisements are successfully listed.");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getActiveJobAdvertisementOrderedByDesc() {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByStatusTrueOrderByStartDateDesc(),
				"Active job advertisements are successfully listed by date.");
	}

	@Override
	public DataResult<List<JobAdvertisement>> getActiveJobAdvertisementWithSpecificCompany(String companyName) {
		return new SuccessDataResult<List<JobAdvertisement>>(this.jobAdvertisementDao.getByStatusTrueAndEmployer_CompanyName(companyName),
				"Job advertisements are listed by the desired company.");
	}

	@Override
	public Result updateJobAdvertisementStatus(int jobAdvertisementId) {

		this.jobAdvertisementDao.updateJobAdvertisementStatus(jobAdvertisementId);
		return new SuccessResult("Selected Job Advertisement is closed.");
	}

	@Override
	public DataResult<JobAdvertisement> getJobAdvertisementById(int jobAdvertisementId) {
		return new SuccessDataResult<JobAdvertisement>(this.jobAdvertisementDao.getJobAdvertisementById(jobAdvertisementId), "Query returned successfully.");
	}
}
