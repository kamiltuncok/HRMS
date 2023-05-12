package kodlamaio.HRMS.business.abstracts.userServices;

import kodlamaio.HRMS.core.utilities.results.DataResult;

import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.users.Employer;
import kodlamaio.HRMS.entities.concretes.users.User;
import kodlamaio.HRMS.entities.dtos.UserForLoginDto;

public interface AuthService{
	
	DataResult<User> login(UserForLoginDto loginDto) throws Exception;
	Result registerForJobSeeker(JobSeeker jobSeeker) throws Exception;
	Result registerForEmployer(Employer employer) throws Exception;
}
