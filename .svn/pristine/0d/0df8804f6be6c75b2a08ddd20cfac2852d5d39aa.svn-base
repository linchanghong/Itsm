package com.sccl.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sccl.framework.entity.TableEntity;


/**
 * FlowNodeModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_FLOWNODEMODEL", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="FLOWNODEMODEL",schema="ITSM")
public class FlowNodeModel extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -7555487101248278589L;
	private Long NodeID;
     private String NodeName;
     private Integer NodeAttr;
     private String NodeNumber;
     private String FlowID;
     private String ModuleCode;
     private String NodeMark;
     private Double PointX;
     private Double PointY;
     private String NextID;
     private Long PreviewID;
     private String ApposeNodeIDs;
     private Integer DealWithDay;
     private boolean IsNodeOneself;
     private boolean IsNeedCheckDate;


    // Constructors

    /** default constructor */
    public FlowNodeModel() {
    }

	/** minimal constructor */
    public FlowNodeModel(Long NodeID) {
        this.NodeID = NodeID;
    }
    
    /** full constructor */
    public FlowNodeModel(Long NodeID, String NodeName, Integer NodeAttr, String NodeNumber, String FlowID, String ModuleCode, String NodeMark, Double PointX, Double PointY, String NextID, Long PreviewID, String ApposeNodeIDs, Integer DealWithDay, boolean IsNodeOneself, boolean IsNeedCheckDate) {
        this.NodeID = NodeID;
        this.NodeName = NodeName;
        this.NodeAttr = NodeAttr;
        this.NodeNumber = NodeNumber;
        this.FlowID = FlowID;
        this.ModuleCode = ModuleCode;
        this.NodeMark = NodeMark;
        this.PointX = PointX;
        this.PointY = PointY;
        this.NextID = NextID;
        this.PreviewID = PreviewID;
        this.ApposeNodeIDs = ApposeNodeIDs;
        this.DealWithDay = DealWithDay;
        this.IsNodeOneself = IsNodeOneself;
        this.IsNeedCheckDate = IsNeedCheckDate;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(generator = "GEN_FLOWNODEMODEL", strategy = GenerationType.SEQUENCE)
    @Column(name="NODEID", unique=true, nullable=false, precision=22, scale=0)
    public Long getNodeID() {
        return this.NodeID;
    }
    
    public void setNodeID(Long NodeID) {
        this.NodeID = NodeID;
    }
    
    @Column(name="NODENAME", length=200)
    public String getNodeName() {
        return this.NodeName;
    }
    
    public void setNodeName(String NodeName) {
        this.NodeName = NodeName;
    }
    
    @Column(name="NODEATTR", precision=8, scale=0)
    public Integer getNodeAttr() {
        return this.NodeAttr;
    }
    
    public void setNodeAttr(Integer NodeAttr) {
        this.NodeAttr = NodeAttr;
    }
    
    @Column(name="NODENUMBER", length=200)
    public String getNodeNumber() {
        return this.NodeNumber;
    }
    
    public void setNodeNumber(String NodeNumber) {
        this.NodeNumber = NodeNumber;
    }
    
    @Column(name="FLOWID", length=40)
	public String getFlowID() {
		return FlowID;
	}

	public void setFlowID(String flowID) {
		FlowID = flowID;
	}
	
    @Column(name="MODULECODE", length=50)
    public String getModuleCode() {
        return this.ModuleCode;
    }
    
    public void setModuleCode(String ModuleCode) {
        this.ModuleCode = ModuleCode;
    }
    
    @Column(name="NODEMARK", length=4000)

    public String getNodeMark() {
        return this.NodeMark;
    }
    
    public void setNodeMark(String NodeMark) {
        this.NodeMark = NodeMark;
    }
    
    @Column(name="POINTX", precision=22)

    public Double getPointX() {
        return this.PointX;
    }
    
    public void setPointX(Double PointX) {
        this.PointX = PointX;
    }
    
    @Column(name="POINTY", precision=22)

    public Double getPointY() {
        return this.PointY;
    }
    
    public void setPointY(Double PointY) {
        this.PointY = PointY;
    }
    
    @Column(name="NEXTID", length=50)

    public String getNextID() {
        return this.NextID;
    }
    
    public void setNextID(String NextID) {
        this.NextID = NextID;
    }
    
    @Column(name="PREVIEWID", precision=22, scale=0)

    public Long getPreviewID() {
        return this.PreviewID;
    }
    
    public void setPreviewID(Long PreviewID) {
        this.PreviewID = PreviewID;
    }
    
    @Column(name="APPOSENODEIDS", length=50)

    public String getApposeNodeIDs() {
        return this.ApposeNodeIDs;
    }
    
    public void setApposeNodeIDs(String ApposeNodeIDs) {
        this.ApposeNodeIDs = ApposeNodeIDs;
    }
    
    @Column(name="DEALWITHDAY", precision=8, scale=0)

    public Integer getDealWithDay() {
        return this.DealWithDay;
    }
    
    public void setDealWithDay(Integer DealWithDay) {
        this.DealWithDay = DealWithDay;
    }
    
    @Column(name="ISNODEONESELF", precision=2, scale=0)

    public boolean getIsNodeOneself() {
        return this.IsNodeOneself;
    }
    
    public void setIsNodeOneself(boolean IsNodeOneself) {
        this.IsNodeOneself = IsNodeOneself;
    }
    
    @Column(name="ISNEEDCHECKDATE", precision=2, scale=0)

    public boolean getIsNeedCheckDate() {
        return this.IsNeedCheckDate;
    }
    
    public void setIsNeedCheckDate(boolean IsNeedCheckDate) {
        this.IsNeedCheckDate = IsNeedCheckDate;
    }
}