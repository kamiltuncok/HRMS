package kodlamaio.HRMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.City;

public interface CityDao extends JpaRepository<City, Long> {
	City findByName(String name);
}
