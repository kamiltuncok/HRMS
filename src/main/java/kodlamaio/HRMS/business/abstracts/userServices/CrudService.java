package kodlamaio.HRMS.business.abstracts.userServices;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;

public interface CrudService<T>{
	DataResult<List<T>>  getAll() throws Exception;
	DataResult<T> add(T entity) throws Exception;
	Result delete(T entity) throws Exception;
	DataResult<T> update(T entity) throws Exception;
}
