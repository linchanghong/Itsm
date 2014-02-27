package com.sccl.itsm.assessManager.entity;

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

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.itsm.assessManager.entity.WorkReport.java
 * @ClassName: WorkReport
 * @Description: TODO-
 * @author:Liaoxuwei
 * @UpdateUser:Liaoxuwei
 * @UpdateDate:2013-9-06
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-28 上午10:13:20
 * @Version:v1.0
 */


@XmlRootElement
@Entity
@Table(name = "T_WORK_REPORT", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findWorkReportByUserId", query="SELECT w FROM WorkReport w where w.dr=0 and w.reporter.id=:userId"),
    @NamedQuery(name="findWorkReports", query="SELECT w FROM WorkReport w where w.dr=0"),
    @NamedQuery(name="WorkReport.status",query="UPDATE WorkReport w SET w.status=:statusId where w.id=:id"),
    @NamedQuery(name="WorkReport.delete",query="UPDATE WorkReport w SET w.dr=1 where w.id=:id")
    })
public class WorkReport extends TableEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 739146831800908758L;
	
	
    private Long id;               //主键
    private String title;          //报告标题
    private String content;        //报告内容
    private Integer type;          //报告类型，1：个人日报，2：个人周报，3：项目周报，4：项目月报
    private Date reportDate;       //填报时间
    private Date startDate;        //开始日期
    private Date endDate;          //截止日期
    private User reporter;         //填报人
    private SupportSystem belongsSystem;            //所属支撑团队，即对应系统
    private Integer dr;            //删除标志
    private Integer status;        //报告状态,0:未呈报,1:流程中,2:已审批
    private Integer planFinish;    //计划完成情况，0：未完成，1~100：完成度
    private String plancontent;    //计划内容
	
    public String typeName;
    public String statusName;
    /**
     * @Title:WorkReport
     * @Description:无参构造
     */
    public WorkReport() {
        super();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_WORK_REPORT")
	@SequenceGenerator(name = "GEN_WORK_REPORT", sequenceName = "SEQ_WORK_REPORT", allocationSize = 1)
    @Column(name = "WORK_REPORT_ID", nullable = false)
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "REPORT_TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "REPORT_CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "REPORT_TYPE")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "REPORT_DATE")
	@Temporal(TemporalType.DATE)
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ManyToOne
    @JoinColumn(name="REPORTER")
	public User getReporter() {
		return reporter;
	}
	public void setReporter(User reporter) {
		this.reporter = reporter;
	}
	
	@ManyToOne
    @JoinColumn(name="BELONGS_SYSTEM")
	public SupportSystem getBelongsSystem() {
		return belongsSystem;
	}
	public void setBelongsSystem(SupportSystem belongsSystem) {
		this.belongsSystem = belongsSystem;
	}
	
	@Column(name = "DR")
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	
	@Column(name = "REPORT_STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "PLAN_FINISH")
	public Integer getPlanFinish() {
		return planFinish;
	}
	public void setPlanFinish(Integer planFinish) {
		this.planFinish = planFinish;
	}
	
	@Column(name = "PLAN_CONTENT")
	public String getPlancontent() {
		return plancontent;
	}
	public void setPlancontent(String plancontent) {
		this.plancontent = plancontent;
	}
	
    
}
