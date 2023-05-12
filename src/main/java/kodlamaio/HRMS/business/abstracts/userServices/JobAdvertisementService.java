package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;

public interface JobAdvertisementService extends CrudService<JobAdvertisement> {
	DataResult<List<JobAdvertisement>> getActiveJobAdvertisement();
	DataResult<List<JobAdvertisement>> getActiveJobAdvertisementOrderedByDesc();
	DataResult<List<JobAdvertisement>> getActiveJobAdvertisementWithSpecificCompany(String companyName);
	Result updateJobAdvertisementStatus(int jobAdvertisementId);
	DataResult<JobAdvertisement> getJobAdvertisementById(int jobAdvertisementId);
}
