package kodlamaio.HRMS.dto;

import jakarta.validation.constraints.NotNull;

public record JobApplicationRequest(
    @NotNull(message = "Job Advertisement ID is mandatory") Long jobAdvertisementId,
    @NotNull(message = "Job Seeker ID is mandatory") Long jobSeekerId
) {}
