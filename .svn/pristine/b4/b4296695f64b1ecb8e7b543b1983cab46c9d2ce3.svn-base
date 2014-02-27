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
 * FlowModel entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_FLOWMODEL", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="FLOWMODEL",schema="ITSM")
public class FlowModel extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 6067623800179731139L;
	private Long FlowID;
     private String FlowName;
     private boolean IsOneByOne;
     private Long OprtTypeID;
     private boolean ISActive;
     private String CompId;
     private boolean IsSubmitModify;
     private boolean IsSameRoleUserPass;


    // Constructors

    /** default constructor */
    public FlowModel() {
    }

	/** minimal constructor */
    public FlowModel(Long FlowID, String FlowName) {
        this.FlowID = FlowID;
        this.FlowName = FlowName;
    }
    
    /** full constructor */
    public FlowModel(Long FlowID, String FlowName, boolean IsOneByOne, Long OprtTypeID, boolean ISActive, String CompId, boolean IsSubmitModify, boolean IsSameRoleUserPass) {
        this.FlowID = FlowID;
        this.FlowName = FlowName;
        this.IsOneByOne = IsOneByOne;
        this.OprtTypeID = OprtTypeID;
        this.ISActive = ISActive;
        this.CompId = CompId;
        this.IsSubmitModify = IsSubmitModify;
        this.IsSameRoleUserPass = IsSameRoleUserPass;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(generator = "GEN_FLOWMODEL", strategy = GenerationType.SEQUENCE)
    @Column(name="FLOWID", unique=true, nullable=false, precision=22, scale=0)
    public Long getFlowID() {
        return this.FlowID;
    }
    
    public void setFlowID(Long FlowID) {
        this.FlowID = FlowID;
    }
    
    @Column(name="FLOWNAME", nullable=false, length=200)
    public String getFlowName() {
        return this.FlowName;
    }
    
    public void setFlowName(String FlowName) {
        this.FlowName = FlowName;
    }
    
    @Column(name="ISONEBYONE", precision=2, scale=0)
    public boolean getIsOneByOne() {
        return this.IsOneByOne;
    }
    
    public void setIsOneByOne(boolean IsOneByOne) {
        this.IsOneByOne = IsOneByOne;
    }

    @Column(name="OPRTTYPEID", length=40)
	public Long getOprtTypeID() {
		return OprtTypeID;
	}

	public void setOprtTypeID(Long oprtTypeID) {
		OprtTypeID = oprtTypeID;
	}
    
    @Column(name="ISACTIVE", precision=2, scale=0)
    public boolean getISActive() {
        return this.ISActive;
    }
    
    public void setISActive(boolean ISActive) {
        this.ISActive = ISActive;
    }

    @Column(name = "COMPID", precision = 40)
	public String getCompId() {
		return CompId;
	}

	public void setCompId(String compId) {
		CompId = compId;
	}
    
    @Column(name="ISSUBMITMODIFY", precision=2, scale=0)

    public boolean getIsSubmitModify() {
        return this.IsSubmitModify;
    }
    
    public void setIsSubmitModify(boolean IsSubmitModify) {
        this.IsSubmitModify = IsSubmitModify;
    }
    
    @Column(name="ISSAMEROLEUSERPASS", precision=2, scale=0)

    public boolean getIsSameRoleUserPass() {
        return this.IsSameRoleUserPass;
    }
    
    public void setIsSameRoleUserPass(boolean IsSameRoleUserPass) {
        this.IsSameRoleUserPass = IsSameRoleUserPass;
    }
}