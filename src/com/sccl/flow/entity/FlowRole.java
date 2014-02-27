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
 * FlowRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_FLOWROLE", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="FLOWROLE",schema="ITSM")
public class FlowRole extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 7632234683859192472L;
	private Long FlowRoleID;
     private String CompId;
     private String FlowRoleName;
     private boolean Effective;


    // Constructors

    /** default constructor */
    public FlowRole() {
    }

	/** minimal constructor */
    public FlowRole(Long FlowRoleID) {
        this.FlowRoleID = FlowRoleID;
    }
    
    /** full constructor */
    public FlowRole(Long FlowRoleID, String FlowRoleName, boolean Effective, String CompId) {
        this.FlowRoleID = FlowRoleID;
        this.FlowRoleName = FlowRoleName;
        this.Effective = Effective;
        this.CompId = CompId;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(generator = "GEN_FLOWROLE", strategy = GenerationType.SEQUENCE)
    @Column(name="FLOWROLEID", unique=true, nullable=false, precision=22, scale=0)
    public Long getFlowRoleID() {
        return this.FlowRoleID;
    }
    
    public void setFlowRoleID(Long FlowRoleID) {
        this.FlowRoleID = FlowRoleID;
    }
    
    @Column(name="FLOWROLENAME", length=200)

    public String getFlowRoleName() {
        return this.FlowRoleName;
    }
    
    public void setFlowRoleName(String FlowRoleName) {
        this.FlowRoleName = FlowRoleName;
    }
    
    @Column(name="EFFECTIVE", precision=2, scale=0)
    public boolean getEffective() {
        return this.Effective;
    }
    
    public void setEffective(boolean Effective) {
        this.Effective = Effective;
    }
    
    @Column(name = "COMPID", precision = 40)
	public String getCompId() {
		return CompId;
	}

	public void setCompId(String compId) {
		CompId = compId;
	}
}