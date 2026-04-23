package kodlamaio.HRMS.dto;

import java.time.LocalDate;
import kodlamaio.HRMS.entities.concretes.EducationDegree;

public record SchoolRequest(
    String schoolName,
    String departmentName,
    EducationDegree educationDegree,
    LocalDate startDate,
    LocalDate graduateDate,
    Long resumeId
) {}
