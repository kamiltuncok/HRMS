package kodlamaio.HRMS.mapper;

import kodlamaio.HRMS.dto.CategoryResponse;
import kodlamaio.HRMS.entities.concretes.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toResponseList(List<Category> categories);
}
