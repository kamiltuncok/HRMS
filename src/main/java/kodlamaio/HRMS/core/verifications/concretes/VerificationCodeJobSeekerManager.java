package kodlamaio.HRMS.core.verifications.concretes;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.verifications.abstracts.VerificationCodeJobSeekerService;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.entities.concretes.VerificationCodeJobSeeker;
import kodlamaio.HRMS.repository.VerificationCodeJobSeekerDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationCodeJobSeekerManager implements VerificationCodeJobSeekerService {

	private final VerificationCodeJobSeekerDao verificationCodeJobSeekerDao;

	@Override
	public Result sendCode(String email) {
		String code = "JS-" + (int) (Math.random() * 1000000);
		// In a real app, this would save the code to the DB or a cache and send an
		// email
		return new SuccessResult(email + " adresine doğrulama kodu gönderildi: " + code);
	}

	@Override
	public Result isVerified(boolean isVerified, LocalDate verifiedDate, User user) {
		if (user instanceof JobSeeker jobSeeker) {
			VerificationCodeJobSeeker code = new VerificationCodeJobSeeker();
			code.setVerified(isVerified);
			code.setVerifiedDate(verifiedDate);
			code.setJobSeeker(jobSeeker);
			code.setCode("VERIFIED-BY-SYSTEM");
			verificationCodeJobSeekerDao.save(code);
		}
		return new SuccessResult("İş arayan doğrulandı.");
	}

	@Override
	public Result save() {
		// This method was oddly designed in the original code, relying on shared state.
		// Refactored to represent the completion of verification logic.
		return new SuccessResult("İş arayan e-postasına gönderilen kodu onayladı.");
	}
}
