package kodlamaio.HRMS.dto;

import java.time.LocalDate;

public record JobExperienceRequest(
    Long id,
    String companyName,
    LocalDate startDate,
    LocalDate endDate,
    String positionName,
    Long resumeId,
    Long jobTitleId
) {}
