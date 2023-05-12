package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.GithubAddress;

public interface GithubAddressService {

	DataResult<List<GithubAddress>> getAll();
	Result add(GithubAddress githubAddress);
	
	
}
