package kodlamaio.HRMS.business.concretes.userManagers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.business.abstracts.userServices.UserService;
import kodlamaio.HRMS.core.business.BusinessRules;
import kodlamaio.HRMS.core.security.HashingHelper;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dataaccess.abstracts.userDaos.UserDao;
import kodlamaio.HRMS.entities.concretes.users.User;

@Service
public class UserManager implements UserService {

	private UserDao userDao;
	
	@Autowired
	public UserManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<User>> getAll() {
		return new SuccessDataResult<List<User>>(this.userDao.findAll(), "Kullanıcı eklendi");
	}

	@Override
	public DataResult<User> add(User user) throws Exception {
		user.setPassword(HashingHelper.CreatePasswordHash(user.getPassword()));
		User u= this.userDao.save(user);
		
		return new SuccessDataResult<User>(u,"Kullanıcı eklendi");
		
	}
	
	@Override
	public Result existsUserByEposta(String email) {
		boolean exists=this.userDao.existsByEmail(email);
		if(exists) {
			return new ErrorResult("Bu eposta kullanılmaktadır.");
		}
		else {
			return new SuccessResult();
		}
		
	}
	@Override
	
	public Result delete(User user) {
		this.userDao.delete(user);
		return new SuccessResult("Kullanıcı silindi");
	}
	@Override
	public DataResult<User> update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataResult<User> getByEmail(String email) {
		// TODO Auto-generated method stub
		return new SuccessDataResult<User>(this.userDao.getByEmail(email));
	}
	@Override
	public Result validate(User user) throws Exception {
		Result result =BusinessRules.Run(existsUserByEposta(user.getEmail()));
		if(result!=null) {
			return result;
		}
		return new SuccessResult();
	}
	@Override
	public DataResult<User> getUserById(int userId) throws Exception {
		User user=userDao.getUserById(userId);
		return new SuccessDataResult<User>(user);
	}

}
