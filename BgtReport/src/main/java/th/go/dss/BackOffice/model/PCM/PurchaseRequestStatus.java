package th.go.dss.BackOffice.model.PCM;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRO_STATUS")
public class PurchaseRequestStatus {
	@Id
	@Column(name="STATUS_ID")
	private Integer id;
	
	@Column(name="REMARK")
	private String state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

}
