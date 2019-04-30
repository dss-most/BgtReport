package th.go.dss.BackOffice.model.PCM;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.go.dss.BackOffice.model.HRX.Employee;

@Entity
@Table(name="PRO_PROC_T_DESC")
public class PurchaseJob {
	

	private static final Logger logger = LoggerFactory.getLogger(PurchaseJob.class);
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="pro_proc_t_trans_id")
	@JsonBackReference("PurchaseRequest-PurchaseJob")
	private PurchaseRequest purchaseRequest;
	
	@OneToOne
	@JoinColumn(name="EMP_EMP_ID")
	@JsonBackReference("Employee-PurchaseJob")
	private Employee assignedTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssignedToName() {
		if(this.assignedTo == null ) {
			logger.error("PurchaseJob.id: " + this.id + " has null assignedTo");
			return null;
		}
		return this.assignedTo.getEmpName();
	}
	
	public Employee getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Employee assignedTo) {
		this.assignedTo = assignedTo;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}
	
	
}
