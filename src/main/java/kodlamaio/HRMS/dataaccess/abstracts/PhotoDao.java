package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.Photo;

public interface PhotoDao extends JpaRepository<Photo, Integer> {

	//List<Photo> findAllByResume_Id(int resumeId);  şimdilik kalsın...
}
