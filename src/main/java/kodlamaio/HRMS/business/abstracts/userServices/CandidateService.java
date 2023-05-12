package kodlamaio.HRMS.business.abstracts.userServices;




import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.users.Candidate;


public interface CandidateService {

	public Result save(Candidate candidate) throws Exception;
}
