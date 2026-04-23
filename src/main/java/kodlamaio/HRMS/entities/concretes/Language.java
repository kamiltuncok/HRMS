package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "languages")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @com.fasterxml.jackson.annotation.JsonProperty("languageName")
    @Column(name = "language_name")
    private String name;

    @Column(name = "language_level")
    private String level;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
