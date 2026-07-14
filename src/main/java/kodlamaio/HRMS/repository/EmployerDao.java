
package kodlamaio.HRMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.Employer;

import java.util.Optional;

public interface EmployerDao extends JpaRepository<Employer, Long> {
    Optional<Employer> findByEmail(String email);
}
