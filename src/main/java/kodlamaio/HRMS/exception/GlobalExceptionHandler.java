package kodlamaio.HRMS.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException exceptions) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<>(validationErrors, "Validation Errors");
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDataResult<Object> handleBadCredentialsException(
            org.springframework.security.authentication.BadCredentialsException exception) {
        return new ErrorDataResult<>("Invalid email or password", "Authentication Error");
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDataResult<Object> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException exception) {
        return new ErrorDataResult<>("You don't have permission to access this resource", "Authorization Error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDataResult<Object> handleAllExceptions(Exception exception) {
        return new ErrorDataResult<>(exception.getMessage(), "Standard Error");
    }
}
