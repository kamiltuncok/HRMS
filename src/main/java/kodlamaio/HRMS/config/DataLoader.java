package kodlamaio.HRMS.config;

import kodlamaio.HRMS.entities.concretes.*;
import kodlamaio.HRMS.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
                .forEach(title -> jobTitleDao.save(new JobTitle(null, title, null)));
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
        if (jobAdvertisementDao.count() > 0) return;

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
            ad.setEmployer(employers.get(0));
            ad.setCity(cities.get(i % cities.size()));
            ad.setJobTitle(titles.get(i % titles.size()));
            ad.setTypeOfWork(types.get(i % types.size()));
            ad.setOpenPositions(2);
            jobAdvertisementDao.save(ad);

            if (!seekers.isEmpty()) {
                JobApplication app = new JobApplication();
                app.setApplicationDate(LocalDate.now());
                app.setJobSeeker(seekers.get(0));
                app.setJobAdvertisement(ad);
                app.setStatus("PENDING");
                jobApplicationDao.save(app);
            }
        }
    }
}
