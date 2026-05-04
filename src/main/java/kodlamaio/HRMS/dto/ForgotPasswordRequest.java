package kodlamaio.HRMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
    @NotBlank(message = "E-posta adresi boş olamaz")
    @Email(message = "Geçersiz e-posta adresi")
    String email
) {}
