package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.Language;

public interface LanguageDao extends JpaRepository<Language, Integer> {

}
