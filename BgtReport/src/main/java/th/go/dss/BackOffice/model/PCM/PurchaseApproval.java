package th.go.dss.BackOffice.model.PCM;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import th.go.dss.BackOffice.model.BGT.BudgetUsage;

@Entity
@Table(name="PRO_PROC_APP")
public class PurchaseApproval {

	@Id
	@Column(name="APP_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="PRO_PROC_T_TRANS_ID")
	@JsonBackReference("PurchaseRequest-PurchaseApproval")
	private PurchaseRequest purchaseRequest;

	@OneToOne(fetch=FetchType.LAZY, mappedBy="purchaseApproval")
	private BudgetUsage budgetUsage;
	
	@Basic
	@Column(name="APP_NO")
	private String appNo;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public BudgetUsage getBudgetUsage() {
		return budgetUsage;
	}

	public void setBudgetUsage(BudgetUsage budgetUsage) {
		this.budgetUsage = budgetUsage;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	
	
	
	
}
