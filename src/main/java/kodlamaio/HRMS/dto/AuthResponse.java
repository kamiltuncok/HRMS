package kodlamaio.HRMS.dto;

public record AuthResponse(
                String token,
                Long id,
                String email,
                String role) {
}
