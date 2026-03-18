package kodlamaio.HRMS.dto;

import java.time.LocalDate;

public record ResumeRequest(
    Long id,
    String summary,
    LocalDate birthDate,
    String phone,
    String githubUrl,
    String linkedinUrl,
    String portfolioUrl,
    Long jobSeekerId
) {}
