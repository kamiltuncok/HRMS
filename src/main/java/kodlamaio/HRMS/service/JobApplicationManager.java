package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
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
}
