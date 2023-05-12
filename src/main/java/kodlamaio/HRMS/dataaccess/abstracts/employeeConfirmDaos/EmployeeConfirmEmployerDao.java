package kodlamaio.HRMS.dataaccess.abstracts.employeeConfirmDaos;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.employeeConfirms.EmployeeConfirmEmployer;



public interface EmployeeConfirmEmployerDao extends JpaRepository<EmployeeConfirmEmployer,Integer> {

}
