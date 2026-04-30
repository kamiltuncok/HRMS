package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlamaio.HRMS.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
@RequiredArgsConstructor
public class CategoriesController extends BaseController {

    private final CategoryService categoryService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return Ok(() -> categoryService.getAllCategories());
    }

    @GetMapping("/getbyid")
    public ResponseEntity<?> getById(@RequestParam Long id) {
        return Ok(() -> categoryService.getCategoryById(id));
    }
}
