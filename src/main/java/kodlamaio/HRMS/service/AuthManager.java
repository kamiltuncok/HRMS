package kodlamaio.HRMS.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kodlamaio.HRMS.core.utilities.results.DataResult;
import kodlamaio.HRMS.core.utilities.results.Result;
import kodlamaio.HRMS.core.utilities.results.SuccessDataResult;
import kodlamaio.HRMS.dto.AuthResponse;
import kodlamaio.HRMS.dto.EmployerRequest;
import kodlamaio.HRMS.dto.JobSeekerRequest;
import kodlamaio.HRMS.dto.UserForLoginRequest;
import kodlamaio.HRMS.entities.concretes.User;
import kodlamaio.HRMS.exception.AccountLockedException;
import kodlamaio.HRMS.repository.UserDao;
import kodlamaio.HRMS.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthManager implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthManager.class);

    private final JobSeekerService jobSeekerService;
    private final EmployerService employerService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordValidatorService passwordValidatorService;
    private final UserDao userDao;

    @Override
    public Result registerForJobSeeker(JobSeekerRequest request) throws Exception {
        passwordValidatorService.validatePassword(request.password());
        return this.jobSeekerService.add(request);
    }

    @Override
    public Result registerForEmployer(EmployerRequest request) throws Exception {
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
}
