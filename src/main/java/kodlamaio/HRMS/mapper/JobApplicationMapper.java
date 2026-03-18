package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.JobApplicationRequest;
import kodlamaio.HRMS.dto.JobApplicationResponse;
import kodlamaio.HRMS.entities.concretes.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applicationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "jobSeeker.id", source = "jobSeekerId")
    @Mapping(target = "jobAdvertisement.id", source = "jobAdvertisementId")
    JobApplication toEntity(JobApplicationRequest request);

    @Mapping(target = "jobAdvertisementId", source = "jobAdvertisement.id")
    @Mapping(target = "jobSeekerId", source = "jobSeeker.id")
    @Mapping(target = "jobDescription", source = "jobAdvertisement.description")
    @Mapping(target = "companyName", source = "jobAdvertisement.employer.companyName")
    JobApplicationResponse toResponse(JobApplication entity);

    List<JobApplicationResponse> toResponseList(List<JobApplication> entities);
}
