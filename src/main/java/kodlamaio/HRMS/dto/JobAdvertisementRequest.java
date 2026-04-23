package kodlamaio.HRMS.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record JobAdvertisementRequest(
                @NotNull(message = "City ID is mandatory") Long cityId,

                @NotNull(message = "Employer ID is mandatory") Long employerId,

                @NotNull(message = "Job Title ID is mandatory") Long jobTitleId,

                Long typeOfWorkId,

                @NotBlank(message = "Description is mandatory") String description,

                @FutureOrPresent(message = "Application deadline must be in the future or present") LocalDate applicationDeadline) {
}
