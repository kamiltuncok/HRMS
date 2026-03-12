package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.JobSeekerResponse;
import kodlamaio.HRMS.entities.concretes.JobSeeker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface JobSeekerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "resume", ignore = true)
    @Mapping(target = "role", ignore = true)
    JobSeeker toEntity(JobSeekerRequest request);

    JobSeekerResponse toResponse(JobSeeker entity);

    List<JobSeekerResponse> toResponseList(List<JobSeeker> entities);
}
