package kodlamaio.HRMS.core.validations.abstracts;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.City;
import kodlamaio.HRMS.entities.concretes.Employer;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import kodlamaio.HRMS.entities.concretes.User;

public interface ValidationService {
	boolean isEmailUsed(User user);

	boolean isAllFullEmployer(Employer employer);

	boolean isDomainEqualEmail(Employer employer);

	Result checkCity(City city);

	Result checkJobAdvertisement(JobAdvertisement jobAdvertisement);
}
