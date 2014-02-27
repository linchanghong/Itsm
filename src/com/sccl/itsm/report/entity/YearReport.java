package com.sccl.itsm.report.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.User;


@XmlRootElement
@Entity
@Table(name = "T_PLAN_YEAR", schema = "ITSM")
@NamedQueries({
	@NamedQuery(name="findYearReportByUserId",query="SELECT y FROM YearReport y where y.opid.id=:userId"),
	@NamedQuery(name="findYearReports", query="SELECT y FROM YearReport y"),
	@NamedQuery(name="YearReport.status",query="UPDATE YearReport y SET y.status=:statusId where y.id=:id"),
	@NamedQuery(name="YearReport.delete",query="DELETE YearReport y where y.id=:id")
})
public class YearReport extends TableEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2790782915968294009L;
    private Long id;              //主键　
    private String title;         //标题
	private Integer year;         //年
	private String content;       //内容
	private Date beginDate;       //开始时间
	private Date endDate;         //结束时间
	private User opid;            //操作人
	private Integer status;       //状态
	private SupportSystem systemId;            //所属支撑团队，即对应系统 ;
	private Integer moduleId;
	
	 public String statusName;
	
	/**
     * @Title:YearReport
     * @Description:无参构造
     */
	public YearReport(){
		super();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PLAN_TEAR")
	@SequenceGenerator(name = "GEN_PLAN_TEAR", sequenceName = "SEQ_PLAN_TEAR", allocationSize = 1)
    @Column(name = "T_PLAN_YEAR_ID", nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="YEAR")
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="BEGINDATE")
	@Temporal(TemporalType.DATE)
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@Column(name="ENDDATE")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@ManyToOne
    @JoinColumn(name="OPID")
	public User getOpid() {
		return opid;
	}
	public void setOpid(User opid) {
		this.opid = opid;
	}
	@Column(name="T_PLAN_YEAR_STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@ManyToOne
    @JoinColumn(name="SYSTEMID")
	public SupportSystem getSystemId() {
		return systemId;
	}
	public void setSystemId(SupportSystem systemId) {
		this.systemId = systemId;
	}
	@Column(name="MODULEID")
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
}
