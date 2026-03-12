package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "verification_codes_jobseekers")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VerificationCodeJobSeeker extends VerificationCode {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobseeker_id")
    private JobSeeker jobSeeker;
}
