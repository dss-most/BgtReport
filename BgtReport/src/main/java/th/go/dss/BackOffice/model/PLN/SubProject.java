package th.go.dss.BackOffice.model.PLN;

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

import th.go.dss.BackOffice.model.BGT.BudgetAllocation;
import th.go.dss.BackOffice.model.HRX.Organization;

import com.mysema.query.annotations.QueryInit;

@Entity
@Table(name="GLB_SUB_PROJECT")
public class SubProject {

	@Id
	@Column(name="SUB_PROJ_ID")
	private Integer id;
	
	@Column(name="SUB_PROJ_NAME")
	private String name;
	
	@Column(name="SUB_PROJ_CODE")
	private String code;
	
	@Column(name="SUB_PROJ_ABBR")
	private String abbr;
	
	@OneToMany
	@JoinColumn(name="GSP_SUB_PROJ_ID")
	@JsonBackReference("SubProject-BudgetAllocation")
	Set<BudgetAllocation> budgetAllocationSet;
	
	@ManyToOne
	@JoinColumn(name="PROJ_PROJECT_ID")
	@JsonBackReference("Project-SubProject")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="ORG_ORG_ID")
	private Organization owner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public Set<BudgetAllocation> getBudgetAllocationSet() {
		return budgetAllocationSet;
	}

	public void setBudgetAllocationSet(Set<BudgetAllocation> budgetAllocationSet) {
		this.budgetAllocationSet = budgetAllocationSet;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Organization getOwner() {
		return owner;
	}

	public void setOwner(Organization owner) {
		this.owner = owner;
	}
	
	
}
