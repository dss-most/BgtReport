package th.go.dss.BackOffice.model.USR;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import th.go.dss.BackOffice.model.HRX.Employee;

@Entity
@Table(name="S_USER")
public class User {
	@Id
	@Column(name="ID")
	private Long id;
	
	@Basic
	@Column(name="LOGIN")
	private String loginName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="EMP_ID")   
	@JsonBackReference("Employee-User")
	private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
}
