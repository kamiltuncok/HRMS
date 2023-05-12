package kodlamaio.HRMS.core.verifications.abstracts;

import java.sql.Date;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.users.User;

public interface VerificationCodeService {
	public Result sendCode(String email);

	public Result ısVerified(boolean ısVerified, Date verifiedDate, User user);

	public Result save();
}
