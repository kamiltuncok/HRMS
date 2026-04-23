package kodlamaio.HRMS.service;

import java.util.List;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.Skill;
import kodlamaio.HRMS.dto.SkillRequest;

public interface SkillService {

	DataResult<List<Skill>> getAll();

	Result add(SkillRequest skillRequest);

	Result delete(Long id);
}
