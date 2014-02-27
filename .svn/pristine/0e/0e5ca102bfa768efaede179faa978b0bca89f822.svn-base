package com.sccl.flow.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sccl.framework.entity.TableEntity;


/**
 * FlowNodeInstence entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_FLOWNODEINSTENCE", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="FLOWNODEINSTENCE",schema="ITSM")
public class FlowNodeInstence extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -8769340106861193498L;
	private Long instId;
     private Long nodeId;
     private String nodeNumber;
     private int nodeAttr;
     private Long flowModelId;
     private Long flowId;
     private String moduleCode;
     private int checkState;
     private boolean makeRubbish;
     private String dealWithIdea;
     private String handlerName;
     private Long handlerId;
     private int moduleID;
     private Date handleDate;
     private String nextids;
     private String other1;
     private String other2;
     private int totalFlowId;
     private Long senderId;
     private String senderName;
     private Long senderDeptId;
     private double nodeOrder;
     private String flowRoleName;
     private int dealWithDay;
     private Date comeDate;
     private boolean isNodeOneself;
     private boolean isNeedCheckDate;
     private boolean isSameRoleUserPass;
     private boolean autoBy;


    // Constructors

    /** default constructor */
    public FlowNodeInstence() {
    }

	/** minimal constructor */
    public FlowNodeInstence(Long instId) {
        this.instId = instId;
    }
    
    /** full constructor */
    public FlowNodeInstence(Long instId, Long nodeId, String nodeNumber, int nodeAttr, Long flowModelId, Long flowId, String moduleCode, int checkState, boolean makeRubbish, String dealWithIdea, String handlerName, Long handlerId, int moduleID, Date handleDate, String nextids, String other1, String other2, int totalFlowId, Long senderId, String senderName, Long senderDeptId, double nodeOrder, String flowRoleName, int dealWithDay, Date comeDate, boolean isNodeOneself, boolean isNeedCheckDate, boolean isSameRoleUserPass, boolean autoBy) {
        this.instId = instId;
        this.nodeId = nodeId;
        this.nodeNumber = nodeNumber;
        this.nodeAttr = nodeAttr;
        this.flowModelId = flowModelId;
        this.flowId = flowId;
        this.moduleCode = moduleCode;
        this.checkState = checkState;
        this.makeRubbish = makeRubbish;
        this.dealWithIdea = dealWithIdea;
        this.handlerName = handlerName;
        this.handlerId = handlerId;
        this.moduleID = moduleID;
        this.handleDate = handleDate;
        this.nextids = nextids;
        this.other1 = other1;
        this.other2 = other2;
        this.totalFlowId = totalFlowId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderDeptId = senderDeptId;
        this.nodeOrder = nodeOrder;
        this.flowRoleName = flowRoleName;
        this.dealWithDay = dealWithDay;
        this.comeDate = comeDate;
        this.isNodeOneself = isNodeOneself;
        this.isNeedCheckDate = isNeedCheckDate;
        this.isSameRoleUserPass = isSameRoleUserPass;
        this.autoBy = autoBy;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(generator = "GEN_FLOWNODEINSTENCE", strategy = GenerationType.SEQUENCE)
    @Column(name="INSTID", unique=true, nullable=false, precision=22, scale=0)
    public Long getInstId() {
        return this.instId;
    }
    
    public void setInstId(Long instId) {
        this.instId = instId;
    }
    
    @Column(name="NODEID", precision=22, scale=0)

    public Long getNodeId() {
        return this.nodeId;
    }
    
    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
    @Column(name="NODENUMBER", length=200)

    public String getNodeNumber() {
        return this.nodeNumber;
    }
    
    public void setNodeNumber(String nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
    
    @Column(name="NODEATTR", precision=8, scale=0)

    public int getNodeAttr() {
        return this.nodeAttr;
    }
    
    public void setNodeAttr(int nodeAttr) {
        this.nodeAttr = nodeAttr;
    }
    
    @Column(name="FLOWMODELID", precision=22, scale=0)

    public Long getFlowModelId() {
        return this.flowModelId;
    }
    
    public void setFlowModelId(Long flowModelId) {
        this.flowModelId = flowModelId;
    }
    
    @Column(name="FLOWID", precision=22, scale=0)

    public Long getFlowId() {
        return this.flowId;
    }
    
    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }
    
    @Column(name="MODULECODE", length=50)

    public String getModuleCode() {
        return this.moduleCode;
    }
    
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
    
    @Column(name="CHECKSTATE", precision=8, scale=0)

    public int getCheckState() {
        return this.checkState;
    }
    
    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }
    
    @Column(name="MAKERUBBISH", precision=2, scale=0)

    public boolean getMakeRubbish() {
        return this.makeRubbish;
    }
    
    public void setMakeRubbish(boolean makeRubbish) {
        this.makeRubbish = makeRubbish;
    }
    
    @Column(name="DEALWITHIDEA", length=4000)

    public String getDealWithIdea() {
        return this.dealWithIdea;
    }
    
    public void setDealWithIdea(String dealWithIdea) {
        this.dealWithIdea = dealWithIdea;
    }
    
    @Column(name="HANDLERNAME", length=50)

    public String getHandlerName() {
        return this.handlerName;
    }
    
    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }
    
    @Column(name="HANDLERID", precision=22, scale=0)

    public Long getHandlerId() {
        return this.handlerId;
    }
    
    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }
    
    @Column(name="MODULEID", precision=8, scale=0)

    public int getModuleID() {
        return this.moduleID;
    }
    
    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="HANDLEDATE", length=7)

    public Date getHandleDate() {
        return this.handleDate;
    }
    
    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }
    
    @Column(name="NEXTIDS", length=200)

    public String getNextids() {
        return this.nextids;
    }
    
    public void setNextids(String nextids) {
        this.nextids = nextids;
    }
    
    @Column(name="OTHER1", length=4000)

    public String getOther1() {
        return this.other1;
    }
    
    public void setOther1(String other1) {
        this.other1 = other1;
    }
    
    @Column(name="OTHER2", length=4000)

    public String getOther2() {
        return this.other2;
    }
    
    public void setOther2(String other2) {
        this.other2 = other2;
    }
    
    @Column(name="TOTALFLOWID", precision=8, scale=0)

    public int getTotalFlowId() {
        return this.totalFlowId;
    }
    
    public void setTotalFlowId(int totalFlowId) {
        this.totalFlowId = totalFlowId;
    }
    
    @Column(name="SENDERID", precision=22, scale=0)

    public Long getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    
    @Column(name="SENDERNAME", length=50)

    public String getSenderName() {
        return this.senderName;
    }
    
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    @Column(name="SENDERDEPTID", precision=22, scale=0)

    public Long getSenderDeptId() {
        return this.senderDeptId;
    }
    
    public void setSenderDeptId(Long senderDeptId) {
        this.senderDeptId = senderDeptId;
    }
    
    @Column(name="NODEORDER", precision=8)

    public double getNodeOrder() {
        return this.nodeOrder;
    }
    
    public void setNodeOrder(double nodeOrder) {
        this.nodeOrder = nodeOrder;
    }
    
    @Column(name="FLOWROLENAME", length=500)

    public String getFlowRoleName() {
        return this.flowRoleName;
    }
    
    public void setFlowRoleName(String flowRoleName) {
        this.flowRoleName = flowRoleName;
    }
    
    @Column(name="DEALWITHDAY", precision=8, scale=0)

    public int getDealWithDay() {
        return this.dealWithDay;
    }
    
    public void setDealWithDay(int dealWithDay) {
        this.dealWithDay = dealWithDay;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="COMEDATE", length=7)

    public Date getComeDate() {
        return this.comeDate;
    }
    
    public void setComeDate(Date comeDate) {
        this.comeDate = comeDate;
    }
    
    @Column(name="ISNODEONESELF", precision=2, scale=0)

    public boolean getIsNodeOneself() {
        return this.isNodeOneself;
    }
    
    public void setIsNodeOneself(boolean isNodeOneself) {
        this.isNodeOneself = isNodeOneself;
    }
    
    @Column(name="ISNEEDCHECKDATE", precision=2, scale=0)

    public boolean getIsNeedCheckDate() {
        return this.isNeedCheckDate;
    }
    
    public void setIsNeedCheckDate(boolean isNeedCheckDate) {
        this.isNeedCheckDate = isNeedCheckDate;
    }
    
    @Column(name="ISSAMEROLEUSERPASS", precision=2, scale=0)

    public boolean getIsSameRoleUserPass() {
        return this.isSameRoleUserPass;
    }
    
    public void setIsSameRoleUserPass(boolean isSameRoleUserPass) {
        this.isSameRoleUserPass = isSameRoleUserPass;
    }
    
    @Column(name="AUTOBY", precision=2, scale=0)

    public boolean getAutoBy() {
        return this.autoBy;
    }
    
    public void setAutoBy(boolean autoBy) {
        this.autoBy = autoBy;
    }
}