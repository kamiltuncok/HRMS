package kodlamaio.HRMS.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record JobSeekerUpdateRequest(
                @NotBlank(message = "First name is mandatory") String firstName,

                @NotBlank(message = "Last name is mandatory") String lastName,

                @NotNull(message = "Birth date is mandatory") @Past(message = "Birth date must be in the past") LocalDate birthDate,

                @NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String email,

                String phoneNumber) {
}
