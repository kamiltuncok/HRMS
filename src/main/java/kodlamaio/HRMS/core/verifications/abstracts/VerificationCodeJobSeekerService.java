package kodlamaio.HRMS.core.verifications.abstracts;

import java.time.LocalDate;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.User;

public interface VerificationCodeJobSeekerService {
    Result sendCode(String email);

    Result isVerified(boolean isVerified, LocalDate verifiedDate, User user);

    Result save();
}
