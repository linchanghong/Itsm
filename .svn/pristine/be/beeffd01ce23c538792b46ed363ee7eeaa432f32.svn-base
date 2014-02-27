/**
 * @Title: Demand4Flow.java
 * @Package com.sccl.serviceManager.demandManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-29 下午5:32:29
 * @version:V1.0 
 */
package com.sccl.serviceManager.demandManager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.Attachment;
import com.sccl.framework.entity.TableEntity;
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.SystemModule;
import com.sccl.serviceManager.bugManager.entity.User;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.demandManager.entity.Demand4Flow.java
 * @ClassName: Demand4Flow
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-29
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-29 下午5:32:29
 * @Version:v1.0
 */
@XmlRootElement
@Entity
@Table(name = "T_USER_APPLY", schema = "ITSM")
public class Demand4Flow extends TableEntity implements Serializable {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = 5281622242603483722L;

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
     * 请求主题摘要
     */
    private String applyTitle = "";
    /**
     * 请求对应的业务系统
     */
    private SupportSystem belongSystem;
    /**
     * 请求所对应的业务模块
     */
    private SystemModule sysModule;
    /**
     * 响应时间
     */
    private Date replyTime;
    /**
     * 计划解决时间
     */
    private Date planFinishTime;
    /**
     * 需求经理
     */
    private User projectManager;
    /**
     * 开发经理
     */
    private User developManager;
    /**
     * 请求描述
     */
    private String directions = "";
    /**
     * 请求单状态
     */
    private Short applyStatus = 0;
    /**
     * 子需求单总数
     */
    private Short subDemandCount = 0;
    /**
     * 剩余未完成子需求单数量
     */
    private Short subDemandCompletedCount = 0;
    
    
    
    /**
     * 该客户需求单的所有需求服务单集合
     */
    public List<SubDemand> subDemands = new ArrayList<SubDemand>();
    /**
     * 用户请求对象中的文档集合
     */
    public List<Attachment> attachments = new ArrayList<Attachment>();
    
    
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
    /**
     * @return the replyTime
     */
    @Column(name = "REPLY_TIME")
    @Temporal(TemporalType.DATE)
    public Date getReplyTime() {
        return replyTime;
    }
    /**
     * @param replyTime the replyTime to set
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
    /**
     * @return the planFinishTime
     */
    @Column(name = "PLAN_FINISH_TIME")
    @Temporal(TemporalType.DATE)
    public Date getPlanFinishTime() {
        return planFinishTime;
    }
    /**
     * @param planFinishTime the planFinishTime to set
     */
    public void setPlanFinishTime(Date planFinishTime) {
        this.planFinishTime = planFinishTime;
    }
    /**
     * @return the projectManager
     */
    @ManyToOne
//    @JoinColumn(name="PROJECT_MANAGER")
    @JoinColumn(name="REPLYER")
    public User getProjectManager() {
        return projectManager;
    }
    /**
     * @param projectManager the projectManager to set
     */
    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }
    /**
     * @return the developManager
     */
    @ManyToOne
    @JoinColumn(name="DEVELOPER_MANAGER")
    public User getDevelopManager() {
        return developManager;
    }
    /**
     * @param developManager the developManager to set
     */
    public void setDevelopManager(User developManager) {
        this.developManager = developManager;
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
        if(applyStatus != null)
            this.applyStatus = applyStatus;
    }
    /**
     * @return the subDemandCount
     */
    @Column(name="SUB_DEMAND_COUNT")
    public Short getSubDemandCount() {
        return subDemandCount;
    }
    /**
     * @param subDemandCount the subDemandCount to set
     */
    public void setSubDemandCount(Short subDemandCount) {
        if(subDemandCount == null)
            subDemandCount = 0;
        this.subDemandCount = subDemandCount;
    }
    /**
     * @return the subDemandCompletedCount
     */
    @Column(name="SUB_DEMAND_COMPLETED_COUNT")
    public Short getSubDemandRemainingCount() {
        return subDemandCompletedCount;
    }
    /**
     * @param subDemandCompletedCount the subDemandCompletedCount to set
     */
    public void setSubDemandRemainingCount(Short subDemandCompletedCount) {
        if(subDemandCompletedCount == null)
            subDemandCompletedCount = 0;
        this.subDemandCompletedCount = subDemandCompletedCount;
    }
}
