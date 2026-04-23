package kodlamaio.HRMS.service;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.Language;
import kodlamaio.HRMS.dto.LanguageRequest;

public interface LanguageService {

	DataResult<List<Language>> getAll();

	Result add(LanguageRequest languageRequest);

	Result delete(Long id);
}
