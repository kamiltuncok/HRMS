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
@Table(name = "job_experiences")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class JobExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @com.fasterxml.jackson.annotation.JsonProperty("workplaceName")
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @com.fasterxml.jackson.annotation.JsonProperty("leaveDate")
    @Column(name = "end_date")
    private LocalDate endDate; // Nullable means still working

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @com.fasterxml.jackson.annotation.JsonProperty("positionName")
    @Column(name = "position_name")
    private String jobPosition;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
