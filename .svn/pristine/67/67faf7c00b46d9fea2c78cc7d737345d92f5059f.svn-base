package com.sccl.framework.service.login;

import java.util.HashMap;

import com.sccl.framework.entity.MsUser;

public interface IUserLoginServive {
	/**
	 * 用户登录,验证用户登录,验证成功返回true,失败返回false;
	 * @param us
	 * @return
	 */
	public HashMap<String, Object> userLogin(MsUser us);
	/**
	 * 根据code查询用户信息
	 * @param logcode
	 * @return
	 */
	public MsUser finduser(String userCode);
	
	public String autoLogin(String ltpaToken, String ltpa3DESKey, String ltpaPassword);
	
	public MsUser isLogin(String userCode, String userBehave, String parameters);
	

}
