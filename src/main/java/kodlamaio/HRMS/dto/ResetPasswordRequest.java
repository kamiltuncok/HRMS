package kodlamaio.HRMS.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
    @NotBlank(message = "Token boş olamaz")
    String token,

    @NotBlank(message = "Yeni şifre boş olamaz")
    String newPassword
) {}
