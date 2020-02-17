package th.go.dss.BackOffice.model.PCM;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRO_VENDOR")
public class Vendor {
	
	@Id
	@Column(name="VENDOR_ID")
	private Long id;
	
	@Column(name="COMPANY")
	private String companyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
