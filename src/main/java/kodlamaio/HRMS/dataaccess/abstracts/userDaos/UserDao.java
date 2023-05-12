package kodlamaio.HRMS.dataaccess.abstracts.userDaos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import kodlamaio.HRMS.entities.concretes.users.User;

public interface UserDao extends JpaRepository<User,Integer> {
	public List<User> findByEmailEquals(String email);
	boolean existsByEmail(String email);
	User getByEmail(String email);
	User getUserById(int userId);
}
