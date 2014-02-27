/**
 * @Title: SystemManagerEntity.java
 * @Package com.sccl.serviceManager.supportSystemManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-10-24 下午4:22:16
 * @version:V1.0 
 */
package com.sccl.serviceManager.supportSystemManager.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.TableEntity;
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.User;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity.java
 * @ClassName: SystemManagerEntity
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-10-24
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-10-24 下午4:22:16
 * @Version:v1.0
 */
@Entity
@XmlRootElement
@Table(name = "T_SYSTEM_PERSON", schema = "ITSM")
public class SystemManagerEntity extends TableEntity implements Serializable {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = 6517480187803747991L;
    
    
    private EntityID eid;
    /**
     * 用户所在系统
     */
    private SupportSystem system;
    /**
     * 用户
     */
    private User user;
    /**
     * 用户在系统中对应身份
     */
    private ConstDetail userStatus;

    
    /**
     * @Title:SystemManagerEntity
     * @Description:
     */
    public SystemManagerEntity() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the eid
     */
    @EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride(name="sysID", column=@Column(name="SYSTEM_ID", nullable=false) ), 
        @AttributeOverride(name="userID", column=@Column(name="USER_ID", nullable=false) ),
        @AttributeOverride(name="userStatus", column=@Column(name="USER_STATUS", nullable=false) )} )
    public EntityID getEid() {
        return eid;
    }

    /**
     * @param eid the eid to set
     */
    public void setEid(EntityID eid) {
        this.eid = eid;
    }

    /**
     * @return the system
     */
    @ManyToOne
    @JoinColumn(name="SYSTEM_ID",insertable=false, updatable=false)
    public SupportSystem getSystem() {
        return system;
    }

    /**
     * @param system the system to set
     */
    public void setSystem(SupportSystem system) {
        this.system = system;
    }

    /**
     * @return the user
     */
    @ManyToOne
    @JoinColumn(name="USER_ID",insertable=false, updatable=false)
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the userStatus
     */
    @ManyToOne
    @JoinColumn(name="USER_STATUS",insertable=false, updatable=false)
    public ConstDetail getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(ConstDetail userStatus) {
        this.userStatus = userStatus;
    }
}
