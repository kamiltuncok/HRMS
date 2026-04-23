package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skills")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @com.fasterxml.jackson.annotation.JsonProperty("skillName")
    @Column(name = "skill_name")
    private String name;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
