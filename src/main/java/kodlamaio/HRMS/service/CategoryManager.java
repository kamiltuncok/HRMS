package kodlamaio.HRMS.service;

import java.util.List;
import org.springframework.stereotype.Service;
import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.dto.CategoryResponse;
import kodlamaio.HRMS.repository.CategoryDao;
import kodlamaio.HRMS.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryManager implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public DataResult<List<CategoryResponse>> getAllCategories() {
        var categories = categoryDao.findAll();
        return new SuccessDataResult<>(categoryMapper.toResponseList(categories), "Categories listed successfully.");
    }

    @Override
    public DataResult<CategoryResponse> getCategoryById(Long id) {
        var categoryOptional = categoryDao.findById(id);
        if (categoryOptional.isEmpty()) {
            return new ErrorDataResult<>("Category not found.");
        }
        return new SuccessDataResult<>(categoryMapper.toResponse(categoryOptional.get()), "Category found.");
    }
}
