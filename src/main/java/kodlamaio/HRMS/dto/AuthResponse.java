package kodlamaio.HRMS.dto;

public record AuthResponse(
                String token,
                String email,
                String role) {
}
