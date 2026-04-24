package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resumes")
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "summary")
    private String summary;

    @Column(name = "cv_file_path")
    private String cvFilePath;

    @Column(name = "cv_file_name")
    private String cvFileName;

    @Column(name = "cv_upload_date")
    private LocalDateTime cvUploadDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "jobseeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonManagedReference
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<JobExperience> jobExperiences = new HashSet<>();

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonManagedReference
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<School> schools = new HashSet<>();

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonManagedReference
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skill> skills = new HashSet<>();

    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    @com.fasterxml.jackson.annotation.JsonManagedReference
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Language> languages = new HashSet<>();

    // Compatibility methods for ResumeManager
    public Long getResumeId() {
        return this.id;
    }

    public String getGithub() {
        return this.githubUrl;
    }

    public void setGithub(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLinkedin() {
        return this.linkedinUrl;
    }

    public void setLinkedin(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }
}
