/**
 * @Title: BugFlowInstence.java
 * @Package com.sccl.serviceManager.bugManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-8-28 下午1:26:24
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.entity.BugFlowInstence.java
 * @ClassName: BugFlowInstence
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-8-28
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-8-28 下午1:26:24
 * @Version:v1.0
 */
@Entity
@SequenceGenerator(name = "GEN_FLOWINSTENCE", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="FLOWINSTENCE",schema="ITSM")
public class BugFlowInstence extends TableEntity implements Serializable {


    // Fields    

     /**
     * 
     */
    private static final long serialVersionUID = -1832032341072326263L;
    private Long flowId;
     private Long oprtTypeID;
     private Long flowModelId;
     private BigInteger billID;
     private int flowState;
     private Long senderId;
     private Date sendTime;
     private String FlowTitle;
     private String other1;
     private String other2;
     private String customerName;
     private String subOrBgnName;
     private Double businessMoney;
     private Long flowDeptId;


    // Constructors

    /** default constructor */
    public BugFlowInstence() {
    }

    /** minimal constructor */
    public BugFlowInstence(Long flowId) {
        this.flowId = flowId;
    }
    
    /** full constructor */
    public BugFlowInstence(Long flowId, Long oprtTypeID, Long flowModelId, BigInteger billID, int flowState, Long senderId, Date sendTime, String FlowTitle, String other1, String other2, String customerName, String subOrBgnName, Double businessMoney, Long flowDeptId) {
        this.flowId = flowId;
        this.oprtTypeID = oprtTypeID;
        this.flowModelId = flowModelId;
        this.billID = billID;
        this.flowState = flowState;
        this.senderId = senderId;
        this.sendTime = sendTime;
        this.FlowTitle = FlowTitle;
        this.other1 = other1;
        this.other2 = other2;
        this.customerName = customerName;
        this.subOrBgnName = subOrBgnName;
        this.businessMoney = businessMoney;
        this.flowDeptId = flowDeptId;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(generator = "GEN_FLOWINSTENCE", strategy = GenerationType.SEQUENCE)
    @Column(name="FLOWID", unique=true, nullable=false, precision=22, scale=0)
    public Long getFlowId() {
        return this.flowId;
    }
    
    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }
    
    @Column(name="OPRTTYPEID", precision=22, scale=0)

    public Long getOprtTypeID() {
        return this.oprtTypeID;
    }
    
    public void setOprtTypeID(Long oprtTypeID) {
        this.oprtTypeID = oprtTypeID;
    }
    
    @Column(name="FLOWMODELID", precision=22, scale=0)

    public Long getFlowModelId() {
        return this.flowModelId;
    }
    
    public void setFlowModelId(Long flowModelId) {
        this.flowModelId = flowModelId;
    }
    
    @Column(name="BILLID", precision=22, scale=0)

    public BigInteger getBillID() {
        return this.billID;
    }
    
    public void setBillID(BigInteger billID) {
        this.billID = billID;
    }
    
    @Column(name="FLOWSTATE", precision=8, scale=0)

    public int getFlowState() {
        return this.flowState;
    }
    
    public void setFlowState(int flowState) {
        this.flowState = flowState;
    }
    
    @Column(name="SENDERID", precision=22, scale=0)

    public Long getSenderId() {
        return this.senderId;
    }
    
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="SENDTIME", length=7)

    public Date getSendTime() {
        return this.sendTime;
    }
    
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
    
    @Column(name="FLOWTITLE", length=500)

    public String getFlowTitle() {
        return this.FlowTitle;
    }
    
    public void setFlowTitle(String FlowTitle) {
        this.FlowTitle = FlowTitle;
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
    
    @Column(name="CUSTOMERNAME", length=500)

    public String getCustomerName() {
        return this.customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    @Column(name="SUBORBGNNAME", length=500)

    public String getSubOrBgnName() {
        return this.subOrBgnName;
    }
    
    public void setSubOrBgnName(String subOrBgnName) {
        this.subOrBgnName = subOrBgnName;
    }
    
    @Column(name="BUSINESSMONEY", precision=38, scale=4)

    public Double getBusinessMoney() {
        return this.businessMoney;
    }
    
    public void setBusinessMoney(Double businessMoney) {
        this.businessMoney = businessMoney;
    }
    
    @Column(name="FLOWDEPTID", precision=22, scale=0)

    public Long getFlowDeptId() {
        return this.flowDeptId;
    }
    
    public void setFlowDeptId(Long flowDeptId) {
        this.flowDeptId = flowDeptId;
    }
}
