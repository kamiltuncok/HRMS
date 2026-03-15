package kodlamaio.HRMS.dto;

import java.time.LocalDate;

public record JobAdvertisementResponse(
                Long id,
                EmployerResponse employer,
                JobTitleResponse jobTitle,
                TypeOfWorkResponse typeOfWork,
                String description,
                CityResponse city,
                int openPositions,
                LocalDate applicationDeadline,
                LocalDate createdDate,
                boolean isActive) {
}
