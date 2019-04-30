package th.go.dss.BackOffice.model.PLN;

import java.io.Serializable;
import java.util.List;

import th.go.dss.BackOffice.model.BGT.BudgetAllocationItem;

public class SubProjectBudget implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7401847168257636902L;
	
	
	private Integer id;
	private String abbr;
	private String name;
	private String orgAbbr;
	private Integer orgId;
	private Double debitedBudget;
	private Double budgetAllocated;
	private Double budgetApproved;
	private Double budgetUsed;
	
	private List<BudgetAllocationItemWeb> budgetAllocationItems;
	
	public SubProjectBudget(SubProject sp) {
		this.id = sp.getId();
		this.abbr = sp.getAbbr();
		this.name = sp.getName();
		this.orgAbbr = sp.getOwner().getAbbr();
		this.orgId = sp.getOwner().getId();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgAbbr() {
		return orgAbbr;
	}
	public void setOrgAbbr(String orgAbbr) {
		this.orgAbbr = orgAbbr;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Double getBudgetAllocated() {
		return budgetAllocated;
	}
	public void setBudgetAllocated(Double budgetAllocated) {
		this.budgetAllocated = budgetAllocated;
	}
	public Double getBudgetUsed() {
		return budgetUsed;
	}
	public void setBudgetUsed(Double budgetUsed) {
		this.budgetUsed = budgetUsed;
	}
	public Double getBudgetApproved() {
		return budgetApproved;
	}
	public void setBudgetApproved(Double budgetApproved) {
		this.budgetApproved = budgetApproved;
	}
	public Double getDebitedBudget() {
		return debitedBudget;
	}
	public void setDebitedBudget(Double debitedBudget) {
		this.debitedBudget = debitedBudget;
	}
	public List<BudgetAllocationItemWeb> getBudgetAllocationItems() {
		return budgetAllocationItems;
	}
	public void setBudgetAllocationItems(
			List<BudgetAllocationItemWeb> budgetAllocationItems) {
		this.budgetAllocationItems = budgetAllocationItems;
	}

	

}
