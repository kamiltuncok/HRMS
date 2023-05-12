package kodlamaio.HRMS.dataaccess.abstracts.userDaos;

import org.springframework.data.jpa.repository.JpaRepository;


import kodlamaio.HRMS.entities.concretes.users.Employer;

public interface EmployerDao extends JpaRepository<Employer,Integer> {

	Employer getEmployerById(int employerId);
}
