package th.go.dss.BackOffice.model.HRX;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="HR_HEAD_COUNT")
public class Position {
	
	@Id
	@Column(name="HEAD_COUNT_ID")
	private Integer id;
	
	/**
	 * เลขที่ตำแหน่ง
	 */
	@Column(name="POS_CODE")
	private String posCode;
	
	/**
	 * ชนิดตำแหน่ง
	 * '1' = ข้าราชการ
	 * '2' = ลูกจ้างประจำ
	 * '3' = ลูกจ้างชั่วคราว
	 * 'A' = พนักงานราชการ
	 */
	@Column(name="POS_TYPE")
	private Character posType;
	
	/**
	 * ชื่อตำแหน่ง
	 */
	@ManyToOne
	@JoinColumn(name="TITLE_J_TIT_J_ID")
	private JobPosition jobPosition;
	
	/**
	 *  ตำแหน่งทางบริหาร
	 */
	@ManyToOne
	@JoinColumn(name="TITLE_M_TIT_M_ID")
	private ManagementPosition managementPosition;
	
	@Column(name="CEO_FLG")
	private Character ceoFlag;
	
	@ManyToOne
	@JoinColumn(name="INT_ORG_ID")
	private Organization internalOrg;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public Character getPosType() {
		return posType;
	}

	public void setPosType(Character posType) {
		this.posType = posType;
	}

	public JobPosition getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(JobPosition jobPosition) {
		this.jobPosition = jobPosition;
	}

	public ManagementPosition getManagementPosition() {
		return managementPosition;
	}

	public void setManagementPosition(ManagementPosition managementPosition) {
		this.managementPosition = managementPosition;
	}

	public Character getCeoFlag() {
		return ceoFlag;
	}

	public void setCeoFlag(Character ceoFlag) {
		this.ceoFlag = ceoFlag;
	}

	public Organization getInternalOrg() {
		return internalOrg;
	}

	public void setInternalOrg(Organization internalOrg) {
		this.internalOrg = internalOrg;
	}
	
	
	
	
}
