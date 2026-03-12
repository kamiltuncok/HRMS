package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.core.validations.abstracts.ValidationService;
import kodlamaio.HRMS.repository.CityDao;
import kodlamaio.HRMS.entities.concretes.City;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityManager implements CityService {
	private final CityDao cityDao;
	private final ValidationService validationService;

	@Override
	public DataResult<List<City>> getAll() {
		return new SuccessDataResult<>(this.cityDao.findAll(), "Cities listed successfully.");
	}

	@Override
	public Result add(City city) {
		Result validationResult = this.validationService.checkCity(city);
		if (!validationResult.isSuccess()) {
			return new ErrorResult(validationResult.getMessage());
		}

		if (checkIfCityExists(city.getName())) {
			return new ErrorResult("The city already exists.");
		}

		this.cityDao.save(city);
		return new SuccessResult("The city has been added successfully.");
	}

	private boolean checkIfCityExists(String cityName) {
		return this.cityDao.findByName(cityName) != null;
	}
}
