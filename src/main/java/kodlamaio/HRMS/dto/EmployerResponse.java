package kodlamaio.HRMS.dto;

public record EmployerResponse(
                Long id,
                String companyName,
                String webAddress,
                String phoneNumber,
                String email,
                String profileImageUrl) {
}
