package th.go.dss.BackOffice.model.HRX;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

@Entity
@Table(name="GLB_ORGANIZATION")
public class Organization implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ORG_ID")
	private Integer id;
	
	@Column(name="ORG_NAME")
	private String name;
	
	@Column(name="ORG_ABBR")
	private String abbr;
	

	@ManyToOne
	@JoinColumn(name="ORG_ORG_ID")
	private Organization parent;
	
	@ManyToOne
	@JoinColumn(name="SUPER_PARENT_ID")
	private Organization superParent;

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

	@JsonBackReference("Organization-parent")
	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	@JsonBackReference("Organization-parent")
	public Organization getSuperParent() {
		return superParent;
	}

	public void setSuperParent(Organization superParent) {
		this.superParent = superParent;
	}
	
	
}
