package th.go.dss.BackOffice.model.PCM;

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
@Table(name="PRO_ITEM_APP_NO")
public class PurchaseApprovalItemized {

	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="APP_NO")
	private String appNo; // หมายเลข pcm ต่อการเบิก 
	
	@ManyToOne
	@JoinColumn(name="PRO_PROC_T_TRANS_ID")
//	@JsonBackReference("PurchaseRequest-PurchaseApprovalItemized")
	private PurchaseRequest purchaseRequest;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="purchaseApprovalItemized")
	private BudgetUsage budgetUsage;
	
	@ManyToOne
	@JoinColumn(name="VENDOR_VENDOR_ID")
	private Vendor vendor;
	
	@Column(name="APP_PERIOD_NO")
	private String periodNo;
	
	@Column(name="PERIOD_FULL_MON")
	private String periodFullMonth;
	
	@Column(name="ITEM_AMT")
	private Double itemAmount;
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
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

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getPeriodNo() {
		return periodNo;
	}

	public void setPeriodNo(String periodNo) {
		this.periodNo = periodNo;
	}

	public String getPeriodFullMonth() {
		return periodFullMonth;
	}

	public void setPeriodFullMonth(String periodFullMonth) {
		this.periodFullMonth = periodFullMonth;
	}

	public Double getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}
	
	
	
}
