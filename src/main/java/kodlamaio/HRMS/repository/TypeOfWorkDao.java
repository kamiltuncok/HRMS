package kodlamaio.HRMS.repository;

import kodlamaio.HRMS.entities.concretes.TypeOfWork;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeOfWorkDao extends JpaRepository<TypeOfWork, Long> {
    Optional<TypeOfWork> findByName(String name);
}
