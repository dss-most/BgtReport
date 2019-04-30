package th.go.dss.BackOffice.model.HRX;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="HR_TITLE_J")
public class JobPosition {

	@Id
	@Column(name="TIT_J_ID")
	private Integer id;
	
	@Column(name="TIT_J_NAME")
	private String name;
	
	@Column(name="TIT_J_ABBR")
	private String abbr;

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

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
	
	
}
