package kodlamaio.HRMS.business.abstracts.userServices;



import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.users.Employer;

public interface EmployerService extends CrudService<Employer> {

	//public Result save(Employer employer);
	Result isNull(Employer employer) throws Exception;
	Result validate(Employer employer) throws Exception;
	DataResult<Employer> getEmployerById(int employerId) throws Exception;
}
