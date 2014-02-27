/**
 * @Title: ReplyLevel.java
 * @Package com.sccl.serviceManager.bugManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-7-24 上午10:46:56
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.sccl.framework.entity.TableEntity;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.entity.ReplyLevel.java
 * @ClassName: ReplyLevel
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-24
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-24 上午10:46:56
 * @Version:v1.0
 */
@XmlRootElement
@Entity
@Table(name = "T_REPLY_LEVEL", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="finDReplyLevelById", query="SELECT d FROM ReplyLevel d where d.replyLevelId=:uid"),
    })
public class ReplyLevel extends TableEntity implements Serializable {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = -8590775081566229913L;

    /**
     * @Title:ReplyLevel
     * @Description:
     */
    public ReplyLevel() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 请求程度主键
     */
    private int replyLevelId;
    /**
     * 请求程度名称
     */
    private String levelNameString;
    /**
     * 相应时间秒
     */
    private long replyTimes;
    /**
     * 删除标志
     */
    private int DR;

    /**
     * @return the replyLevelId
     */
    @Id
    @GeneratedValue
    @Column(name = "REPLY_LEVEL_ID", nullable = false)
    public int getreplyLevelId() {
        return replyLevelId;
    }
    /**
     * @param replyLevelId the replyLevelId to set
     */
    public void setreplyLevelId(int replyLevelId) {
        this.replyLevelId = replyLevelId;
    }
    /**
     * @return the levelNameString
     */
    @Column(name = "LEVEL_NAME")
    public String getLevelNameString() {
        return levelNameString;
    }
    /**
     * @param levelNameString the levelNameString to set
     */
    public void setLevelNameString(String levelNameString) {
        this.levelNameString = levelNameString;
    }
    /**
     * @return the replyTimes
     */
    @Column(name = "REPLY_TIMES")
    public long getReplyTimes() {
        return replyTimes;
    }
    /**
     * @param replyTimes the replyTimes to set
     */
    public void setReplyTimes(long replyTimes) {
        this.replyTimes = replyTimes;
    }
    /**
     * @return the DR
     */
    @Column(name = "DR")
    public int getDR() {
        return DR;
    }
    /**
     * @param DR the DR to set
     */
    public void setDR(int DR) {
        this.DR = DR;
    }

}
