/**
 * @Title: SubDemand4Mod.java
 * @Package com.sccl.serviceManager.demandManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-10-10 上午10:25:04
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
import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.TableEntity;
import com.sccl.serviceManager.bugManager.entity.ReplyLevel;
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.SystemModule;
import com.sccl.serviceManager.bugManager.entity.User;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.demandManager.entity.SubDemand4Mod.java
 * @ClassName: SubDemand4Mod
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-10-10
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-10-10 上午10:25:04
 * @Version:v1.0
 */
@XmlRootElement
@Entity
@Table(name = "T_DEMANDS_PART", schema = "ITSM")
public class SubDemand4Mod extends TableEntity implements Serializable {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = -3472103579657196267L;
    /**
     * 用户需求分解表主键
     */
    private Long userDemandId;
    /**
     * 主需求单对象id
     */
    private Long userApplyId;
    /**
     * 子需求标题
     */
    private String demandTitle;
    /**
     * 需求描述
     */
    private String directions;
    /**
     * 重要程度
     */
    private ReplyLevel urgent;
    /**
     * 影响范围
     */
    private ConstDetail range;
    /**
     * 所属系统
     */
    private SupportSystem belongsSystem;
    /**
     * 所属业务模块
     */
    private SystemModule belongsBusiness;
    /**
     * 需求状态，0：未提交，1：需求分析，2：开发经理指派人员，3：开发，4：单元测试，5：功能测试，6：项目经理审核，7：完成
     */
    private Long demandStatus;
    /**
     * 需求发起人，项目经理
     */
    private User sponsor;
    /**
     * 子需求的发起时间
     */
    private Date initDate;
    /**
     * 由项目经理指定需求人员
     */
    private User analyst;
    /**
     * 项目经理填写预计需求分析完成时间
     */
    private Date planAnalysisDate;
    /**
     * 项目经理指定开发经理
     */
    private User developManager;
    /**
     * 项目经理填写预计开发完成时间
     */
    private Date planDevelopDate;
    /**
     * 项目经理填写预计单元测试完成时间
     */
    private Date planUtestDate;
    /**
     * 需求人员担当功能测试
     */
    private User funcTester;
    /**
     * 项目经理填写预计功能测试完成时间
     */
    private Date planFtestDate;
    /**
     * 是否删除--0：未删除；1：已删除
     */
    private Long dr;
    
    
    
    /**
     * 子需求单状态描述
     */
    public String status = "未提交";
    /**
     * 用户请求对象中的文档集合
     */
    public List<Attachment> attachments = new ArrayList<Attachment>();
    
    
    
    

    // Constructors

    /** default constructor */
    public SubDemand4Mod() {
    }

    /** minimal constructor */
    public SubDemand4Mod(Long userDemandId) {
        this.userDemandId = userDemandId;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SUB_DEMAND")
    @SequenceGenerator(name = "GEN_SUB_DEMAND", sequenceName = "SEQ_SUB_DEMAND", allocationSize = 1)
    @Column(name = "USER_DEMAND_ID", unique = true, nullable = false, precision = 10, scale = 0)
    public Long getUserDemandId() {
        return this.userDemandId;
    }

    public void setUserDemandId(Long userDemandId) {
        this.userDemandId = userDemandId;
    }

    @Column(name = "USER_APPLY_ID", precision = 10, scale = 0)
    public Long getUserApplyId() {
        return this.userApplyId;
    }

    public void setUserApplyId(Long userApplyId) {
        this.userApplyId = userApplyId;
    }

    @Column(name = "DEMAND_TITLE", length = 800)
    public String getDemandTitle() {
        return this.demandTitle;
    }

    public void setDemandTitle(String demandTitle) {
        this.demandTitle = demandTitle;
    }

    @Column(name = "DIRECTIONS", length = 2000)
    public String getDirections() {
        return this.directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
    
    @ManyToOne
    @JoinColumn(name = "URGENT")
    public ReplyLevel getUrgent() {
        return this.urgent;
    }

    public void setUrgent(ReplyLevel urgent) {
        this.urgent = urgent;
    }

    @ManyToOne
    @JoinColumn(name="RANGE")
    public ConstDetail getRange() {
        return this.range;
    }

    public void setRange(ConstDetail range) {
        this.range = range;
    }

    @ManyToOne
    @JoinColumn(name="BELONGS_SYSTEM")
    public SupportSystem getBelongsSystem() {
        return this.belongsSystem;
    }

    public void setBelongsSystem(SupportSystem belongsSystem) {
        this.belongsSystem = belongsSystem;
    }

    @ManyToOne
    @JoinColumn(name = "BELONGS_BUSINESS")
    public SystemModule getBelongsBusiness() {
        return this.belongsBusiness;
    }

    public void setBelongsBusiness(SystemModule belongsBusiness) {
        this.belongsBusiness = belongsBusiness;
    }

    @Column(name = "DEMAND_STATUS", precision = 10, scale = 0)
    public Long getDemandStatus() {
        return this.demandStatus;
    }

    public void setDemandStatus(Long demandStatus) {
        this.demandStatus = demandStatus;
    }

    @ManyToOne
    @JoinColumn(name="SPONSOR")
    public User getSponsor() {
        return this.sponsor;
    }

    public void setSponsor(User sponsor) {
        this.sponsor = sponsor;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "INIT_DATE", length = 7)
    public Date getInitDate() {
        return this.initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    @ManyToOne
    @JoinColumn(name="ANALYST")
    public User getAnalyst() {
        return this.analyst;
    }

    public void setAnalyst(User analyst) {
        this.analyst = analyst;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PLAN_ANALYSIS_DATE", length = 7)
    public Date getPlanAnalysisDate() {
        return this.planAnalysisDate;
    }

    public void setPlanAnalysisDate(Date planAnalysisDate) {
        this.planAnalysisDate = planAnalysisDate;
    }

    @ManyToOne
    @JoinColumn(name="DEVELOP_MANAGER")
    public User getDevelopManager() {
        return this.developManager;
    }

    public void setDevelopManager(User developManager) {
        this.developManager = developManager;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PLAN_DEVELOP_DATE", length = 7)
    public Date getPlanDevelopDate() {
        return this.planDevelopDate;
    }

    public void setPlanDevelopDate(Date planDevelopDate) {
        this.planDevelopDate = planDevelopDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PLAN_UTEST_DATE", length = 7)
    public Date getPlanUtestDate() {
        return this.planUtestDate;
    }

    public void setPlanUtestDate(Date planUtestDate) {
        this.planUtestDate = planUtestDate;
    }

    @ManyToOne
    @JoinColumn(name = "FUNC_TESTER")
    public User getFuncTester() {
        return this.funcTester;
    }

    public void setFuncTester(User funcTester) {
        this.funcTester = funcTester;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PLAN_FTEST_DATE", length = 7)
    public Date getPlanFtestDate() {
        return this.planFtestDate;
    }

    public void setPlanFtestDate(Date planFtestDate) {
        this.planFtestDate = planFtestDate;
    }

    @Column(name = "DR", precision = 22, scale = 0)
    public Long getDr() {
        return this.dr;
    }

    public void setDr(Long dr) {
        this.dr = dr;
    }

}