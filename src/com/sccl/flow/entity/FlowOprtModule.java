package com.sccl.flow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sccl.framework.entity.TableEntity;


/**
 * FlowOprtModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="FLOWOPRTMODULE",schema="ITSM")
public class FlowOprtModule extends TableEntity implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -2296892645172211018L;
	private Long FlowOprtModuleID;
     private Long FlowOprtID;
     private String FlowOprtModuleName;
     private String FlowOprtModuleCode;
     private String Location;
     private boolean IsEditModule;
     private Long SystemId;
     private String FlowOprtModulePath;
     private String CpsFlowOprtModuleId;
     private String FlowOprtModuleURL;
     private String ZhuanBan;
     private Integer IsReason;
     private Integer Btntan;


    // Constructors

    /** default constructor */
    public FlowOprtModule() {
    }

	/** minimal constructor */
    public FlowOprtModule(Long FlowOprtModuleID) {
        this.FlowOprtModuleID = FlowOprtModuleID;
    }
    
    /** full constructor */
    public FlowOprtModule(Long FlowOprtModuleID, String FlowOprtModuleName, String FlowOprtModuleCode, Long FlowOprtID, String Location, boolean IsEditModule, Long SystemId, String FlowOprtModulePath, String CpsFlowOprtModuleId, String FlowOprtModuleURL, String ZhuanBan, Integer IsReason, Integer Btntan) {
        this.FlowOprtModuleID = FlowOprtModuleID;
        this.FlowOprtID = FlowOprtID;
        this.FlowOprtModuleName = FlowOprtModuleName;
        this.FlowOprtModuleCode = FlowOprtModuleCode;
        this.Location = Location;
        this.IsEditModule = IsEditModule;
        this.SystemId = SystemId;
        this.FlowOprtModulePath = FlowOprtModulePath;
        this.CpsFlowOprtModuleId = CpsFlowOprtModuleId;
        this.FlowOprtModuleURL = FlowOprtModuleURL;
        this.ZhuanBan = ZhuanBan;
        this.IsReason = IsReason;
        this.Btntan = Btntan;
    }

   
    // Property accessors
    @Id 
    @Column(name="FLOWOPRTMODULEID", unique=true, nullable=false, precision=22, scale=0)
    public Long getFlowOprtModuleID() {
        return this.FlowOprtModuleID;
    }
    
    public void setFlowOprtModuleID(Long FlowOprtModuleID) {
        this.FlowOprtModuleID = FlowOprtModuleID;
    }
    
    @Column(name="FLOWOPRTMODULENAME", length=50)
    public String getFlowOprtModuleName() {
        return this.FlowOprtModuleName;
    }
    
    public void setFlowOprtModuleName(String FlowOprtModuleName) {
        this.FlowOprtModuleName = FlowOprtModuleName;
    }
    
    @Column(name="FLOWOPRTMODULECODE", length=50)
    public String getFlowOprtModuleCode() {
        return this.FlowOprtModuleCode;
    }
    
    public void setFlowOprtModuleCode(String FlowOprtModuleCode) {
        this.FlowOprtModuleCode = FlowOprtModuleCode;
    }

    @Column(name="FLOWOPRTID", length=40)
	public Long getFlowOprtID() {
		return FlowOprtID;
	}

	public void setFlowOprtID(Long FlowOprtID) {
		this.FlowOprtID = FlowOprtID;
	}
    
    @Column(name="LOCATION", length=50)
    public String getLocation() {
        return this.Location;
    }
    
    public void setLocation(String Location) {
        this.Location = Location;
    }
    
    @Column(name="ISEDITMODULE", precision=2, scale=0)
    public boolean getIsEditModule() {
        return this.IsEditModule;
    }
    
    public void setIsEditModule(boolean IsEditModule) {
        this.IsEditModule = IsEditModule;
    }
    
    @Column(name="SYSTEMID", precision=22, scale=0)
    public Long getSystemId() {
        return this.SystemId;
    }
    
    public void setSystemId(Long SystemId) {
        this.SystemId = SystemId;
    }
    
    @Column(name="FLOWOPRTMODULEPATH", length=500)
    public String getFlowOprtModulePath() {
        return this.FlowOprtModulePath;
    }
    
    public void setFlowOprtModulePath(String FlowOprtModulePath) {
        this.FlowOprtModulePath = FlowOprtModulePath;
    }
    
    @Column(name="CPSFLOWOPRTMODULEID", length=200)
    public String getCpsFlowOprtModuleId() {
        return this.CpsFlowOprtModuleId;
    }
    
    public void setCpsFlowOprtModuleId(String CpsFlowOprtModuleId) {
        this.CpsFlowOprtModuleId = CpsFlowOprtModuleId;
    }
    
    @Column(name="FLOWOPRTMODULEURL", length=500)
    public String getFlowOprtModuleURL() {
        return this.FlowOprtModuleURL;
    }
    
    public void setFlowOprtModuleURL(String FlowOprtModuleURL) {
        this.FlowOprtModuleURL = FlowOprtModuleURL;
    }
    
    @Column(name="ZHUANBAN", length=50)
    public String getZhuanBan() {
        return this.ZhuanBan;
    }
    
    public void setZhuanBan(String ZhuanBan) {
        this.ZhuanBan = ZhuanBan;
    }
    
    @Column(name="ISREASON", precision=8, scale=0)
    public Integer getIsReason() {
        return this.IsReason;
    }
    
    public void setIsReason(Integer IsReason) {
        this.IsReason = IsReason;
    }
    
    @Column(name="BTNTAG", precision=8, scale=0)
    public Integer getBtntan() {
        return this.Btntan;
    }
    
    public void setBtntan(Integer Btntan) {
        this.Btntan = Btntan;
    }
}