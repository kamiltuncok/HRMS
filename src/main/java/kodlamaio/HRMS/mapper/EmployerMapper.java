package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.EmployerResponse;
import kodlamaio.HRMS.entities.concretes.Employer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jobAdvertisements", ignore = true)
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", ignore = true)
    Employer toEntity(EmployerRequest request);

    EmployerResponse toResponse(Employer entity);

    List<EmployerResponse> toResponseList(List<Employer> entities);
}
