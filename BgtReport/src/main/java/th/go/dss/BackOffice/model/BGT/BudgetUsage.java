package th.go.dss.BackOffice.model.BGT;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Formula;

import th.go.dss.BackOffice.model.HRX.Organization;
import th.go.dss.BackOffice.model.PCM.PurchaseApproval;
import th.go.dss.BackOffice.model.USR.User;

@Entity
@Table(name="PLN_APPV_BGT")
public class BudgetUsage {
	@Id
	@Column(name="APPV_ID")
	private Integer id;
	
	@Column(name="APPV_CODE")
	private String bgtCode;
	
	@Column(name="SUBJECT_NAME")
	private String subject;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Formula("get_desc_appv(APPV_ID)")
	private String moreDescription;
	
	@Column(name="APPV_AMT")
	private Double approvedAmount;
	
	@Column(name="PAID_AMT")
	private Double paidAmount;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="DGA_CODE")
	private String dgaCode;
	
	@Column(name="CANCEL_FLG")
	private Character cancelFlag;
	
	@Column(name="VOUCHER_NUMBER")
	private String voucherNumber;
	
	@Column(name="APPV_FLG")
	private Character approveFlag;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="VOU_DATE")
	private Date voucherDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name="CREATED_BY")
	private User createdByUser;
	
	@Transient
	private String subProjectAbbr;
	
	@Transient
	private String subProjectName;
	
	@Transient
	private Organization owner;
	
	@ManyToOne
	@JoinColumn(name="PAB_ALLOC_BGT_ID")
	@JsonBackReference("BudgetAllocation-BudgetUsage")
	private BudgetAllocation budgetAllocation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROC_APP_ID")
	@JsonBackReference("purchaseApproval-budgetUsage")
	private PurchaseApproval purchaseApproval;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBgtCode() {
		return bgtCode;
	}

	public void setBgtCode(String bgtCode) {
		this.bgtCode = bgtCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Double approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDgaCode() {
		return dgaCode;
	}

	public void setDgaCode(String dgaCode) {
		this.dgaCode = dgaCode;
	}

	public Character getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(Character cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public Character getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(Character approveFlag) {
		this.approveFlag = approveFlag;
	}

	
	
	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	
	
	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public BudgetAllocation getBudgetAllocation() {
		return budgetAllocation;
	}

	public void setBudgetAllocation(BudgetAllocation budgetAllocation) {
		this.budgetAllocation = budgetAllocation;
	}

	public String getSubProjectName() {
		if(subProjectName == null && this.budgetAllocation != null) {
			subProjectName = this.budgetAllocation.getSubProject().getName();
		}
		return subProjectName;
	}

	public String getSubProjectAbbr() {
		if(subProjectAbbr == null && this.budgetAllocation != null) {
			subProjectAbbr = this.budgetAllocation.getSubProject().getAbbr();
		}
		return subProjectAbbr;
	}
	
	public Organization getOwner() {
		if(owner == null && this.budgetAllocation != null) {
			owner = this.budgetAllocation.getSubProject().getOwner();
		}
		return owner;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getMoreDescription() {
		return moreDescription;
	}

	public void setMoreDescription(String moreDescription) {
		this.moreDescription = moreDescription;
	}

	public PurchaseApproval getPurchaseApproval() {
		return purchaseApproval;
	}

	public void setPurchaseApproval(PurchaseApproval purchaseApproval) {
		this.purchaseApproval = purchaseApproval;
	}

	
	
}
