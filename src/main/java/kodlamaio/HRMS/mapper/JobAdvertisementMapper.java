package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.JobAdvertisementRequest;
import kodlamaio.HRMS.dto.JobAdvertisementResponse;
import kodlamaio.HRMS.entities.concretes.JobAdvertisement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JobAdvertisementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "startDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "city.id", source = "cityId")
    @Mapping(target = "employer.id", source = "employerId")
    @Mapping(target = "jobTitle.id", source = "jobTitleId")
    @Mapping(target = "typeOfWork.id", source = "typeOfWorkId")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "endDate", source = "applicationDeadline")
    JobAdvertisement toEntity(JobAdvertisementRequest request);

    @Mapping(target = "jobTitle", source = "jobTitle.title")
    @Mapping(target = "typeOfWorkName", source = "typeOfWork.name")
    @Mapping(target = "cityName", source = "city.name")
    @Mapping(target = "companyName", source = "employer.companyName")
    @Mapping(target = "createdDate", source = "startDate")
    @Mapping(target = "applicationDeadline", source = "endDate")
    @Mapping(target = "isActive", source = "status")
    @Mapping(target = "openPositions", ignore = true)
    JobAdvertisementResponse toResponse(JobAdvertisement entity);

    List<JobAdvertisementResponse> toResponseList(List<JobAdvertisement> entities);
}
