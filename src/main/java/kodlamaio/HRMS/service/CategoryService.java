package kodlamaio.HRMS.service;

import java.util.List;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.dto.CategoryResponse;

public interface CategoryService {
    DataResult<List<CategoryResponse>> getAllCategories();
    DataResult<CategoryResponse> getCategoryById(Long id);
}
