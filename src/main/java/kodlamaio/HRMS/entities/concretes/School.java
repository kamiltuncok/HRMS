package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schools")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_degree", nullable = false)
    private EducationDegree educationDegree;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "graduate_date")
    private LocalDate graduateDate;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
