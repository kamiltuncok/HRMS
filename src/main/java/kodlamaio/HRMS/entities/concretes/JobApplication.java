package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_applications")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id")
    private JobSeeker jobSeeker;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_advertisement_id")
    private JobAdvertisement jobAdvertisement;

    @Column(name = "status")
    private String status = "PENDING";
}
