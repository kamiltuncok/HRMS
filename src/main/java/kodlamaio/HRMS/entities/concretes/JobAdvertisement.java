package kodlamaio.HRMS.entities.concretes;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;



import kodlamaio.HRMS.entities.concretes.users.Employer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_advertisements")
public class JobAdvertisement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "description")
	private String description;

	@Column(name = "max_salary")
	private int maxSalary;

	@Column(name = "min_salary")
	private int minSalary;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "status")
	private boolean status;

	@Column(name = "free_position_amount")
	private int freePositionAmount;
	
	@Column(name = "type_of_work")
	private String typeOfWork;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;
	
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id")
	private Employer employer;
	
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_title_id")
	private JobTitle jobPosition;
}
