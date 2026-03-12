package kodlamaio.HRMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.Photo;

public interface PhotoDao extends JpaRepository<Photo, Long> {

}
