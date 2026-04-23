package kodlamaio.HRMS.dto;

import java.time.LocalDate;

public record JobApplicationResponse(
    Long id,
    Long jobAdvertisementId,
    Long jobSeekerId,
    String jobSeekerFirstName,
    String jobSeekerLastName,
    String jobSeekerEmail,
    String jobTitle,
    String jobDescription,
    String companyName,
    LocalDate applicationDate,
    String status
) {}
