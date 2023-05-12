package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.GithubAddress;

public interface GithubAddressDao extends JpaRepository<GithubAddress, Integer> {

}
