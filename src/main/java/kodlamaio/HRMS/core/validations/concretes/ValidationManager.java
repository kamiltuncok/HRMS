package kodlamaio.HRMS.core.validations.concretes;

import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.validations.abstracts.ValidationService;
import kodlamaio.HRMS.entities.concretes.City;
import kodlamaio.HRMS.entities.concretes.Employer;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationManager implements ValidationService {

	private final UserDao userDao;

	@Override
	public boolean isEmailUsed(User user) {
		return !userDao.existsByEmail(user.getEmail());
	}

	@Override
	public boolean isAllFullEmployer(Employer employer) {
		return !employer.getEmail().isBlank() &&
				!employer.getCompanyName().isBlank() &&
				!employer.getPhoneNumber().isBlank() &&
				!employer.getWebAddress().isBlank() &&
				!employer.getPassword().isBlank();
	}

	@Override
	public boolean isDomainEqualEmail(Employer employer) {
		if (employer.getEmail() == null || employer.getWebAddress() == null) {
			return false;
		}

		String[] splitEmail = employer.getEmail().split("@");
		String[] splitWebAddress = employer.getWebAddress().split("\\.");

		if (splitEmail.length != 2 || splitWebAddress.length < 2) {
			return false;
		}

		String emailDomain = splitEmail[1];
		String webDomain = splitWebAddress[splitWebAddress.length - 2] + "."
				+ splitWebAddress[splitWebAddress.length - 1];

		return emailDomain.equalsIgnoreCase(webDomain);
	}

	@Override
	public Result checkCity(City city) {
		if (city.getName() == null || city.getName().isBlank())
			return new ErrorResult("City name cannot be empty.");
		return new SuccessResult("Validation OK.");
	}

	@Override
	public Result checkJobAdvertisement(JobAdvertisement jobAdvertisement) {
		if (jobAdvertisement.getDescription() == null || jobAdvertisement.getDescription().isBlank())
			return new ErrorResult("Description cannot be empty.");
		return new SuccessResult("Validation OK.");
	}
}
