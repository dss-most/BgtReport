package th.go.dss.BackOffice.model.BGT;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

@Entity
@Table(name="BGT_INSTALL_DET")
public class DebitedBudget {
	
	@Id
	@Column(name="ID")
	private Integer id;
	
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@ManyToOne
	@JoinColumn(name="PAB_ALLOC_BGT_ID")
	private BudgetAllocation budgetAllocation;
	
	@ManyToOne
	@JoinColumn(name="ALLOC_BGT_ITEM_ID")
	private BudgetAllocationItem budgetAllocationItem;

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

	@JsonBackReference("BudgetAllocationItem-DebitedBudget")
	public BudgetAllocationItem getBudgetAllocationItemS() {
		return budgetAllocationItem;
	}

	public void setBudgetAllocationItem(BudgetAllocationItem budgetAllocationItem) {
		this.budgetAllocationItem = budgetAllocationItem;
	}

	@JsonBackReference("BudgetAllocation-DebitedBudget")
	public BudgetAllocation getBudgetAllocation() {
		return budgetAllocation;
	}

	public void setBudgetAllocation(BudgetAllocation budgetAllocation) {
		this.budgetAllocation = budgetAllocation;
	} 
	
	

}
