package th.go.dss.BackOffice.model.PLN;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name="GLB_PROJECT")
public class Project {
	@Id
	@Column(name="PROJECT_ID")
	private Integer id;
	
	@Column(name="PROJECT_NAME")
	private String name;
	
	@Column(name="PROJECT_CODE")
	private String code;
	
	@Column(name="FISCAL_YEAR")
	private Integer fiscalYear;
	
	@OneToMany
	@JoinColumn(name="PROJ_PROJECT_ID")
	Set<SubProject> subProjectSet;
	
	@ManyToOne
	@JoinColumn(name="PROD_PRODUCT_ID")
	private Product product;

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

	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	@JsonManagedReference("Project-SubProject")
	public Set<SubProject> getSubProjects() {
		return subProjectSet;
	}

	public void setSubProjects(Set<SubProject> subProjects) {
		this.subProjectSet = subProjects;
	}

	public Set<SubProject> getSubProjectSet() {
		return subProjectSet;
	}

	public void setSubProjectSet(Set<SubProject> subProjectSet) {
		this.subProjectSet = subProjectSet;
	}

	@JsonBackReference("Product-Project")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
