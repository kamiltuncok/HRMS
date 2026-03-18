package kodlamaio.HRMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.repository.TypeOfWorkDao;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/typeofwork")
@CrossOrigin
@RequiredArgsConstructor
public class TypeOfWorkController extends BaseController {

    private final TypeOfWorkDao typeOfWorkDao;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        return Ok(() -> new SuccessDataResult<>(typeOfWorkDao.findAll(), "Type of work data listed"));
    }
}
