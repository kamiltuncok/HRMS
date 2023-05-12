package kodlamaio.HRMS.business.concretes.userManagers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.EmployerService;
import kodlamaio.HRMS.core.business.BusinessRules;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.userDaos.EmployerDao;
import kodlamaio.HRMS.entities.concretes.users.Employer;

@Service
public class EmployerManager implements EmployerService {

	private EmployerDao employerDao;
	//private ValidationService validationService;
	//private VerificationCodeEmployerService verificationCodeEmployerService;
	//private EmployeeConfirmEmployerService employeeConfirmEmployerService;

	@Autowired
	public EmployerManager(EmployerDao employerDao) {
		super();
		this.employerDao = employerDao;
		//this.validationService = validationService;
		//this.verificationCodeEmployerService = verificationCodeEmployerService;
		//this.employeeConfirmEmployerService = employeeConfirmEmployerService;

	}

  /*	@Override
	public Result save(Employer employer) {
		if (validationService.isEmailUsed(employer) && validationService.isAllFullEmployer(employer)
				&& validationService.isDomainEqualEmail(employer)
				&& validationService.isPasswordEqualRepeat(employer)) {
			employerDao.save(employer);

			verificationCodeEmployerService.sendCode(employer.getEmail());

			if (verificationCodeEmployerService.ısVerified(true, new Date(2022), employer).isSuccess()
					&& employeeConfirmEmployerService.confirmedEmployer(employer).isSuccess()) {
				return new SuccessResult(verificationCodeEmployerService.sendCode(employer.getEmail()).getMessage()
						+ "--> Onaylandı " + "--> İş veren sisteme eklendi");
			}

		} else if (!validationService.isAllFullEmployer(employer)) {
			return new ErrorResult("Lütfen boş alan bırakmayınız.");
		} else if (!validationService.isPasswordEqualRepeat(employer)) {
			return new ErrorResult("şifre tekrarı ve şifre eşit değildir.");
		} else if (!validationService.isEmailUsed(employer)) {
			return new ErrorResult("Emil daha önceden kullanılmış.");
		} else if (!validationService.isDomainEqualEmail(employer)) {
			return new ErrorResult("Web domain'i ve Email eşit olmalıdır.");
		}
		return new ErrorResult();

	}
	*/
	
	@Override
	public DataResult<List<Employer>> getAll() {
		// TODO Auto-generated method stub
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(),"İşveren listelendi");
	}

	@Override
	public DataResult<Employer> add(Employer employer) {
		Employer emp=this.employerDao.save(employer);
		return new SuccessDataResult<Employer>(emp,"İşveren Eklendi");
		
	}

	@Override
	public Result delete(Employer entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<Employer> update(Employer entity) {
		Employer current=this.employerDao.getById(entity.getId());
		
		current.setCompanyName(entity.getCompanyName());
		current.setPhoneNumber(entity.getPhoneNumber());
		current.setWebAdress(entity.getWebAdress());
		//current.setUser(new User());
		this.employerDao.save(current);
		return new SuccessDataResult<Employer>(null,"Güncellendi");
	}

	@Override
	public Result isNull(Employer employer) throws Exception {

		if(employer.getCompanyName().isBlank() || employer.getWebAdress().isBlank()|| employer.getPhoneNumber().isBlank()
			|| employer.getUser().getEmail().isBlank() || employer.getUser().getPassword().isBlank()) {
			
			return new ErrorResult("Tüm alanlar zorunludur!");
		}
		return new SuccessResult();
	}

	@Override
	public Result validate(Employer employer) throws Exception {
		Result result=BusinessRules.Run(isNull(employer));
		if(result!=null) {
			return result;
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<Employer> getEmployerById(int employerId) throws Exception {
		// TODO Auto-generated method stub
		return new SuccessDataResult<Employer>(this.employerDao.getEmployerById(employerId),"İşveren getirildi");
	}


}


