package kodlamaio.HRMS.service;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.dto.JobApplicationRequest;
import kodlamaio.HRMS.dto.JobApplicationResponse;
import kodlamaio.HRMS.entities.concretes.JobApplication;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import kodlamaio.HRMS.mapper.JobApplicationMapper;
import kodlamaio.HRMS.repository.JobApplicationDao;
import kodlamaio.HRMS.repository.JobSeekerDao;
import kodlamaio.HRMS.repository.JobAdvertisementDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationManager implements JobApplicationService {

    private final JobApplicationDao jobApplicationDao;
    private final JobApplicationMapper jobApplicationMapper;
    private final JobSeekerDao jobSeekerDao;
    private final JobAdvertisementDao jobAdvertisementDao;

    @Override
    public DataResult<JobApplicationResponse> add(JobApplicationRequest request) {
        // Load managed entities. Mapping ids onto detached stubs fails because
        // JobSeeker is @Version-ed ("uninitialized version value") and surfaced as
        // the misleading "Bu kayıt zaten mevcut" error.
        JobSeeker jobSeeker = this.jobSeekerDao.findById(request.jobSeekerId()).orElse(null);
        if (jobSeeker == null) {
            return new ErrorDataResult<>("Job seeker not found for ID: " + request.jobSeekerId());
        }
        JobAdvertisement jobAdvertisement = this.jobAdvertisementDao.findById(request.jobAdvertisementId()).orElse(null);
        if (jobAdvertisement == null) {
            return new ErrorDataResult<>("Job advertisement not found for ID: " + request.jobAdvertisementId());
        }
        if (this.jobApplicationDao.existsByJobSeeker_IdAndJobAdvertisement_Id(
                request.jobSeekerId(), request.jobAdvertisementId())) {
            return new ErrorDataResult<>("Bu ilana zaten başvurdunuz.");
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobSeeker(jobSeeker);
        jobApplication.setJobAdvertisement(jobAdvertisement);
        jobApplication.setApplicationDate(LocalDate.now());
        jobApplication.setStatus("PENDING");

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
