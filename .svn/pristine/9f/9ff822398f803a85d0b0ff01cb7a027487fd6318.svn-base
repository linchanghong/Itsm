/**
 * 
 */
package com.sccl.serviceManager.bugManager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.sccl.framework.entity.TableEntity;

/**
 * @author Administrator
 * 支撑系统业务模块
 */
@XmlRootElement
@Entity
@Table(name = "T_SYSTEM_MODULE", schema = "ITSM")
public class SystemModule extends TableEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4800838864836007907L;
	/**
	 * 系统模块主键ID
	 */
	private int moduleID = -1;
	/**
	 * 支撑系统主键ID
	 */
	private int systemID;
	/**
	 * 业务模块名称
	 */
	private String moduleName;
	/**
	 * 业务模块编码
	 */
	private String moduleCode;
	/**
	 * 备注
	 */
	private String remark;
	/**
     * 删除标志
     */
	private int DR;
    
    @Id
    @GeneratedValue
    @Column(name = "MODULE_ID", nullable = false)
	public int getModuleID() {
		return moduleID;
	}
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}
	
	/**
     * @return the system
     */
    @Column(name = "SYSTEM_ID", nullable = false)
    public int getSystem() {
        return systemID;
    }
    /**
     * @param system the system to set
     */
    public void setSystem(int systemID) {
        this.systemID = systemID;
    }
    
    @Column(name="MODULE_NAME")
    public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	@Column(name="MODULE_CODE")
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="DR")
	public int getDR() {
		return DR;
	}
	public void setDR(int dR) {
		DR = dR;
	}

}
