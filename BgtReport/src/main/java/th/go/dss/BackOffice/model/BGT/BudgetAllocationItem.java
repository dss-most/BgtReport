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
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name="PLN_ALLOC_BGT_ITEM")
public class BudgetAllocationItem {

	@Id
	@Column(name="ALLOC_BGT_ITEM_ID")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="PALB_ALLOC_BGT_ID")
	private BudgetAllocation budgetAllocation;
	

	@Column(name="ALLOC_AMOUNT")
	private String amount;
	

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="QTY") 
	private Integer qty;
	
	@Column(name="UOM")
	private String uom;
	
	@Column(name="UNIT_PRICE")
	private Double unitPrice;
	
	@OneToOne
	@JoinColumn(name="BGT_INST_ID")
	private CreditedBudget creditedBudget;
	
	
	@JsonIgnore
	@OneToMany
	@JoinColumn(name="ALLOC_BGT_ITEM_ID")
	private Set<DebitedBudget> debitedBudgetSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonBackReference("BudgetAllocation-BudgetAllocationItem")
	public BudgetAllocation getBudgetAllocation() {
		return budgetAllocation;
	}

	public void setBudgetAllocation(BudgetAllocation budgetAllocation) {
		this.budgetAllocation = budgetAllocation;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@JsonManagedReference("BudgetAllocationItem-DebitedBudget")
	public Set<DebitedBudget> getDebitedBudgetSet() {
		return debitedBudgetSet;
	}

	public void setDebitedBudgetSet(Set<DebitedBudget> debitedBudgetSet) {
		this.debitedBudgetSet = debitedBudgetSet;
	}

	@JsonBackReference("CreditedBudget-BudgetLLocationIem")
	public CreditedBudget getCreditedBudget() {
		return creditedBudget;
	}

	public void setCreditedBudget(CreditedBudget creditedBudget) {
		this.creditedBudget = creditedBudget;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	
	
	
	
	
}
