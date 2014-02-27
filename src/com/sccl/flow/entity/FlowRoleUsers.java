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
 * FlowRoleUsers entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_FLOWROLEUSERS", sequenceName = "SEQ_FLOW", allocationSize=1)
@Table(name="FLOWROLEUSERS",schema="ITSM")
public class FlowRoleUsers extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -5170664474936595937L;
	private Long RoleUserID;
     private String UserID;
     private String FlowRoleID;
     private String ManageDepts;
     private Integer ManageType;
     private boolean Effective;


    // Constructors

    /** default constructor */
    public FlowRoleUsers() {
    }

	/** minimal constructor */
    public FlowRoleUsers(Long RoleUserID) {
        this.RoleUserID = RoleUserID;
    }
    
    /** full constructor */
    public FlowRoleUsers(Long RoleUserID, String UserID, String FlowRoleID, String ManageDepts, Integer ManageType, boolean Effective) {
        this.RoleUserID = RoleUserID;
        this.UserID = UserID;
        this.FlowRoleID = FlowRoleID;
        this.ManageDepts = ManageDepts;
        this.ManageType = ManageType;
        this.Effective = Effective;
    }

   
    // Property accessors
    @Id 
    @GeneratedValue(generator = "GEN_FLOWROLEUSERS", strategy = GenerationType.SEQUENCE)
    @Column(name="ROLEUSERID", unique=true, nullable=false, precision=22, scale=0)
    public Long getRoleUserID() {
        return this.RoleUserID;
    }
    
    public void setRoleUserID(Long RoleUserID) {
        this.RoleUserID = RoleUserID;
    }

    @Column(name="USERID", length=40)
	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	@Column(name="FLOWROLEID", length=40)
	public String getFlowRoleID() {
		return FlowRoleID;
	}

	public void setFlowRoleID(String flowRoleID) {
		FlowRoleID = flowRoleID;
	}
    
    @Column(name="MANAGEDEPTS", length=4000)
    public String getManageDepts() {
        return this.ManageDepts;
    }
    
    public void setManageDepts(String ManageDepts) {
        this.ManageDepts = ManageDepts;
    }
    
    @Column(name="MANAGETYPE", precision=8, scale=0)
    public Integer getManageType() {
        return this.ManageType;
    }
    
    public void setManageType(Integer ManageType) {
        this.ManageType = ManageType;
    }
    
    @Column(name="EFFECTIVE", precision=2, scale=0)
    public boolean getEffective() {
        return this.Effective;
    }
    
    public void setEffective(boolean Effective) {
        this.Effective = Effective;
    }
}