package kodlamaio.HRMS.dataaccess.abstracts.userDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import kodlamaio.HRMS.entities.concretes.users.Candidate;


public interface CandidateDao extends JpaRepository<Candidate,Integer> {
	public List<Candidate> findByIdentityNumberEquals(String identityNumber);
}
