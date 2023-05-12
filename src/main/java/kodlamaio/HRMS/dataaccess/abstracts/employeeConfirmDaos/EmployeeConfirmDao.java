package kodlamaio.HRMS.dataaccess.abstracts.employeeConfirmDaos;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlamaio.HRMS.entities.concretes.employeeConfirms.EmployeeConfirm;

public interface EmployeeConfirmDao extends JpaRepository<EmployeeConfirm,Integer> {

}
