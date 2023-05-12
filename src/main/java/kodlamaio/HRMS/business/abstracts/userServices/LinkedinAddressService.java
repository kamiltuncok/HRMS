package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.LinkedinAddress;

public interface LinkedinAddressService {

	DataResult<List<LinkedinAddress>> getAll();
	Result add(LinkedinAddress linkedinAddress);
	
	
}
