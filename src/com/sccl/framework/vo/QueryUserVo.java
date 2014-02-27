/**
 * Copyright (c) 2013 AKSL
 * All right reserved.
 */
package com.sccl.framework.vo;

/**
 * 类描述
 * @author 张银祥
 * @create_time 2013-7-26 下午04:11:53
 * @project hr
 */
public class QueryUserVo {
  private String psnName;
  private String psnUser;
  private String psnLogin;
  private Integer psnUnit;
  
  public String getPsnName() {
    return psnName;
  }
  public void setPsnName(String psnName) {
    this.psnName = psnName;
  }
  public String getPsnUser() {
    return psnUser;
  }
  public void setPsnUser(String psnUser) {
    this.psnUser = psnUser;
  }
  public String getPsnLogin() {
    return psnLogin;
  }
  public void setPsnLogin(String psnLogin) {
    this.psnLogin = psnLogin;
  }
  public Integer getPsnUnit() {
    return psnUnit;
  }
  public void setPsnUnit(Integer psnUnit) {
    this.psnUnit = psnUnit;
  }
}
