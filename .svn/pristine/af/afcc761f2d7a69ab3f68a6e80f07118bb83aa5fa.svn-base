/**
 * @Title: EntityID.java
 * @Package com.sccl.serviceManager.supportSystemManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-10-24 下午7:42:06
 * @version:V1.0 
 */
package com.sccl.serviceManager.supportSystemManager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.supportSystemManager.entity.EntityID.java
 * @ClassName: EntityID
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-10-24
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-10-24 下午7:42:06
 * @Version:v1.0
 */
@Embeddable
public class EntityID implements Serializable {


    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = -7209555862732404908L;
    private int sysID;
    private int userID;
    private int userStatus;
    
    /**
     * @Title:EntityID
     * @Description:
     */
    public EntityID() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the sysID
     */
    @Column(name="SYSTEM_ID")
    public int getSysID() {
        return sysID;
    }

    /**
     * @param sysID the sysID to set
     */
    public void setSysID(int sysID) {
        this.sysID = sysID;
    }

    /**
     * @return the userID
     */
    @Column(name="USER_ID")
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the userStatus
     */
    @Column(name="USER_STATUS")
    public int getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */
    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    /* 
     * <p>Title: hashCode</p>
     * <p>Description: </p>
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 22;
        
        result = 26 * result + this.getSysID();
        result = 26 * result + this.getUserID();
        result = 26 * result + this.getUserStatus();
        return result;
    }

    /* 
     * <p>Title: equals</p>
     * <p>Description: </p>
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ( (this == obj ) ) return true;
        if ( (obj == null ) ) return false;
        if ( !(obj instanceof EntityID) ) return false;
        EntityID castOther = ( EntityID ) obj; 
        
        return (this.getSysID() == castOther.getSysID() 
                && this.getUserID() == castOther.getUserID() 
                && this.getUserStatus() == castOther.getUserStatus());
    }
}
