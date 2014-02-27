/**
 * @Title: UserApply.java
 * @Package com.sccl.serviceManager.bugManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-7-23 上午11:13:20
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.entity;

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


import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.TableEntity;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.entity.UserApply.java
 * @ClassName: UserApply
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-23
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-23 上午11:13:20
 * @Version:v1.0
 */
@XmlRootElement
//@Scope("prototype")
@Entity
@Table(name = "T_USER_APPLY", schema = "ITSM")
public class UserApplyAdd extends TableEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4669037305996656458L;
    /**
     * 主键
     */
    private Long applyId;
    /**
     * 用户请求单编号
     */
    private String userApplyCode = "";
    /**
     * 请求类型1- 问题 2-bug 3-需求
     */
    private Integer applyType;
    /**
     * 请求单的填写者，可以是用户，也可以是支撑人员
     */
    private User applyEntry;
    /**
     * 提出请求的具体用户
     */
    private User sponsor;
    /**
     * 请求发起人对应的公司
     */
    private String company = "";
    /**
     * 请求发起人对应的部门
     */
    private String department = "";
    /**
     * 请求发起人的联系电话
     */
    private String telephone = "";
    /**
     * 请求发起时间
     */
    private Date applyStartDate;
    /**
     * 期望解决时间
     */
    private Date applyEndDate;
    /**
     * 请求来源，用户登录系统录入，用户通过QQ电话等提交
     */
    private ConstDetail applyOrigin;
    /**
     * 请求的紧急程度
     */
    private ReplyLevel urgent;
    /**
     * 问题可能影响的范围
     */
    private ConstDetail range;
    /**
     * 请求对应的业务系统
     */
    private SupportSystem belongSystem;
    /**
     * 请求所对应的业务模块
     */
    private SystemModule sysModule;
//    /**
//     * 响应时间
//     */
//    private Date replyTime;
//    /**
//     * 受理人
//     */
//    private User replyer;
//    /**
//     * 计划解决时间
//     */
//    private Date planFinishTime;
//    /**
//     * 实际解决时间
//     */
//    private Date realFinishTime;
    /**
     * 请求主题摘要
     */
    private String applyTitle = "";
    /**
     * 请求描述
     */
    private String directions = "";
//    /**
//     * 问题的实际影响范围
//     */
//    private ConstDetail realRange;
//    /**
//     * 原因分析
//     */
//    private String reason = "";
//    /**
//     * 处理方法
//     */
//    private String solutions = "";
    /**
     * 需求提出理由
     */
    private String applyReason = "";
    /**
     * 请求单状态
     */
    private Short applyStatus;
    /**
     * 文档集合
     */
//    private List<Attachments> attachments;
    /**
     * 是否删除：0-未删除；1-已删除
     */
    private Integer DR = 1;
    
    
    
    /**
     * @Title:UserApply
     * @Description:无参构造
     */
    public UserApplyAdd() {
        super();
    }
    
    
    
    /**
     * @return the applyId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_USER_APPLY")
    @SequenceGenerator(name = "GEN_USER_APPLY", sequenceName = "SEQ_USERAPPLY", allocationSize = 1)
    @Column(name = "USER_APPLY_ID", nullable = false)
    public Long getApplyId() {
        return applyId;
    }
    /**
     * @param applyId the applyId to set
     */
    public void setApplyId(Long uid) {
        this.applyId = uid;
    }
    /**
     * @return the UserApplyCode
     */
    @Column(name = "USER_APPLY_CODE")
    public String getUserApplyCode() {
        return userApplyCode;
    }
    /**
     * @param UserApplyCode the UserApplyCode to set
     */
    public void setUserApplyCode(String UserApplyCode) {
        this.userApplyCode = UserApplyCode;
    }
    /**
     * @return the applyType
     */
    @Column(name = "APPLY_TYPE")
    public Integer getApplyType() {
        return applyType;
    }
    /**
     * @param applyType the applyType to set
     */
    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }
    
    /**
     * @return the applyEntry
     */
    @ManyToOne
    @JoinColumn(name="APPLY_ENTRY")
    public User getApplyEntry() {
        return applyEntry;
    }

    /**
     * @param applyEntry the applyEntry to set
     */
    public void setApplyEntry(User applyEntry) {
        this.applyEntry = applyEntry;
    }

    /**
     * @return the sponsor
     */
    @ManyToOne
    @JoinColumn(name="SPONSOR")
    public User getSponsor() {
        return sponsor;
    }

    /**
     * @param sponsor the sponsor to set
     */
    public void setSponsor(User sponsor) {
        this.sponsor = sponsor;
    }

