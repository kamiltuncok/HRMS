package kodlamaio.HRMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.SkillDao;
import kodlamaio.HRMS.entities.concretes.Skill;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillManager implements SkillService {

	private final SkillDao skillDao;

	@Override
	public DataResult<List<Skill>> getAll() {
		return new SuccessDataResult<>(this.skillDao.findAll(), "Skills listed successfully.");
	}

	@Override
	public Result add(Skill skill) {
		this.skillDao.save(skill);
		return new SuccessResult("Skill has been added successfully.");
	}

	@Override
	public Result delete(Long id) {
		this.skillDao.deleteById(id);
		return new SuccessResult("Skill has been deleted successfully.");
	}
}
