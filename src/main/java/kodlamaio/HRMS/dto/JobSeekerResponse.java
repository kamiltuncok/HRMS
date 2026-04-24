package kodlamaio.HRMS.dto;

import java.time.LocalDate;

public record JobSeekerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate birthDate,
        String profileImageUrl) {
}
