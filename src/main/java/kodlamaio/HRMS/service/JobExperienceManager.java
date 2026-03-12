package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.stereotype.Service;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.JobExperienceDao;
import kodlamaio.HRMS.entities.concretes.JobExperience;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobExperienceManager implements JobExperienceService {

    private final JobExperienceDao jobExperienceDao;

    @Override
    public DataResult<List<JobExperience>> getAll() {
        return new SuccessDataResult<>(jobExperienceDao.findAll(), "Job experiences listed successfully.");
    }

    @Override
    public Result add(JobExperience jobExperience) {
        jobExperienceDao.save(jobExperience);
        return new SuccessResult("Job experience has been added successfully.");
    }

    @Override
    public DataResult<List<JobExperience>> findByResume_IdOrderByEndDateAsc(Long resumeId) {
        return new SuccessDataResult<>(jobExperienceDao.findByResume_IdOrderByEndDateAsc(resumeId),
                "Job experiences listed by end date (asc).");
    }

    @Override
    public DataResult<List<JobExperience>> findByResume_IdOrderByEndDateDesc(Long resumeId) {
        return new SuccessDataResult<>(jobExperienceDao.findByResume_IdOrderByEndDateDesc(resumeId),
                "Job experiences listed by end date (desc).");
    }
}
