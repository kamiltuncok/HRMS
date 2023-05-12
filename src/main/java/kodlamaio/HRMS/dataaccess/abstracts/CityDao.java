package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.City;


public interface CityDao extends JpaRepository<City,Integer> {
	City findByName(String name);
}
