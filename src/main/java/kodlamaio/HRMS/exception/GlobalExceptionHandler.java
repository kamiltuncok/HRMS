package kodlamaio.HRMS.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kodlamaio.HRMS.core.utilities.results.ErrorDataResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // --- Bean Validation (Jakarta @Valid) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException exceptions) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<>(validationErrors, "Doğrulama hataları");
    }

    // --- Business Logic Errors (email exists, invalid input, etc.) ---
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessException(BusinessException exception) {
        return new ErrorDataResult<>(exception.getMessage(), exception.getMessage());
    }

    // --- Authentication Errors ---
    @ExceptionHandler({
        org.springframework.security.authentication.BadCredentialsException.class,
        org.springframework.security.core.userdetails.UsernameNotFoundException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDataResult<Object> handleAuthExceptions(Exception exception) {
        return new ErrorDataResult<>("Girilen bilgiler hatalı", "Girilen bilgiler hatalı");
    }

    // --- Account Locked ---
    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ErrorDataResult<Object> handleAccountLockedException(AccountLockedException exception) {
        return new ErrorDataResult<>(exception.getMessage(), exception.getMessage());
    }

    // --- Weak Password ---
    @ExceptionHandler(WeakPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleWeakPasswordException(WeakPasswordException exception) {
        return new ErrorDataResult<>(exception.getMessage(), exception.getMessage());
    }

    // --- Invalid Reset Token ---
    @ExceptionHandler(InvalidResetTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleInvalidResetTokenException(InvalidResetTokenException exception) {
        return new ErrorDataResult<>(exception.getMessage(), exception.getMessage());
    }

    // --- Authorization / Forbidden ---
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDataResult<Object> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException exception) {
        return new ErrorDataResult<>("Bu kaynağa erişim yetkiniz bulunmamaktadır", "Yetkilendirme hatası");
    }

    // --- DB Constraint Violations (race condition safety) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDataResult<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        log.error("Data integrity violation: {}", exception.getMessage());
        
        String rootMessage = exception.getRootCause() != null ? exception.getRootCause().getMessage() : "";
        
        if (rootMessage.contains("email")) {
            return new ErrorDataResult<>("Bu email zaten kayıtlı", "Bu email zaten kayıtlı");
        }
        if (rootMessage.contains("identity_number")) {
            return new ErrorDataResult<>("Bu TC Kimlik numarası zaten kayıtlı", "Bu TC Kimlik numarası zaten kayıtlı");
        }
        
        return new ErrorDataResult<>("Bu kayıt zaten mevcut", "Bu kayıt zaten mevcut");
    }

    // --- Catch-All (NEVER expose raw error details) ---
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDataResult<Object> handleAllExceptions(Exception exception) {
        log.error("Unhandled exception: ", exception);
        return new ErrorDataResult<>("Beklenmeyen bir hata oluştu. Lütfen daha sonra tekrar deneyin.", "Sunucu hatası");
    }
}

