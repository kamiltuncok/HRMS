package kodlamaio.HRMS.core.validations.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.validations.abstracts.ValidationService;
import kodlamaio.HRMS.dataaccess.abstracts.userDaos.CandidateDao;
import kodlamaio.HRMS.dataaccess.abstracts.userDaos.UserDao;
import kodlamaio.HRMS.entities.concretes.City;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import kodlamaio.HRMS.entities.concretes.users.Candidate;
import kodlamaio.HRMS.entities.concretes.users.Employer;
import kodlamaio.HRMS.entities.concretes.users.User;

@Service
public class ValidationManager implements ValidationService {
	private CandidateDao candidateDao;
	private UserDao userDao;

	@Autowired
	public ValidationManager(CandidateDao candidateDao, UserDao userDao) {
		this.candidateDao = candidateDao;
		this.userDao = userDao;
	}

	@Override
	public boolean isIdentityNumberUsed(Candidate candidate) {
		if (candidateDao.findByIdentityNumberEquals(candidate.getIdentityNumber()).size() != 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isEmailUsed(User user) {
		if (userDao.findByEmailEquals(user.getEmail()).size() != 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isAllFullCandidate(Candidate candidate) {
		if ((candidate.getEmail().isEmpty()) || (candidate.getBirthYear() == 0) || (candidate.getFirstName().isEmpty())
				|| (candidate.getLastName().isEmpty()) || (candidate.getPassword().isEmpty())
				|| (candidate.getIdentityNumber().isEmpty())) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public boolean isAllFullEmployer(Employer employer) {
		if ((employer.getEmail().isEmpty()) || (employer.getCompanyName().isEmpty())
				|| (employer.getPhoneNumber().isEmpty()) || (employer.getWebAdress().isEmpty())
				|| (employer.getPassword().isEmpty())) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isDomainEqualEmail(Employer employer) {
	    String[] splitEmail = employer.getEmail().split("@");
	    String[] splitWebAddress = employer.getWebAdress().split("\\.");

	    if (splitEmail.length != 2 || splitWebAddress.length < 2) {
	        // Input is not well-formed
	        return false;
	    }

	    String emailDomain = splitEmail[1];
	    String webDomain = splitWebAddress[splitWebAddress.length - 2] + "." + splitWebAddress[splitWebAddress.length - 1];

	    System.out.println("Email domain: " + emailDomain);
	    System.out.println("Web domain: " + webDomain);
	    return emailDomain.equalsIgnoreCase(webDomain);
	}

	@Override
	public boolean isPasswordEqualRepeat(User user) {
		if (user.getPassword().equals(user.getRepeatPassword())) {
			return true;
		} else {
			return false;
		}

	}
	
	@Override
	public Result checkCity(City city) {

		if (city.getName().isEmpty())
			return new ErrorResult("The city name can not be empty.");
		return new SuccessResult("Validation OK.");
	}

	@Override
	public Result checkJobAdvertisement(JobAdvertisement jobAdvertisement) {

		if (jobAdvertisement.getDescription().isEmpty())
			return new ErrorResult("Please fill the blanks completely.");
		if (jobAdvertisement.getFreePositionAmount() <= 0)
			return new ErrorResult("Free position amount can't be zero or less.");

		return new SuccessResult("Validation OK.");
	}
}
