package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.stereotype.Service;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.repository.JobExperienceDao;
import kodlamaio.HRMS.entities.concretes.JobExperience;
import kodlamaio.HRMS.entities.concretes.Resume;
import kodlamaio.HRMS.entities.concretes.JobTitle;
import kodlamaio.HRMS.dto.JobExperienceRequest;
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
    public Result add(JobExperienceRequest request) {
        JobExperience jobExperience = new JobExperience();
        jobExperience.setCompanyName(request.companyName());
        jobExperience.setStartDate(request.startDate());
        jobExperience.setEndDate(request.endDate());
        jobExperience.setJobPosition(request.positionName());
        
        if (request.resumeId() != null) {
            Resume resume = new Resume();
            resume.setId(request.resumeId());
            jobExperience.setResume(resume);
        }
        
        if (request.jobTitleId() != null) {
            JobTitle jobTitle = new JobTitle();
            jobTitle.setId(request.jobTitleId());
            jobExperience.setJobTitle(jobTitle);
        }

        jobExperienceDao.save(jobExperience);
        return new SuccessResult("Job experience has been added successfully.");
    }

    @Override
    public Result delete(Long id) {
        jobExperienceDao.deleteById(id);
        return new SuccessResult("Job experience has been deleted successfully.");
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
