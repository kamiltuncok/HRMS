package kodlamaio.HRMS.core.verifications.concretes;

import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.verifications.abstracts.VerificationCodeCandidateService;
import kodlamaio.HRMS.dataaccess.abstracts.verificationCodeDaos.VerificationCodeCandidateDao;
import kodlamaio.HRMS.entities.concretes.users.User;
import kodlamaio.HRMS.entities.concretes.verificationCodes.VerificationCodeCandidate;

@Service
public class VerificationCodeCandidateManager implements VerificationCodeCandidateService {

	private VerificationCodeCandidate verificationCodeCandidate = new VerificationCodeCandidate();
	private VerificationCodeCandidateDao verificationCodeCandidateDao;

	@Autowired
	public VerificationCodeCandidateManager(VerificationCodeCandidateDao verificationCodeCandidateDao) {

		this.verificationCodeCandidateDao = verificationCodeCandidateDao;
	}

	@Override
	public Result sendCode(String email) {
		String code = String.valueOf(verificationCodeCandidate.getCandidateId() + Math.random());

		verificationCodeCandidate.setCode(code);

		return new SuccessResult(email + " adresine doğrulama kodu gönderildi.");
	}

	@Override
	public Result ısVerified(boolean isVerified, Date verifiedDate, User user) {
		verificationCodeCandidate.setVerified(true);
		verificationCodeCandidate.setVerifiedDate(verifiedDate);
		verificationCodeCandidate.setCandidateId(user.getUserId());
		System.out.println(verificationCodeCandidate.getCode() + " kodu, " + verificationCodeCandidate.getVerifiedDate()
				+ " tarihinde onaylandı.");

		return new SuccessResult();
	}

	@Override
	public Result save() {

		verificationCodeCandidateDao.save(verificationCodeCandidate);
		return new SuccessResult("Aday e-postasına gönderilen kodu onayladı.");
	}

}