//    /**
//     * @return the replyer
//     */
//    @ManyToOne
//    @JoinColumn(name="REPLYER")
//    public User getReplyer() {
//        return replyer;
//    }
//
//    /**
//     * @param replyer the replyer to set
//     */
//    public void setReplyer(User replyer) {
//        this.replyer = replyer;
//    }


    /**
     * @return the company
     */
    @Column(name = "COMPANY")
    public String getCompany() {
        return company;
    }
    
    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }
    /**
     * @return the department
     */
    @Column(name = "DEPARTMENT")
    public String getDepartment() {
        return department;
    }
    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    /**
     * @return the telephone
     */
    @Column(name = "TELEPHONE")
    public String getTelephone() {
        return telephone;
    }
    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /**
     * @return the applyStartDate
     */
    @Column(name = "APPLY_START_DATE")
    @Temporal(TemporalType.DATE)
    public Date getApplyStartDate() {
        return applyStartDate;
    }
    /**
     * @param applyStartDate the applyStartDate to set
     */
    public void setApplyStartDate(Date applyStartDate) {
        this.applyStartDate = applyStartDate;
    }
    /**
     * @return the applyEndDate
     */
    @Column(name = "APPLY_END_DATE")
    @Temporal(TemporalType.DATE)
    public Date getApplyEndDate() {
        return applyEndDate;
    }
    /**
     * @param applyEndDate the applyEndDate to set
     */
    public void setApplyEndDate(Date applyEndDate) {
        this.applyEndDate = applyEndDate;
    }
    /**
     * @return the applyOrigin
     */
    @ManyToOne
    @JoinColumn(name="APPLY_ORIGIN")
    public ConstDetail getApplyOrigin() {
        return applyOrigin;
    }
    /**
     * @param applyOrigin the applyOrigin to set
     */
    public void setApplyOrigin(ConstDetail applyOrigin) {
        this.applyOrigin = applyOrigin;
    }
    /**
     * @return the urgent
     */
    @ManyToOne
    @JoinColumn(name="URGENT")
    public ReplyLevel getUrgent() {
        return urgent;
    }
    /**
     * @param urgent the urgent to set
     */
    public void setUrgent(ReplyLevel urgent) {
        this.urgent = urgent;
    }
    /**
     * @return the range
     */
    @ManyToOne
    @JoinColumn(name="RANGE")
    public ConstDetail getRange() {
        return range;
    }
    /**
     * @param range the range to set
     */
    public void setRange(ConstDetail range) {
        this.range = range;
    }
    /**
     * @return the belongSystem
     */
    
    /**
     * @return the belongSystem
     */
    @ManyToOne
    @JoinColumn(name="BELONGS_SYSTEM")
    public SupportSystem getBelongSystem() {
        return belongSystem;
    }

    /**
     * @param belongSystem the belongSystem to set
     */
    public void setBelongSystem(SupportSystem belongSystem) {
        this.belongSystem = belongSystem;
    }
    
    /**
     * @return the sysModule
     */
    @ManyToOne
    @JoinColumn(name = "BELONGS_BUSINESS")
    public SystemModule getSysModule() {
        return sysModule;
    }

    /**
     * @param sysModule the sysModule to set
     */
    public void setSysModule(SystemModule sysModule) {
        this.sysModule = sysModule;
    }

//    /**
//     * @return the replyTime
//     */
//    @Column(name = "REPLY_TIME")
//    @Temporal(TemporalType.DATE)
//    public Date getReplyTime() {
//        return replyTime;
//    }
//    /**
//     * @param replyTime the replyTime to set
//     */
//    public void setReplyTime(Date replyTime) {
//        this.replyTime = replyTime;
//    }
//
//    /**
//     * @return the planFinishTime
//     */
//    @Column(name = "PLAN_FINISH_TIME")
//    @Temporal(TemporalType.DATE)
//    public Date getPlanFinishTime() {
//        return planFinishTime;
//    }
//
//    /**
//     * @param planFinishTime the planFinishTime to set
//     */
//    public void setPlanFinishTime(Date planFinishTime) {
//        this.planFinishTime = planFinishTime;
//    }
//    /**
//     * @return the realFinishTime
//     */
//    @Column(name = "REAL_FINISH_TIME")
//    @Temporal(TemporalType.DATE)
//    public Date getRealFinishTime() {
//        return realFinishTime;
//    }
//    /**
//     * @param realFinishTime the realFinishTime to set
//     */
//    public void setRealFinishTime(Date realFinishTime) {
//        this.realFinishTime = realFinishTime;
//    }
    /**
     * @return the applyTitle
     */
    @Column(name = "APPLY_TITLE")
    public String getApplyTitle() {
        return applyTitle;
    }
    /**
     * @param applyTitle the applyTitle to set
     */
    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }
    /**
     * @return the directions
     */
    @Column(name = "DIRECTIONS")
    public String getDirections() {
        return directions;
    }
    /**
     * @param directions the directions to set
     */
    public void setDirections(String directions) {
        this.directions = directions;
    }
    /**
     * @return the realRange
     */
    
//    /**
//     * @return the realRange
//     */
//    @ManyToOne
//    @JoinColumn(name="REAL_RANGE")
//    public ConstDetail getRealRange() {
//        return realRange;
//    }
//    /**
//     * @param realRange the realRange to set
//     */
//    public void setRealRange(ConstDetail realRange) {
//        this.realRange = realRange;
//    }
//    /**
//     * @return the reason
//     */
//    @Column(name = "REASON")
//    public String getReason() {
//        return reason;
//    }
//    /**
//     * @param reason the reason to set
//     */
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//    /**
//     * @return the solutions
//     */
//    @Column(name = "SOLUTIONS")
//    public String getSolutions() {
//        return solutions;
//    }
//    /**
//     * @param solutions the solutions to set
//     */
//    public void setSolutions(String solutions) {
//        this.solutions = solutions;
//    }
    /**
     * @return the applyReason
     */
    @Column(name = "APPLY_REASON")
    public String getApplyReason() {
        return applyReason;
    }
    /**
     * @param applyReason the applyReason to set
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }
    
    /**
     * @return the applyStatus
     */
    @Column(name="APPLY_STATUS")
    public Short getApplyStatus() {
        return applyStatus;
    }

    /**
     * @param applyStatus the applyStatus to set
     */
    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }
    
    /**
     * @return the Attachments
     */
//    @OneToMany(mappedBy="apply")
//    public List<Attachments> getAttachments() {
//        return attachments;
//    }
//    /**
//     * @param Attachments the Attachments to set
//     */
//    public void setAttachments(List<Attachments> attachments) {
//        this.attachments = attachments;
//    }



    /**
     * @return the DR
     */
    @Column(name = "DR")
    public Integer getDR() {
        return DR;
    }

    /**
     * @param DR the DR to set
     */
    public void setDR(Integer DR) {
        this.DR = DR;
    }
    
}
