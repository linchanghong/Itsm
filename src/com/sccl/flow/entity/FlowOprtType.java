package com.sccl.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sccl.framework.entity.TableEntity;


/**
 * FlowOprtType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="FLOWOPRTTYPE",schema="ITSM")
public class FlowOprtType extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 2810498319180913386L;
	private Long OprtTypeID;
     private String OprtTypeName;
     private Integer OprtTypeOrder;
     private boolean Effective;
     private String Type;
     private Long TFOprtTypeID;
     private String ProjectManagerSQL;
     private boolean ContainsEditModule;
     private Long SysemId;
     private Integer CompFlag;


    // Constructors

    /** default constructor */
    public FlowOprtType() {
    }

	/** minimal constructor */
    public FlowOprtType(Long OprtTypeID) {
        this.OprtTypeID = OprtTypeID;
    }
    
    /** full constructor */
    public FlowOprtType(Long OprtTypeID, String OprtTypeName, Integer OprtTypeOrder, boolean Effective, String Type, Long TFOprtTypeID, String ProjectManagerSQL, boolean ContainsEditModule, Long SysemId, Integer CompFlag) {
        this.OprtTypeID = OprtTypeID;
        this.OprtTypeName = OprtTypeName;
        this.OprtTypeOrder = OprtTypeOrder;
        this.Effective = Effective;
        this.Type = Type;
        this.TFOprtTypeID = TFOprtTypeID;
        this.ProjectManagerSQL = ProjectManagerSQL;
        this.ContainsEditModule = ContainsEditModule;
        this.SysemId = SysemId;
        this.CompFlag = CompFlag;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="OPRTTYPEID", unique=true, nullable=false, precision=22, scale=0)

    public Long getOprtTypeID() {
        return this.OprtTypeID;
    }
    
    public void setOprtTypeID(Long OprtTypeID) {
        this.OprtTypeID = OprtTypeID;
    }
    
    @Column(name="OPRTTYPENAME", length=200)

    public String getOprtTypeName() {
        return this.OprtTypeName;
    }
    
    public void setOprtTypeName(String OprtTypeName) {
        this.OprtTypeName = OprtTypeName;
    }
    
    @Column(name="OPRTTYPEORDER", precision=8, scale=0)

    public Integer getOprtTypeOrder() {
        return this.OprtTypeOrder;
    }
    
    public void setOprtTypeOrder(Integer OprtTypeOrder) {
        this.OprtTypeOrder = OprtTypeOrder;
    }
    
    @Column(name="EFFECTIVE", precision=2, scale=0)

    public boolean getEffective() {
        return this.Effective;
    }
    
    public void setEffective(boolean Effective) {
        this.Effective = Effective;
    }
    
    @Column(name="TYPE", length=50)

    public String getType() {
        return this.Type;
    }
    
    public void setType(String Type) {
        this.Type = Type;
    }
    
    @Column(name="TFOPRTTYPEID", precision=22, scale=0)

    public Long getTFOprtTypeID() {
        return this.TFOprtTypeID;
    }
    
    public void setTFOprtTypeID(Long TFOprtTypeID) {
        this.TFOprtTypeID = TFOprtTypeID;
    }
    
    @Column(name="PROJECTMANAGERSQL", length=1000)

    public String getProjectManagerSQL() {
        return this.ProjectManagerSQL;
    }
    
    public void setProjectManagerSQL(String ProjectManagerSQL) {
        this.ProjectManagerSQL = ProjectManagerSQL;
    }
    
    @Column(name="CONTAINSEDITMODULE", precision=2, scale=0)

    public boolean getContainsEditModule() {
        return this.ContainsEditModule;
    }
    
    public void setContainsEditModule(boolean ContainsEditModule) {
        this.ContainsEditModule = ContainsEditModule;
    }
    
    @Column(name="SYSTEMID", precision=22, scale=0)

    public Long getSysemId() {
        return this.SysemId;
    }
    
    public void setSysemId(Long SysemId) {
        this.SysemId = SysemId;
    }
    
    @Column(name="COMPFLAG", precision=8, scale=0)

    public Integer getCompFlag() {
        return this.CompFlag;
    }
    
    public void setCompFlag(Integer CompFlag) {
        this.CompFlag = CompFlag;
    }
}