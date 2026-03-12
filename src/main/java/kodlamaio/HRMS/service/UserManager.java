package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Override
	public DataResult<List<User>> getAll() {
		return new SuccessDataResult<>(this.userDao.findAll(), "Kullanıcılar listelendi");
	}

	@Override
	public DataResult<User> add(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userDao.save(user);
		return new SuccessDataResult<>(savedUser, "Kullanıcı eklendi");
	}

	@Override
	public Result existsUserByEposta(String email) {
		if (this.userDao.existsByEmail(email)) {
			return new kodlamaio.HRMS.core.utilities.results.ErrorResult("Bu eposta kullanılmaktadır.");
		}
		return new SuccessResult();
	}

	@Override
	public Result delete(User user) {
		this.userDao.delete(user);
		return new SuccessResult("Kullanıcı silindi");
	}

	@Override
	public DataResult<User> update(User entity) {
		User savedUser = this.userDao.save(entity);
		return new SuccessDataResult<>(savedUser, "Kullanıcı güncellendi");
	}

	@Override
	public DataResult<User> getByEmail(String email) {
		return new SuccessDataResult<>(
				this.userDao.findByEmail(email).orElse(null));
	}

	@Override
	public Result validate(User user) {
		return existsUserByEposta(user.getEmail());
	}

	@Override
	public DataResult<User> getUserById(Long userId) {
		return new SuccessDataResult<>(userDao.findById(userId).orElse(null));
	}
}
