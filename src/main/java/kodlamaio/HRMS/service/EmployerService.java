package kodlamaio.HRMS.service;

import java.util.List;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.EmployerResponse;

public interface EmployerService {
	DataResult<List<EmployerResponse>> getAll();

	DataResult<EmployerResponse> add(EmployerRequest request);

	DataResult<EmployerResponse> update(Long id, kodlamaio.HRMS.dto.EmployerUpdateRequest request);

	Result delete(Long id);

	Result validate(EmployerRequest request) throws Exception;

	DataResult<EmployerResponse> getEmployerById(Long employerId);
}
