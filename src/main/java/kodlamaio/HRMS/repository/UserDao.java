package kodlamaio.HRMS.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import kodlamaio.HRMS.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);
}
