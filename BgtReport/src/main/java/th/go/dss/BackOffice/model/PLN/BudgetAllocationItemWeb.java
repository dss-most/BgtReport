package th.go.dss.BackOffice.model.PLN;

import th.go.dss.BackOffice.model.BGT.BudgetAllocationItem;

public class BudgetAllocationItemWeb {
	private Integer id;
	private String description;
	private String unit;
	private Integer qty;
	private Integer allocatedAmount;
	
	private String pcmNumber;
	private String pcmCreatedDate;
	
	private String bgtNumber;
	private String bgtCreatedDate;
	
	
	
	public BudgetAllocationItemWeb(BudgetAllocationItem item) {
		this.id = item.getId();
		this.description = item.getDescription();
		this.unit = item.getUom();
		this.qty = item.getQty();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getPcmNumber() {
		return pcmNumber;
	}
	public void setPcmNumber(String pcmNumber) {
		this.pcmNumber = pcmNumber;
	}
	public String getPcmCreatedDate() {
		return pcmCreatedDate;
	}
	public void setPcmCreatedDate(String pcmCretedDate) {
		this.pcmCreatedDate = pcmCretedDate;
	}
	public String getBgtNumber() {
		return bgtNumber;
	}
	public void setBgtNumber(String bgtNumber) {
		this.bgtNumber = bgtNumber;
	}
	public String getBgtCreatedDate() {
		return bgtCreatedDate;
	}
	public void setBgtCreatedDate(String bgtCreatedDate) {
		this.bgtCreatedDate = bgtCreatedDate;
	}
	
	
	
}
