package kodlamaio.HRMS.config;

import kodlamaio.HRMS.entities.concretes.*;
import kodlamaio.HRMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleDao roleDao;
    private final CityDao cityDao;
    private final JobTitleDao jobTitleDao;
    private final TypeOfWorkDao typeOfWorkDao;
    private final EmployerDao employerDao;
    private final JobSeekerDao jobSeekerDao;
    private final JobAdvertisementDao jobAdvertisementDao;
    private final JobApplicationDao jobApplicationDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed Roles
        if (roleDao.findByRoleName(Role.JOBSEEKER).isEmpty()) {
            roleDao.save(new Role(null, Role.JOBSEEKER));
        }
        if (roleDao.findByRoleName(Role.EMPLOYER).isEmpty()) {
            roleDao.save(new Role(null, Role.EMPLOYER));
        }

        // Seed Cities
        if (cityDao.count() == 0) {
            List.of("Istanbul", "Ankara", "Izmir", "Bursa", "Antalya")
                .forEach(name -> cityDao.save(new City(null, name, null)));
        }

        // Seed Job Titles
        if (jobTitleDao.count() == 0) {
            List.of("Software Engineer", "Frontend Developer", "Backend Developer", "Product Manager", "UI/UX Designer")
                .forEach(title -> jobTitleDao.save(new JobTitle(null, title, null, null)));
        }

        // Seed Types of Work
        if (typeOfWorkDao.count() == 0) {
            List.of("Full-Time", "Part-Time", "Remote", "Freelance", "Internship")
                .forEach(name -> typeOfWorkDao.save(new TypeOfWork(null, name)));
        }

        // Seed Sample Data for JobAdvertisements and Applications if possible
        seedSamples();
    }

    private void seedSamples() {
        if (employerDao.count() == 0) {
            Role employerRole = roleDao.findByRoleName(Role.EMPLOYER).orElse(null);
            if (employerRole != null) {
                for (int i = 1; i <= 5; i++) {
                    Employer employer = new Employer();
                    employer.setEmail("employer" + i + "@example.com");
                    employer.setPassword(passwordEncoder.encode("12345"));
                    employer.setRole(employerRole);
                    employer.setCompanyName("Company " + i);
                    employer.setWebAddress("www.company" + i + ".com");
                    employer.setPhoneNumber("123456789" + i);
                    employerDao.save(employer);
                }
            }
        }

        if (jobSeekerDao.count() == 0) {
            Role seekerRole = roleDao.findByRoleName(Role.JOBSEEKER).orElse(null);
            if (seekerRole != null) {
                for (int i = 1; i <= 5; i++) {
                    JobSeeker seeker = new JobSeeker();
                    seeker.setEmail("seeker" + i + "@example.com");
                    seeker.setPassword(passwordEncoder.encode("12345"));
                    seeker.setRole(seekerRole);
                    seeker.setFirstName("SeekerFirst" + i);
                    seeker.setLastName("SeekerLast" + i);
                    seeker.setBirthDate(LocalDate.of(1990 + i, 1, 1));
                    seeker.setPhoneNumber("987654321" + i);
                    jobSeekerDao.save(seeker);
                }
            }
        }

        if (jobAdvertisementDao.count() >= 5) return;

        var employers = employerDao.findAll();
        var cities = cityDao.findAll();
        var titles = jobTitleDao.findAll();
        var types = typeOfWorkDao.findAll();
        var seekers = jobSeekerDao.findAll();

        if (employers.isEmpty() || cities.isEmpty() || titles.isEmpty() || types.isEmpty()) return;

        for (int i = 0; i < 5; i++) {
            JobAdvertisement ad = new JobAdvertisement();
            ad.setDescription("Sample Job Advertisement " + (i + 1));
            ad.setStartDate(LocalDate.now());
            ad.setEndDate(LocalDate.now().plusMonths(1));
            ad.setStatus(true);
            ad.setEmployer(employers.get(i % employers.size()));
            ad.setCity(cities.get(i % cities.size()));
            ad.setJobTitle(titles.get(i % titles.size()));
            ad.setTypeOfWork(types.get(i % types.size()));
            ad.setOpenPositions(2);
            jobAdvertisementDao.save(ad);

            if (!seekers.isEmpty()) {
                JobApplication app = new JobApplication();
                app.setApplicationDate(LocalDate.now());
                app.setJobSeeker(seekers.get(i % seekers.size()));
                app.setJobAdvertisement(ad);
                app.setStatus("PENDING");
                jobApplicationDao.save(app);
            }
        }
    }
}
