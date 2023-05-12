package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.LinkedinAddress;

public interface LinkedinAddressDao extends JpaRepository<LinkedinAddress, Integer> {

}
