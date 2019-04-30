package th.go.dss.BackOffice.model.HRX;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="HR_EMPLOYEE")
public class Employee {
	
	@Id
	@Column(name="EMP_ID")
	private Integer id;
	
	@Column(name="EMP_NAME")
	private String empName;
	
	@Column(name="ENGLISH_NAME")
	private String empEnglishName;
	
	@Column(name="EMP_TYPE")
	private Character empType;
	
	@Column(name="SEX") 
	private Character sex;
	
	@OneToOne
	@JoinColumn(name="HC_HEAD_COUNT_ID")
	private Position currentPosition;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEnglishName() {
		return empEnglishName;
	}

	public void setEmpEnglishName(String empEnglishName) {
		this.empEnglishName = empEnglishName;
	}

	public Character getEmpType() {
		return empType;
	}

	public void setEmpType(Character empType) {
		this.empType = empType;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
	
}
