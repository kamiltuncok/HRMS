package kodlamaio.HRMS.dto;

import java.time.LocalDate;

public record JobAdvertisementResponse(
                Long id,
                String companyName,
                String jobTitle,
                String typeOfWorkName,
                String description,
                String cityName,
                int openPositions,
                LocalDate applicationDeadline,
                LocalDate createdDate,
                boolean isActive) {
}
