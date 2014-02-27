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
@Table(name = "T_PLAN_MONTH", schema = "ITSM")
@NamedQueries({
	@NamedQuery(name="findMonthReportByUserId",query="SELECT m FROM MonthReport m where m.opid.id=:userId"),
	@NamedQuery(name="findMonthReports", query="SELECT m FROM MonthReport m"),
	@NamedQuery(name="MonthReport.status",query="UPDATE MonthReport m SET m.status=:statusId where m.id=:id"),
	@NamedQuery(name="MonthReport.delete",query="DELETE MonthReport m where m.id=:id")
})
public class MonthReport extends TableEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3043950485833814562L;
	
	private Long id;              //主键
	private YearReport yearId;    //外键
	private String title;         //标题
	

	private Integer month;         //月
	private String content;       //内容
	private Date beginDate;       //开始时间
	private Date endDate;         //结束时间
	private User opid;            //操作人
	private Integer status;       //状态
	private SupportSystem systemId;            //所属支撑团队，即对应系统 ;
	private Integer moduleId;
	
	public String statusName;
	
	/**
     * @Title:MonthReport
     * @Description:无参构造
     */
	public MonthReport(){
		super();
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PLAN_MONTH")
	@SequenceGenerator(name = "GEN_PLAN_MONTH", sequenceName = "SEQ_PLAN_MONTH", allocationSize = 1)
    @Column(name = "T_PLAN_MONTH_ID", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    @ManyToOne
    @JoinColumn(name="T_PLAN_YEAR_ID")
	public YearReport getYearId() {
		return yearId;
	}

	public void setYearId(YearReport yearId) {
		this.yearId = yearId;
	}
    @Column(name="MONTH")
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	@Column(name="T_PLAN_MONTH_STATUS")
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
