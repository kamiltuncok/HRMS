package kodlamaio.HRMS.business.abstracts.userServices;



import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.users.User;


public interface UserService extends CrudService<User> {
	Result existsUserByEposta(String eposta) throws Exception;
	Result validate(User user)throws Exception;
	DataResult<User> getByEmail(String email) throws Exception;
	DataResult<User> getUserById(int userId) throws Exception;
}
