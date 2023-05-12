package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.City;

public interface CityService {
	DataResult<List<City>> getAll();

	Result add(City city);
}
