package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.JobTitleResponse;
import kodlamaio.HRMS.entities.concretes.JobTitle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobTitleMapper {

    @Mapping(target = "categoryName", source = "category.name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    JobTitleResponse toResponse(JobTitle jobTitle);

    List<JobTitleResponse> toResponseList(List<JobTitle> jobTitles);
}
