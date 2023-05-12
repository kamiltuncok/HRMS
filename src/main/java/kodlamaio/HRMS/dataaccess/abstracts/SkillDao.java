package kodlamaio.HRMS.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.Skill;

public interface SkillDao extends JpaRepository<Skill, Integer> {

}
