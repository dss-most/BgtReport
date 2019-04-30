package th.go.dss.BackOffice.model.PLN;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name="GLB_PRODUCT")
public class Product {
	
	@Id
	@Column(name="PRODUCT_ID")
	private Integer id;
	
	@Column(name="PRODUCT_NAME")
	private String name;
	
	@Column(name="PRODUCT_ABBR")
	private String abbr;
	
	@Column(name="PRODUCT_CODE")
	private String code;
	
	@OneToMany
	@JoinColumn(name="PROD_PRODUCT_ID")
	private Set<Project> projectSet;

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

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	
		public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonManagedReference("Product-Project")
	public Set<Project> getProjectSet() {
		return projectSet;
	}

	public void setProjectSet(Set<Project> projectSet) {
		this.projectSet = projectSet;
	}

	
}
