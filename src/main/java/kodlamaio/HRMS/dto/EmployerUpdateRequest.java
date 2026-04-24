package kodlamaio.HRMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployerUpdateRequest(
        @NotBlank(message = "Company name is mandatory") String companyName,

        @NotBlank(message = "Web address is mandatory") String webAddress,

        @NotBlank(message = "Phone number is mandatory") String phoneNumber,

        @NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,
        
        String profileImageUrl) {
}
