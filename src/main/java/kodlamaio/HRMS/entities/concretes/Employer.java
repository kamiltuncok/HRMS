package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employers")
@PrimaryKeyJoinColumn(name = "user_id")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Employer extends User {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "web_address", nullable = false)
    private String webAddress;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "employer")
    private Set<JobAdvertisement> jobAdvertisements;
}
