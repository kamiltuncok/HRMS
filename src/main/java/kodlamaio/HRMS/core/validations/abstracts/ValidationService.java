package kodlamaio.HRMS.core.validations.abstracts;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.City;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import kodlamaio.HRMS.entities.concretes.users.Candidate;
import kodlamaio.HRMS.entities.concretes.users.Employer;
import kodlamaio.HRMS.entities.concretes.users.User;

public interface ValidationService {
	public boolean isIdentityNumberUsed(Candidate candidate);

	public boolean isEmailUsed(User user);

	public boolean isAllFullCandidate(Candidate candidate);

	public boolean isAllFullEmployer(Employer employer);

	public boolean isDomainEqualEmail(Employer employer);

	public boolean isPasswordEqualRepeat(User user);
	
	Result checkCity(City city);

	Result checkJobAdvertisement(JobAdvertisement jobAdvertisement);
}
