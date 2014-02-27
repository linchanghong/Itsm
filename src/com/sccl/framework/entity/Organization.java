package com.sccl.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
@Entity
@Table(name = "ORGANIZATION", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findOrgOrdered",query="SELECT o FROM Organization o where 1=1 order by o.id, o.parentId"),
    @NamedQuery(name="Organization.findAllCompany",query="SELECT o FROM Organization o where o.orgType = 1 order by o.id, o.parentId")
    })
public class Organization extends  TableEntity implements Serializable {
	
	private static final long serialVersionUID = 1669955578365175357L;
	
	@Expose private Integer id;
	@Expose private String orgName;
	@Expose private String orgCode;
	/**
	 * 1表示公司，0表示部门
	 */
	@Expose private Byte orgType;
	@Expose private Integer parentId;
	@Expose private String parentPath; // 所有父节点路径，如：,1,2,3,4,5,6,
	@Expose private String companyId;//部门上级公司id，从下到上父节点中第一个orgType为1的org的id；
	@Expose private String remark;
	
	public Organization() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ORGANIZATION")
	@SequenceGenerator(name = "GEN_ORGANIZATION", sequenceName = "SEQ_ORGANIZATION", allocationSize = 1)
	@Column(name = "ORG_ID", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "ORG_NAME", length = 50, nullable = false)
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Column(name = "ORG_CODE", length = 50)
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	

	@Column(name = "ORG_TYPE", nullable = false)
	public Byte getOrgType() {
		return orgType;
	}
	public void setOrgType(Byte orgType) {
		this.orgType = orgType;
	}
	
	@Column(name = "PARENT_ID", nullable = false)
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "COMPANY_ID")
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "PARENT_PATH", length = 500)
	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

}
