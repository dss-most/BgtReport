package th.go.dss.BackOffice.model.BGT;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="GLB_EXPENSE_TYPE")
public class BudgetCode {
	
	@Id
	@Column(name="EXPENSE_TYPE_ID")
	private Integer id;
	
	@Column(name="EXPENSE_TYPE_CODE")
	private String code;
	
	@Column(name="EXPENSE_TYPE_NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="SUPER_PARENT_ID")
	private BudgetCode topBudgetCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BudgetCode getTopBudgetCode() {
		return topBudgetCode;
	}

	public void setTopBudgetCode(BudgetCode topBudgetCode) {
		this.topBudgetCode = topBudgetCode;
	}
	
	
	
	

}
