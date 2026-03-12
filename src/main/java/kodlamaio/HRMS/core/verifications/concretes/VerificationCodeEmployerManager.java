package kodlamaio.HRMS.core.verifications.concretes;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.verifications.abstracts.VerificationCodeEmployerService;
import kodlamaio.HRMS.entities.concretes.Employer;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.entities.concretes.VerificationCodeEmployer;
import kodlamaio.HRMS.repository.VerificationCodeEmployerDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationCodeEmployerManager implements VerificationCodeEmployerService {

	private final VerificationCodeEmployerDao verificationCodeEmployerDao;

	@Override
	public Result sendCode(String email) {
		String code = "EMP-" + (int) (Math.random() * 1000000);
		// In a real app, this would save the code to DB/cache
		return new SuccessResult(email + " adresine doğrulama kodu gönderildi: " + code);
	}

	@Override
	public Result isVerified(boolean isVerified, LocalDate verifiedDate, User user) {
		if (user instanceof Employer employer) {
			VerificationCodeEmployer code = new VerificationCodeEmployer();
			code.setVerified(isVerified);
			code.setVerifiedDate(verifiedDate);
			code.setEmployer(employer);
			code.setCode("VERIFIED-BY-SYSTEM");
			verificationCodeEmployerDao.save(code);
		}
		return new SuccessResult("İş veren doğrulandı.");
	}

	@Override
	public Result save() {
		return new SuccessResult("İş veren e-postasına gönderilen kodu onayladı.");
	}
}
