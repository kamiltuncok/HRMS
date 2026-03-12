package kodlamaio.HRMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kodlamaio.HRMS.entities.concretes.JobAdvertisement;

public interface JobAdvertisementDao extends JpaRepository<JobAdvertisement, Long> {

	List<JobAdvertisement> findByStatusTrue();

	List<JobAdvertisement> findByStatusTrueOrderByStartDateDesc();

	List<JobAdvertisement> findByStatusTrueAndEmployer_CompanyName(String companyName);

	@Modifying
	@Query("Update JobAdvertisement jat set jat.status = false where jat.id = :id")
	void updateJobAdvertisementStatus(Long id);
}
