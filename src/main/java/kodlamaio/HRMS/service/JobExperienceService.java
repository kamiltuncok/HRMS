package kodlamaio.HRMS.service;

import java.util.List;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.entities.concretes.JobExperience;
import kodlamaio.HRMS.dto.JobExperienceRequest;

public interface JobExperienceService {
    DataResult<List<JobExperience>> getAll();

    Result add(JobExperienceRequest jobExperienceRequest);

    Result delete(Long id);

    DataResult<List<JobExperience>> findByResume_IdOrderByEndDateAsc(Long resumeId);

    DataResult<List<JobExperience>> findByResume_IdOrderByEndDateDesc(Long resumeId);
}
