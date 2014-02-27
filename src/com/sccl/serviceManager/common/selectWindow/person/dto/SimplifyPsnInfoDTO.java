/**
 * Copyright (c) 2011 Noah
 * All right reserved.
 */
package com.sccl.serviceManager.common.selectWindow.person.dto;

/**
 * 类描述
 * @author 张银祥
 * @create_time 2013-7-1 下午05:58:05
 * @project hr
 */
public class SimplifyPsnInfoDTO {

  private Integer personId;
  private String personName;
  private String personCode;
  private Integer orgId;
  private String orgName;
  private Integer deptId;
  private String deptName;
  private Integer jobLevel;
  private String jobLevelName;
  private Integer jobType;
  private String jobTypeName;
  
  
  public SimplifyPsnInfoDTO(){
    
  }
  public SimplifyPsnInfoDTO(String personCode,String deptName,String personName,String jobTypeName,
      Integer personId,Integer orgId,Integer deptId,String orgName){
  this.personCode = personCode;
  this.deptName = deptName;
  this.personName = personName;
  this.jobTypeName = jobTypeName;
  this.personId = personId;
  this.orgId = orgId;
  this.deptId = deptId;
  this.orgName = orgName;
 }
  public Integer getPersonId() {
    return personId;
  }
  public void setPersonId(Integer personId) {
    this.personId = personId;
  }
  public String getPersonName() {
    return personName;
  }
  public void setPersonName(String personName) {
    this.personName = personName;
  }
  public String getPersonCode() {
    return personCode;
  }
  public void setPersonCode(String personCode) {
    this.personCode = personCode;
  }
  public Integer getOrgId() {
    return orgId;
  }
  public void setOrgId(Integer orgId) {
    this.orgId = orgId;
  }
  public String getOrgName() {
    return orgName;
  }
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  public Integer getDeptId() {
    return deptId;
  }
  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }
  public String getDeptName() {
    return deptName;
  }
  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }
  public Integer getJobLevel() {
    return jobLevel;
  }
  public void setJobLevel(Integer jobLevel) {
    this.jobLevel = jobLevel;
  }
  public String getJobLevelName() {
    return jobLevelName;
  }
  public void setJobLevelName(String jobLevelName) {
    this.jobLevelName = jobLevelName;
  }
  public Integer getJobType() {
    return jobType;
  }
  public void setJobType(Integer jobType) {
    this.jobType = jobType;
  }
  public String getJobTypeName() {
    return jobTypeName;
  }
  public void setJobTypeName(String jobTypeName) {
    this.jobTypeName = jobTypeName;
  }
}
