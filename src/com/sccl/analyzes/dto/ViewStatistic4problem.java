/**
 * @Title: ViewStatistic4problem.java
 * @Package com.sccl.analyzes.dto
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-15 下午3:19:03
 * @version:V1.0 
 */
package com.sccl.analyzes.dto;

import com.sccl.framework.entity.QueryEntity;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.analyzes.dto.ViewStatistic4problem.java
 * @ClassName: ViewStatistic4problem
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-15
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-15 下午3:19:03
 * @Version:v1.0
 */
public class ViewStatistic4problem extends QueryEntity {

    /**
     * 系统id
     */
    private Long sysId;
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 问题提交数量
     */
    private Long submitCount = 0l;
    /**
     * 问题受理数量
     */
    private Long acceptCount = 0l;
    /**
     * 问题解决数量
     */
    private Long completedCount = 0l;
    
    /**
     * @Title:ViewStatistic4problem
     * @Description:
     */
    public ViewStatistic4problem() {
        super();
    }
    
    /**
     * @return the sysId
     */
    public Long getSysId() {
        return sysId;
    }
    /**
     * @param sysId the sysId to set
     */
    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }
    /**
     * @return the sysName
     */
    public String getSysName() {
        return sysName;
    }
    /**
     * @param sysName the sysName to set
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    /**
     * @return the submitCount
     */
    public Long getSubmitCount() {
        return submitCount;
    }
    /**
     * @param submitCount the submitCount to set
     */
    public void setSubmitCount(Long submitCount) {
        this.submitCount = submitCount;
    }
    /**
     * @return the acceptCount
     */
    public Long getAcceptCount() {
        return acceptCount;
    }
    /**
     * @param acceptCount the acceptCount to set
     */
    public void setAcceptCount(Long acceptCount) {
        this.acceptCount = acceptCount;
    }
    /**
     * @return the completedCount
     */
    public Long getCompletedCount() {
        return completedCount;
    }
    /**
     * @param completedCount the completedCount to set
     */
    public void setCompletedCount(Long completedCount) {
        this.completedCount = completedCount;
    }
    
}
