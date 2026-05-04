package kodlamaio.HRMS.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.core.utilities.results.SuccessResult;
import kodlamaio.HRMS.dto.AuthResponse;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.ForgotPasswordRequest;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.ResetPasswordRequest;
import kodlamaio.HRMS.dto.UserForLoginRequest;
import kodlamaio.HRMS.entities.concretes.PasswordResetToken;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.exception.AccountLockedException;
import kodlamaio.HRMS.exception.BusinessException;
import kodlamaio.HRMS.exception.InvalidResetTokenException;
import kodlamaio.HRMS.repository.PasswordResetTokenDao;
import kodlamaio.HRMS.repository.UserDao;
import kodlamaio.HRMS.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthManager.class);
    private static final int TOKEN_EXPIRY_MINUTES = 30;

    private final JobSeekerService jobSeekerService;
    private final EmployerService employerService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordValidatorService passwordValidatorService;
    private final UserDao userDao;
    private final PasswordResetTokenDao passwordResetTokenDao;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public Result registerForJobSeeker(JobSeekerRequest request) throws Exception {
        if (userDao.existsByEmail(request.email())) {
            throw new BusinessException("Bu email zaten kayıtlı");
        }
        passwordValidatorService.validatePassword(request.password());
        return this.jobSeekerService.add(request);
    }

    @Override
    public Result registerForEmployer(EmployerRequest request) throws Exception {
        if (userDao.existsByEmail(request.email())) {
            throw new BusinessException("Bu email zaten kayıtlı");
        }
        passwordValidatorService.validatePassword(request.password());
        return this.employerService.add(request);
    }

    @Override
    @Transactional
    public DataResult<AuthResponse> login(UserForLoginRequest loginRequest) {
        User user = userDao.findByEmail(loginRequest.email()).orElseThrow(() -> {
            log.warn("Failed login attempt for unknown email: {}", loginRequest.email());
            return new org.springframework.security.authentication.BadCredentialsException("Email veya şifre hatalı");
        });

        if (user.getLockTime() != null && user.getLockTime().isAfter(LocalDateTime.now())) {
            log.warn("Login attempt on locked account: {}", user.getEmail());
            throw new AccountLockedException("Hesap geçici olarak kilitlendi. Lütfen daha sonra tekrar deneyin.");
        } else if (user.getLockTime() != null && user.getLockTime().isBefore(LocalDateTime.now())) {
            // Lock expired, reset
            user.setFailedAttempts(0);
            user.setLockTime(null);
            userDao.save(user);
        }

        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()));

            user.setFailedAttempts(0);
            user.setLockTime(null);
            userDao.save(user);

            log.info("Successful login for user: {}", user.getEmail());

            String token = jwtService.generateToken(user);
            var response = new AuthResponse(token, user.getId(), user.getEmail(), "ROLE_" + user.getRole().getRoleName().toUpperCase());

            return new SuccessDataResult<>(response, "Login successful");
        } catch (org.springframework.security.core.AuthenticationException e) {
            int attempts = (user.getFailedAttempts() == null ? 0 : user.getFailedAttempts()) + 1;
            user.setFailedAttempts(attempts);

            if (attempts >= 5) {
                user.setLockTime(LocalDateTime.now().plusMinutes(15));
                log.warn("Account locked due to multiple failed attempts: {}", user.getEmail());
            } else {
                log.warn("Failed login attempt {} for user: {}", attempts, user.getEmail());
            }
            
            userDao.save(user);
            throw new org.springframework.security.authentication.BadCredentialsException("Email veya şifre hatalı");
        }
    }

    @Override
    @Transactional
    public Result forgotPassword(ForgotPasswordRequest request) {
        Optional<User> userOpt = userDao.findByEmail(request.email());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Invalidate all previous tokens for this user
            passwordResetTokenDao.invalidateAllTokensForUser(user.getId());

            // Generate raw token and its SHA-256 hash
            String rawToken = UUID.randomUUID().toString();
            String tokenHash = sha256Hash(rawToken);

            // Save hashed token to database
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setTokenHash(tokenHash);
            resetToken.setUser(user);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES));
            resetToken.setUsed(false);
            passwordResetTokenDao.save(resetToken);

            // Send email with raw token (async)
            emailService.sendPasswordResetEmail(user.getEmail(), rawToken);

            log.info("Password reset token generated for user: {}", user.getEmail());
        } else {
            log.info("Password reset requested for non-existent email: {}", request.email());
        }

        // Always return success to prevent email enumeration
        return new SuccessResult("Eğer bu email sistemde kayıtlıysa, şifre sıfırlama bağlantısı gönderildi.");
    }

    @Override
    @Transactional
    public Result resetPassword(ResetPasswordRequest request) {
        String tokenHash = sha256Hash(request.token());

        PasswordResetToken resetToken = passwordResetTokenDao.findByTokenHash(tokenHash)
                .orElseThrow(() -> new InvalidResetTokenException("Geçersiz şifre sıfırlama bağlantısı."));

        if (resetToken.isUsed()) {
            throw new InvalidResetTokenException("Bu şifre sıfırlama bağlantısı zaten kullanılmış.");
        }

        if (resetToken.isExpired()) {
            throw new InvalidResetTokenException("Şifre sıfırlama bağlantısının süresi dolmuş. Lütfen yeni bir talep oluşturun.");
        }

        // Validate new password strength
        passwordValidatorService.validatePassword(request.newPassword());

        // Update user password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userDao.save(user);

        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenDao.save(resetToken);

        log.info("Password successfully reset for user: {}", user.getEmail());

        return new SuccessResult("Şifreniz başarıyla değiştirildi.");
    }

    @Override
    public DataResult<Map<String, Boolean>> validateResetToken(String token) {
        String tokenHash = sha256Hash(token);

        boolean valid = passwordResetTokenDao.findByTokenHash(tokenHash)
                .map(PasswordResetToken::isValid)
                .orElse(false);

        return new SuccessDataResult<>(Map.of("valid", valid), "Token doğrulama tamamlandı.");
    }

    // --- Utility ---

    private static String sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}

