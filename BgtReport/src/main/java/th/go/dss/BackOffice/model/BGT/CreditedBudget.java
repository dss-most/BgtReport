package th.go.dss.BackOffice.model.BGT;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name="BGT_INSTALL")
public class CreditedBudget {

	@Id
	@Column(name="ID")
	private Integer id;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@ManyToOne
	@JoinColumn(name="PAB_ALLOC_BGT_ID")
	private BudgetAllocation budgetAllocation;
	
	@OneToMany
	@JoinColumn(name="BI_ID")
	Set<DebitedBudget> debitedBudgetSet;
	
	@OneToOne(mappedBy="creditedBudget")
	BudgetAllocationItem budgetAllocationItem;

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

	@JsonBackReference("BudgetAllocation-CreditedBudget")
	public BudgetAllocation getBudgetAllocation() {
		return budgetAllocation;
	}

	public void setBudgetAllocation(BudgetAllocation budgetAllocation) {
		this.budgetAllocation = budgetAllocation;
	}

	@JsonManagedReference("CreditedBudget-DebitedBudget")
	public Set<DebitedBudget> getDebitedBudgetSet() {
		return debitedBudgetSet;
	}

	public void setDebitedBudgetSet(Set<DebitedBudget> debitedBudgetSet) {
		this.debitedBudgetSet = debitedBudgetSet;
	}

	@JsonManagedReference("CreditedBudget-BudgetLLocationIem")
	public BudgetAllocationItem getBudgetAllocationItem() {
		return budgetAllocationItem;
	}

	public void setBudgetAllocationItem(BudgetAllocationItem budgetAllocationItem) {
		this.budgetAllocationItem = budgetAllocationItem;
	}
	
	
	
	
}
