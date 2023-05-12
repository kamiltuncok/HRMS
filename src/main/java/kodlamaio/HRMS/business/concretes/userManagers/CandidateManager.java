package kodlamaio.HRMS.business.concretes.userManagers;



import java.sql.Date;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.CandidateService;
import kodlamaio.HRMS.core.adapters.abstracts.CandidateCheckService;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.validations.abstracts.ValidationService;
import kodlamaio.HRMS.core.verifications.abstracts.VerificationCodeCandidateService;
import kodlamaio.HRMS.dataaccess.abstracts.userDaos.CandidateDao;
import kodlamaio.HRMS.entities.concretes.users.Candidate;

@Service
public class CandidateManager implements CandidateService {

	private CandidateDao candidateDao;
	private CandidateCheckService candidateCheckService;
	private ValidationService validationService;
	private VerificationCodeCandidateService verificationCodeCandidateService;

	@Autowired
	public CandidateManager(CandidateDao candidateDao,@Qualifier("candidateCheckManager") CandidateCheckService candidateCheckService,
			ValidationService validationService, VerificationCodeCandidateService verificationCodeCandidateService) {
		super();
		this.candidateDao = candidateDao;
		this.candidateCheckService = candidateCheckService;
		this.validationService = validationService;
		this.verificationCodeCandidateService = verificationCodeCandidateService;
	}

	@Override
	public Result save(Candidate candidate) throws Exception {
		if (candidateCheckService.CheckIfRealCandidate(candidate) && validationService.isIdentityNumberUsed(candidate)
				&& validationService.isEmailUsed(candidate) && validationService.isAllFullCandidate(candidate)
				&& validationService.isPasswordEqualRepeat(candidate)) {
			candidateDao.save(candidate);

			verificationCodeCandidateService.sendCode(candidate.getEmail());

			if (verificationCodeCandidateService.ısVerified(true, new Date(2022), candidate).isSuccess()) {
				return new SuccessResult(verificationCodeCandidateService.sendCode(candidate.getEmail()).getMessage()
						+ "--> Onaylandı " + "--> Aday sisteme eklendi");
			}

		} else if (!validationService.isAllFullCandidate(candidate)) {
			return new ErrorResult("Lütfen boş alan bırakmayınız.");
		} else if (!validationService.isPasswordEqualRepeat(candidate)) {
			return new ErrorResult("şifre tekrarı ve şifre eşit değildir.");
		} else if (!candidateCheckService.CheckIfRealCandidate(candidate)) {
			return new ErrorResult("Mernis doğrulaması gerçekleşmedi.");
		} else if (!validationService.isIdentityNumberUsed(candidate)) {
			return new ErrorResult("Kimlik numarası daha önceden kullanılmış.");
		} else if (!validationService.isEmailUsed(candidate)) {
			return new ErrorResult("Emil daha önceden kullanılmış.");
		}
		return new ErrorResult();

	}
	
	

	

	

}
