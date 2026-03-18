package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_advertisements")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class JobAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "open_positions", nullable = false)
    private int openPositions = 1;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_work_id")
    private TypeOfWork typeOfWork;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_title_id")
    private JobTitle jobTitle;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "jobAdvertisement", fetch = FetchType.LAZY)
    private java.util.List<JobApplication> jobApplications;
}
