package com.sccl.itsm.report.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "T_PLAN", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="ProjectPlan.findAll",query="SELECT p FROM ProjectPlan p")
    })
public class ProjectPlan extends TableEntity implements java.io.Serializable {

	private static final long serialVersionUID = -891909760010925929L;
    
	@Expose private Integer id;
	@Expose private String pName;
	@Expose private Date pStartDate;
	@Expose private Date pFinishDate;
	@Expose private String pContent;
	@Expose private String pState;
	@Expose private String pDescription;
	@Expose private String pParticipants;
	@Expose private Integer parentId;
	@Expose private String parentPath;
	
	public ProjectPlan(){
		
	}
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PLAN")
	@SequenceGenerator(name = "GEN_PLAN", sequenceName = "SEQ_PLAN", allocationSize = 1)
	@Column(name = "PLAN_ID", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PLAN_NAME", nullable = false)
	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@Column(name = "PLAN_START_DATE")
	@Temporal(TemporalType.DATE)
	public Date getpStartDate() {
		return pStartDate;
	}

	public void setpStartDate(Date pStartDate) {
		this.pStartDate = pStartDate;
	}

	@Column(name = "PLAN_FINISH_DATE")
	@Temporal(TemporalType.DATE)
	public Date getpFinishDate() {
		return pFinishDate;
	}

	public void setpFinishDate(Date pFinishDate) {
		this.pFinishDate = pFinishDate;
	}

	@Column(name = "PLAN_CONTENT")
	public String getpContent() {
		return pContent;
	}

	public void setpContent(String pContent) {
		this.pContent = pContent;
	}

	@Column(name = "PLAN_STATE")
	public String getpState() {
		return pState;
	}

	public void setpState(String pState) {
		this.pState = pState;
	}

	@Column(name = "PLAN_DESCRIPTION")
	public String getpDescription() {
		return pDescription;
	}

	public void setpDescription(String pDescription) {
		this.pDescription = pDescription;
	}

	@Column(name = "PLAN_PARTICIPANTS")
	public String getpParticipants() {
		return pParticipants;
	}

	public void setpParticipants(String pParticipants) {
		this.pParticipants = pParticipants;
	}

	@Column(name = "PLAN_PARENT_ID", nullable = false)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "PLAN_PATENT_PATH")
	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
}
