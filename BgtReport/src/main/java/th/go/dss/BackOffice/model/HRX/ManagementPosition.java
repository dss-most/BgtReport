package th.go.dss.BackOffice.model.HRX;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Entity;


@Entity
@Table(name="HR_TITLE_M")
public class ManagementPosition {

	@Id
	@Column(name="TIT_M_ID")
	private Integer id;
	
	@Column(name="TIT_M_NAME")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
