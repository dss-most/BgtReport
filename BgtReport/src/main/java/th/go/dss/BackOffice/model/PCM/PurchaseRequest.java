package th.go.dss.BackOffice.model.PCM;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import th.go.dss.BackOffice.model.HRX.Employee;
import th.go.dss.BackOffice.model.HRX.Organization;
import th.go.dss.BackOffice.model.PLN.SubProject;

@Entity
@Table(name="PRO_PROC_TRAN")
public class PurchaseRequest {
	@Id
	@Column(name="TRANS_ID")
	private Long id;
	
	@Basic
	@Column(name="SUBJECT_ID")
	private String pcmNumber;

	@OneToMany(mappedBy="purchaseRequest")
	private Set<PurchaseApproval> purchaseApprovals;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_DATE")
	private Date createdDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SUB_PROJ_ID")
	@JsonBackReference("SubProject-PurchaseRequest")
	private SubProject subProject;

	@OneToOne(mappedBy="purchaseRequest")
	private PurchaseJob purchaseJob;
	
	@Transient
	public String getSubProjectAbbr() {
		return this.subProject.getAbbr();
	}
	
	@Transient
	private Organization owner;
	
	@Transient
	public Double getTotalBudget() {
		Double total=0.0;
		if(purchaseLineItems != null) {
			for(PurchaseLineItem line : purchaseLineItems) {
				if(line != null) {
					if(line.getTotalBudget() != null) {
						total += line.getTotalBudget();
					}
				}
			}
		}
		return total;
	}
	
	@Column(name="CANCEL_FLG")
	private Character cancelFlag;
	
	@Column(name="SUBJECT_REM")
	private String subject;
	
	@OneToMany(mappedBy="purchaseRequest")
	@OrderColumn(name="SEQ_NO")
	@JsonManagedReference("PurchaseRequest-PurchaseLineItem")
	private List<PurchaseLineItem> purchaseLineItems;
	
	@OneToOne
	@JoinColumn(name="STATUS_STATUS_ID")
	private PurchaseRequestStatus status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPcmNumber() {
		return pcmNumber;
	}

	public void setPcmNumber(String pcmNumber) {
		this.pcmNumber = pcmNumber;
	}


	public Set<PurchaseApproval> getPurchaseApprovals() {
		return purchaseApprovals;
	}

	public void setPurchaseApprovals(Set<PurchaseApproval> purchaseApprovals) {
		this.purchaseApprovals = purchaseApprovals;
	}

	public SubProject getSubProject() {
		return subProject;
	}

	public void setSubProject(SubProject subProject) {
		this.subProject = subProject;
	}


	public PurchaseJob getPurchaseJob() {
		return purchaseJob;
	}


	public void setPurchaseJob(PurchaseJob purchaseJob) {
		this.purchaseJob = purchaseJob;
	}

	public Character getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(Character cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	
	public Organization getOwner() {
		if(owner==null && this.getSubProject().getOwner() != null) {
			owner=this.getSubProject().getOwner();
		}
		
		return owner;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAssignedToName() {
		Integer statusId = status.getId();
		
		if(statusId == 1 || statusId == 21) {
			return "";
		} else if(purchaseJob  == null && (statusId != 1 || statusId != 21)) {
			return "ดำเนินการเอง";
		} else if(purchaseJob == null && statusId == 66) {
			return "รอหัวหน้างานพัสดุมอบหมายงาน";
		} else {
			return purchaseJob.getAssignedToName(); 
		}
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<PurchaseLineItem> getPurchaseLineItems() {
		return purchaseLineItems;
	}

	public void setPurchaseLineItems(List<PurchaseLineItem> purchaseLineItems) {
		this.purchaseLineItems = purchaseLineItems;
	}

	public PurchaseRequestStatus getStatus() {
		return status;
	}

	public void setStatus(PurchaseRequestStatus status) {
		this.status = status;
	}
	
	
	
}
