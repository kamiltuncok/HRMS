package kodlamaio.HRMS.service;

import java.util.List;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobExperience;

public interface JobExperienceService {
    DataResult<List<JobExperience>> getAll();

    Result add(JobExperience jobExperience);

    DataResult<List<JobExperience>> findByResume_IdOrderByEndDateAsc(Long resumeId);

    DataResult<List<JobExperience>> findByResume_IdOrderByEndDateDesc(Long resumeId);
}
