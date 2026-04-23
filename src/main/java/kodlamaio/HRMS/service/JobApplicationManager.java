package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.dto.JobApplicationRequest;
import kodlamaio.HRMS.dto.JobApplicationResponse;
import kodlamaio.HRMS.entities.concretes.JobApplication;
import kodlamaio.HRMS.mapper.JobApplicationMapper;
import kodlamaio.HRMS.repository.JobApplicationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationManager implements JobApplicationService {

    private final JobApplicationDao jobApplicationDao;
    private final JobApplicationMapper jobApplicationMapper;

    @Override
    public DataResult<JobApplicationResponse> add(JobApplicationRequest request) {
        JobApplication jobApplication = jobApplicationMapper.toEntity(request);
        this.jobApplicationDao.save(jobApplication);
        return new SuccessDataResult<>(jobApplicationMapper.toResponse(jobApplication), "Job application has been submitted successfully.");
    }

    @Override
    public DataResult<List<JobApplicationResponse>> getAll() {
        List<JobApplication> applications = this.jobApplicationDao.findAll();
        return new SuccessDataResult<>(jobApplicationMapper.toResponseList(applications), "Job applications listed successfully.");
    }

    @Override
    public DataResult<List<JobApplicationResponse>> getByJobSeekerId(Long jobSeekerId) {
        List<JobApplication> applications = this.jobApplicationDao.findByJobSeeker_Id(jobSeekerId);
        return new SuccessDataResult<>(jobApplicationMapper.toResponseList(applications), "Job seeker applications listed.");
    }

    @Override
    public DataResult<List<JobApplicationResponse>> getByEmployerId(Long employerId) {
        List<JobApplication> applications = this.jobApplicationDao.findByJobAdvertisement_Employer_Id(employerId);
        return new SuccessDataResult<>(jobApplicationMapper.toResponseList(applications), "Employer applications listed.");
    }

    @Override
    public DataResult<JobApplicationResponse> updateStatus(Long id, String status) {
        return this.jobApplicationDao.findById(id).<DataResult<JobApplicationResponse>>map(application -> {
            application.setStatus(status);
            this.jobApplicationDao.save(application);
            return new SuccessDataResult<>(jobApplicationMapper.toResponse(application), "Application status updated to " + status);
        }).orElseGet(() -> new ErrorDataResult<>("Job application not found."));
    }
}
