package th.go.dss.BackOffice.model.PCM;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import th.go.dss.BackOffice.model.BGT.BudgetAllocationItem;

@Entity
@Table(name="PRO_ITEM")
public class PurchaseLineItem {
	@javax.persistence.Id
	@Column(name="ITEM_ID")
	private Long Id;
	
	@Column(name="SEQ_NO")
	private Integer index;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="UOM")
	private String uom;
	
	@Column(name="EST_PRICE")
	private Double unitPrice;
	
	@Column(name="TOTAL_UNIT")
	private Double totalUnit;
	
	@Column(name="TOTAL_BGT")
	private Double totalBudget;
	
	@ManyToOne
	@JoinColumn(name="pro_proc_t_trans_id")
	@JsonBackReference("PurchaseRequest-PurchaseLineItem")
	private PurchaseRequest purchaseRequest;
	
	@ManyToOne
	@JoinColumn(name="BABI_ID")
	private BudgetAllocationItem allocationItem;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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

	public Double getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(Double totalUnit) {
		this.totalUnit = totalUnit;
	}

	public Double getTotalBudget() {
		return totalBudget;
	}

	public void setTotalBudget(Double totalBudget) {
		this.totalBudget = totalBudget;
	}

	public PurchaseRequest getPurchaseRequest() {
		return purchaseRequest;
	}

	public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
		this.purchaseRequest = purchaseRequest;
	}

	public BudgetAllocationItem getAllocationItem() {
		return allocationItem;
	}

	public void setAllocationItem(BudgetAllocationItem allocationItem) {
		this.allocationItem = allocationItem;
	}
	
	
	
}
