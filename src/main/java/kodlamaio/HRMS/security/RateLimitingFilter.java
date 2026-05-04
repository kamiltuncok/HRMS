package kodlamaio.HRMS.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> loginCache = new ConcurrentHashMap<>();
    private final Map<String, Bucket> forgotPasswordCache = new ConcurrentHashMap<>();

    private Bucket createLoginBucket() {
        // 10 requests per minute per IP
        long capacity = 10;
        Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    private Bucket createForgotPasswordBucket() {
        // 5 requests per minute per IP (stricter)
        long capacity = 5;
        Refill refill = Refill.greedy(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();
        String ip = getClientIP(request);
        
        if (path.startsWith("/api/auth/forgot-password")) {
            Bucket bucket = forgotPasswordCache.computeIfAbsent(ip, k -> createForgotPasswordBucket());
            if (bucket.tryConsume(1)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too Many Requests");
            }
        } else if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            Bucket bucket = loginCache.computeIfAbsent(ip, k -> createLoginBucket());
            if (bucket.tryConsume(1)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too Many Requests");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

