package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.*;
import kodlamaio.HRMS.entities.concretes.*;
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

    @Mapping(target = "createdDate", source = "startDate")
    @Mapping(target = "applicationDeadline", source = "endDate")
    @Mapping(target = "isActive", source = "status")
    JobAdvertisementResponse toResponse(JobAdvertisement entity);

    List<JobAdvertisementResponse> toResponseList(List<JobAdvertisement> entities);

    // Mapper methods for nested objects
    @Mapping(target = "name", source = "name")
    CityResponse toCityResponse(City city);

    @Mapping(target = "title", source = "title")
    JobTitleResponse toJobTitleResponse(JobTitle title);

    @Mapping(target = "name", source = "name")
    TypeOfWorkResponse toTypeOfWorkResponse(TypeOfWork type);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyName", source = "companyName")
    @Mapping(target = "email", source = "email")
    EmployerResponse toEmployerResponse(Employer employer);
}
