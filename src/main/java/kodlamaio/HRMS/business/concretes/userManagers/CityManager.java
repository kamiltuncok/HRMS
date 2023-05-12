package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.CityService;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.validations.abstracts.ValidationService;
import kodlamaio.HRMS.dataaccess.abstracts.CityDao;
import kodlamaio.HRMS.entities.concretes.City;

@Service
public class CityManager implements CityService {
	private CityDao cityDao;
	private ValidationService validationService;

	@Autowired
	public CityManager(CityDao cityDao, ValidationService validationService) {
		super();
		this.cityDao = cityDao;
		this.validationService = validationService;
	}

	@Override
	public DataResult<List<City>> getAll() {
		return new SuccessDataResult<List<City>>(this.cityDao.findAll(), "Query returned successfully.");
	}

	@Override
	public Result add(City city) {

		Result validationResult = this.validationService.checkCity(city);
		if (!validationResult.isSuccess())
			return new ErrorResult(validationResult.getMessage());

		boolean checkCityResult = checkCity(city);
		if (checkCityResult)
			return new ErrorResult("The city already exists.");

		this.cityDao.save(city);
		return new SuccessResult("The city is added successfully.");
	}

	// Auxiliary Functions
	public boolean checkCity(City city) {

		City findByName = this.cityDao.findByName(city.getName());
		if (findByName == null)
			return false;
		return true;
	}
}
