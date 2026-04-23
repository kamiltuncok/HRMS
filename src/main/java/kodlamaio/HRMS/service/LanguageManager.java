package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.LanguageDao;
import kodlamaio.HRMS.entities.concretes.Language;
import kodlamaio.HRMS.entities.concretes.Resume;
import kodlamaio.HRMS.dto.LanguageRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageManager implements LanguageService {

	private final LanguageDao languageDao;

	@Override
	public DataResult<List<Language>> getAll() {
		return new SuccessDataResult<>(this.languageDao.findAll(), "Languages listed successfully.");
	}

	@Override
	public Result add(LanguageRequest languageRequest) {
		Language language = new Language();
		language.setName(languageRequest.languageName());
		language.setLevel(languageRequest.level());
		
		Resume resume = new Resume();
		resume.setId(languageRequest.resumeId());
		language.setResume(resume);
		
		this.languageDao.save(language);
		return new SuccessResult("Language has been added successfully.");
	}

	@Override
	public Result delete(Long id) {
		this.languageDao.deleteById(id);
		return new SuccessResult("Language has been deleted successfully.");
	}
}
