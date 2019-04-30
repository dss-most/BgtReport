package th.go.dss.BackOffice.model.BGT;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import th.go.dss.BackOffice.model.PLN.SubProject;

import com.mysema.query.annotations.QueryInit;

@Entity
@Table(name="PLN_ALLOC_BGT")
public class BudgetAllocation {
	
	@Id
	@Column(name="ALLOC_BGT_ID")
	private Integer id;
	
	@Column(name="ALLOC_AMOUNT")
	private Double amount = 0.0;
	
	@Column(name="TRANSFER_AMT")
	private Double transferAmount = 0.0; 
	
	@ManyToOne
	@JoinColumn(name="GSP_SUB_PROJ_ID")
	@QueryInit("*")
	private SubProject subProject;
	
	@ManyToOne
	@JoinColumn(name="EXPT_EXPENSE_TYPE_ID")
	@QueryInit("*")
	private BudgetCode budgetCode;
	
	@OneToMany
	@JoinColumn(name="PAB_ALLOC_BGT_ID")
	private Set<CreditedBudget> creditedBudgetSet;  
	
	@OneToMany
	@JoinColumn(name="PAB_ALLOC_BGT_ID")
	private Set<DebitedBudget> debitedBudgetSet;
	
	@OneToMany
	@JoinColumn(name="PALB_ALLOC_BGT_ID")
	private Set<BudgetAllocationItem> budgetAllocationItemSet;

	@OneToMany
	@JoinColumn(name="PAB_ALLOC_BGT_ID")
	@JsonManagedReference("BudgetAllocation-BudgetUsage")
	private Set<BudgetUsage> budgetUsageSet;
	

	@Transient
	private Double totalCredited;
	
	@Transient 
	private Double totalDebited;
	
	@Transient
	private Double totalBudgetUsed;
	
	@Transient
	private Double totalBudgetReserved;
	
	
	
	
	public Double fetchTotalCredited() {
		Double returnVal = 0.0;
		for(CreditedBudget credit : creditedBudgetSet) {
			returnVal += credit.getAmount();
		}
		return returnVal;
	}
	

	public Double fetchTotalDebited() {
		Double returnVal = 0.0;
		for(DebitedBudget debit : debitedBudgetSet) {
			returnVal += debit.getAmount();
		}
		
		// still have to loop over allocation item if any is get debited
		for(BudgetAllocationItem bItem : budgetAllocationItemSet) {
			for(DebitedBudget dBudgetItem : bItem.getDebitedBudgetSet()) {
				returnVal += dBudgetItem.getAmount();
			}
		}
		
		return returnVal;
	}
	

	public Double fetchTotalUsed() {
		Double returnVal = 0.0;
		for(BudgetUsage budgetUsage : budgetUsageSet) {
			if(budgetUsage.getPaidAmount() != null) {
				returnVal += budgetUsage.getPaidAmount();
			}
		}
		return returnVal;
	}
	

	public Double fetchTotalReserved() {
		Double returnVal = 0.0;
		for(BudgetUsage budgetUsage : budgetUsageSet) {
			if(budgetUsage.getPaidAmount() == null) {
				returnVal += budgetUsage.getApprovedAmount() != null ? budgetUsage.getApprovedAmount() : 0;
			}
		}
		return returnVal;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@JsonBackReference("SubProject-BudgetAllocation")
	public SubProject getSubProject() {
		return subProject;
	}

	public void setSubProject(SubProject subProject) {
		this.subProject = subProject;
	}

	public BudgetCode getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(BudgetCode budgetCode) {
		this.budgetCode = budgetCode;
	}

	@JsonManagedReference("BudgetAllocation-CreditedBudget")
	public Set<CreditedBudget> getCreditedBudgetSet() {
		return creditedBudgetSet;
	}

	public void setCreditedBudgetSet(Set<CreditedBudget> creditedBudgetSet) {
		this.creditedBudgetSet = creditedBudgetSet;
	}

	@JsonManagedReference("BudgetAllocation-DebitedBudget")
	public Set<DebitedBudget> getDebitedBudgetSet() {
		return debitedBudgetSet;
	}

	public void setDebitedBudgetSet(Set<DebitedBudget> debitedBudgetSet) {
		this.debitedBudgetSet = debitedBudgetSet;
	}

	@JsonManagedReference("BudgetAllocation-BudgetAllocationItem")
	public Set<BudgetAllocationItem> getBudgetAllocationItemSet() {
		return budgetAllocationItemSet;
	}

	public void setBudgetAllocationItemSet(
			Set<BudgetAllocationItem> budgetAllocationItemSet) {
		this.budgetAllocationItemSet = budgetAllocationItemSet;
	}


	public Set<BudgetUsage> getBudgetUsageSet() {
		return budgetUsageSet;
	}

	public void setBudgetUsageSet(Set<BudgetUsage> budgetUsageSet) {
		this.budgetUsageSet = budgetUsageSet;
	}


	public Double getTotalCredited() {
		return totalCredited;
	}


	public void setTotalCredited(Double totalCredited) {
		this.totalCredited = totalCredited;
	}


	public Double getTotalDebited() {
		return totalDebited;
	}


	public void setTotalDebited(Double totalDebited) {
		this.totalDebited = totalDebited;
	}


	public Double getTotalBudgetUsed() {
		return totalBudgetUsed;
	}


	public void setTotalBudgetUsed(Double totalBudgetUsed) {
		this.totalBudgetUsed = totalBudgetUsed;
	}


	public Double getTotalBudgetReserved() {
		return totalBudgetReserved;
	}


	public void setTotalBudgetReserved(Double totalBudgetReserved) {
		this.totalBudgetReserved = totalBudgetReserved;
	}


	public Double getTransferAmount() {
		return transferAmount;
	}


	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	

}
