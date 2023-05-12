package kodlamaio.HRMS.entities.concretes.verificationCodes;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="verification_code_candidates")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class VerificationCodeCandidate extends VerificationCode {
	@Id
	@GeneratedValue()
	@Column(name="id")
	private int id;
	@Column(name="candidate_id")
	private int candidateId;

}
