package kodlamaio.HRMS.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    @Column(name = "resume_id")
    private Long id;

    @Column(name = "summary")
    private String summary = "";

    @Column(name = "adress")
    private String address = "";

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone")
    private String phone = "";

    @Column(name = "github")
    private String githubUrl = "";
    
    @Column(name = "linkedin")
    private String linkedinUrl = "";

    @Column(name = "portfolio_url")
    private String portfolioUrl = "";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobseeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<JobExperience> jobExperiences = new HashSet<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<School> schools = new HashSet<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
