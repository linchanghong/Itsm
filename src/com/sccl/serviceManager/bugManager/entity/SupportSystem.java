/**
 * 
 */
package com.sccl.serviceManager.bugManager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;

/**
 * 
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.entity.SupportSystem.java
 * @ClassName: SupportSystem
 * @Description: 支撑系统资源表
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-26
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-26 上午10:26:05
 * @Version:v1.0
 */
@XmlRootElement
@Entity
@Table(name = "T_SUPPORT_SYSTEM", schema = "ITSM")
public class SupportSystem extends TableEntity implements Serializable{
	
	
	/**
	 * 
	 */
    
    private static final long serialVersionUID = -6355328957360208988L;
	/**
	 * 支撑系统主键
	 */
    private int systemID = -1;
	/**
	 * 系统名称
	 */
    private String systemName;
	/**
	 * 系统编号
	 */
    private String systemCode;
	/**
	 * 系统上线时间
	 */
    private Date onlineDate;
	/**
	 * 备注
	 */
    private String remark;
	/**
	 * 删除标志
	 */
    private int DR;
	/**
	 * 系统业务模块集合
	 */
//    private List<SystemModule> sysModules;
	
	/**
	 * 
	 * @Title: getSystemID
	 * @Description:无参构造
	 * @return 
	 * @retunType:int      返回类型
	 * @throws:
	 */
	 @Id
	 @GeneratedValue
	 @Column(name = "SYSTEM_ID", nullable = false)
	public int getSystemID() {
		return systemID;
	}
	
	/**
     * @param systemID the systemID to set
     */
    public void setSystemID(int systemID) {
        this.systemID = systemID;
    }
	
    /**
     * @return the systemName
     */
	@Column(name="SYSTEM_NAME")
    public String getSystemName() {
        return systemName;
    }

    /**
     * @param systemName the systemName to set
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * @return the systemCode
     */
    @Column(name="SYSTEM_CODE")
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * @param systemCode the systemCode to set
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * @return the onlineDate
     */
    @Column(name="ONLINE_DATE")
    @Temporal(TemporalType.DATE)
    public Date getOnlineDate() {
        return onlineDate;
    }

    /**
     * @param onlineDate the onlineDate to set
     */
    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    /**
     * @return the remark
     */
    @Column(name="REMARK")
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the dR
     */
    @Column(name="DR")
    public int getDR() {
        return DR;
    }

    /**
     * @param dR the dR to set
     */
    public void setDR(int dR) {
        DR = dR;
    }

    /**
     * @return the sysModules
     */
//    @OneToMany(mappedBy="system")
//    public List<SystemModule> getSysModules() {
//        return sysModules;
//    }
//    /**
//     * @param sysModules the sysModules to set
//     */
//    public void setSysModules(List<SystemModule> sysModules) {
//        this.sysModules = sysModules;
//    }

}
