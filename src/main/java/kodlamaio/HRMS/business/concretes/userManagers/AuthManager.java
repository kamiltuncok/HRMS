package kodlamaio.HRMS.business.concretes.userManagers;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import kodlamaio.HRMS.business.abstracts.userServices.AuthService;
import kodlamaio.HRMS.business.abstracts.userServices.EmployerService;
import kodlamaio.HRMS.business.abstracts.userServices.JobSeekerService;
import kodlamaio.HRMS.business.abstracts.userServices.UserService;
import kodlamaio.HRMS.core.business.BusinessRules;
import kodlamaio.HRMS.core.security.HashingHelper;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.Role;
import kodlamaio.HRMS.entities.concretes.users.Employer;
import kodlamaio.HRMS.entities.concretes.users.User;
import kodlamaio.HRMS.entities.dtos.UserForLoginDto;

@Service
public class AuthManager implements AuthService {
	
	

	private JobSeekerService jobSeekerService;
	private EmployerService employerService;
	private UserService userService;
	
	@Autowired
	public AuthManager(JobSeekerService jobSeekerService,EmployerService employerService,UserService userService) {
		super();
		this.jobSeekerService = jobSeekerService;
		this.employerService=employerService;
		this.userService=userService;
	}
	@Override
	public DataResult<User> login(UserForLoginDto loginDto) throws Exception {
		DataResult<User> UserToCheck= this.userService.getByEmail(loginDto.getEmail());
		if(UserToCheck.getData()==null) {
			return new ErrorDataResult<User>("E-posta hatalı!");
		}
		if(!HashingHelper.VerifyPasswordHash(loginDto.getPassword(), UserToCheck.getData().getPassword())) {
			return new ErrorDataResult<User>("Şifre hatalı!");
		}
		UserToCheck.getData().setPassword(null);
		return new SuccessDataResult<User>(UserToCheck.getData(),"Giriş başarılı");
	}

	@Override
	public Result registerForJobSeeker(JobSeeker jobSeeker) throws Exception {
		
		// iş kuralları
		Result result=BusinessRules.Run(this.userService.validate(jobSeeker.getUser()),this.jobSeekerService.validate(jobSeeker));
		if(result!=null) {
			return result;
		}
		
		jobSeeker.getUser().setRole(Role.JOBSEEKER());
		
		DataResult<User> userAddResult= this.userService.add(jobSeeker.getUser());
		if(!userAddResult.isSuccess()) {
			return userAddResult;
		}
		
		jobSeeker.getUser().setUserId(userAddResult.getData().getUserId());
		
		DataResult<JobSeeker> jobSeekerAddResult=this.jobSeekerService.add(jobSeeker);
		if(!jobSeekerAddResult.isSuccess()) {
			this.userService.delete(userAddResult.getData());
			return jobSeekerAddResult;
		}
		return new SuccessResult("Kayıt başarılı.");
	}

	@Override
	public Result registerForEmployer(Employer employer) throws Exception {
		// iş Kuralları
		Result result=BusinessRules.Run(this.userService.validate(employer.getUser()),this.employerService.validate(employer));
		if(result!=null) {
			return result;
		}
		
		employer.getUser().setRole(Role.EMPLOYER());
		
		DataResult<User> userAddResult= this.userService.add(employer.getUser());
		if(!userAddResult.isSuccess()) {
			return userAddResult;
		}
		
		employer.getUser().setUserId(userAddResult.getData().getUserId());
		DataResult<Employer> employerAddResult=this.employerService.add(employer);
		if(!employerAddResult.isSuccess()) {
			this.userService.delete(userAddResult.getData());
			return employerAddResult;
		}
		return new SuccessResult("Kayıt başarılı.");
	}

}