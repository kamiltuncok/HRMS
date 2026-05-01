package kodlamaio.HRMS.service;

import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import kodlamaio.HRMS.exception.WeakPasswordException;

@Service
public class PasswordValidatorService {

    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private static final Set<String> WEAK_PASSWORDS = Set.of(
            "123456", "12345678", "password", "qwerty", "admin", "admin123", "123456789");

    public void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new WeakPasswordException("Şifre boş olamaz");
        }

        if (WEAK_PASSWORDS.contains(password.toLowerCase())) {
            throw new WeakPasswordException("Şifre çok yaygın veya tahmin edilmesi kolay");
        }

        if (!pattern.matcher(password).matches()) {
            throw new WeakPasswordException("Şifre en az 8 karakter olmalı ve büyük harf, küçük harf, rakam ve özel karakter içermelidir");
        }
    }
}
