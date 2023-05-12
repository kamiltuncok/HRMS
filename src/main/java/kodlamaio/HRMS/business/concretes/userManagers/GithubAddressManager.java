package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.GithubAddressService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.GithubAddressDao;
import kodlamaio.HRMS.entities.concretes.GithubAddress;

@Service
public class GithubAddressManager implements GithubAddressService {

	private GithubAddressDao githubAddressDao;

	@Autowired
	public GithubAddressManager(GithubAddressDao githubAddressDao) {
		super();
		this.githubAddressDao = githubAddressDao;
	}

	@Override
	public DataResult<List<GithubAddress>> getAll() {
		
		return new SuccessDataResult<List<GithubAddress>>(this.githubAddressDao.findAll(), "GitHub addresses listed");
	}

	@Override
	public Result add(GithubAddress githubAddress) {
		this.githubAddressDao.save(githubAddress);
		return new SuccessResult("GitHub Address added");
	}
	
	
	
	
}
