package com.sccl.serviceManager.common.selectWindow.user.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccl.framework.entity.MsUser;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.utils.Tools;
import com.sccl.serviceManager.common.selectWindow.user.service.ISelectUserService;

@SuppressWarnings("all")
@Component("selectUserAPI")
@RemotingDestination
public class SelectUserAPI {
	
	private ISelectUserService selectUserService;
	
	
	/**
     * @return the selectUserService
     */
    public ISelectUserService getSelectUserService() {
        return selectUserService;
    }
    /**
     * @param selectUserService the selectUserService to set
     */
    @Resource(name = "selectUserService")
    public void setSelectUserService(ISelectUserService selectUserService) {
        this.selectUserService = selectUserService;
    }
	
    /**
     * 
     * @Title: findUsers
     * @Description:根据部门Id查询用户
     * @param depid
     * @param first
     * @param size
     * @param isCount
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
	public String findUsers(String depid, int first, int size, boolean isCount){
		Gson gson = new Gson();//new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<Object> list = selectUserService.findUsers(depid, first, size, isCount);
		String json = gson.toJson(list);
		return json;
	}
	
	/**
	 * 
	 * @Title: findUsersWithoutID
	 * @Description:根据部门Id查询用户,且不包含某一用户
	 * @param depid
	 * @param userID
	 * @param first
	 * @param size
	 * @param isCount
	 * @return 
	 * @retunType:String      返回类型
	 * @throws:
	 */
    public String findUsersWithoutID(String depid,int userID, int first, int size, boolean isCount){
        Gson gson = new Gson();//new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<Object> list = selectUserService.findUsersWithoutID(depid, userID, first, size, isCount);
        String json = gson.toJson(list);
        return json;
    }
	
	/**
	 * 根据用户输入模糊查询
	 * @param where
	 * @return
	 */
	public String queryUsers(int companyid, String userName, int first, int size, boolean isCount){
		Gson gson = new Gson();
		List<Object> list = selectUserService.queryUsersByNameLike(companyid, userName, first, size, isCount);
		String json = gson.toJson(list);
		return json;
	}
	
	/**
     * @Title: queryUsersByNameLike4SysOrUserStatus
     * @Description:不同系统，不同身份,根据用户输入名字模糊查询人员
     * @param userID 排除人员
     * @param sysID 系统
     * @param userStatus 身份
     * @param userName 查询名字
     * @param first 第几页
     * @param size 每页显示多少行
     * @param isCount 是否分页
     * @return List<Object>        返回类型
     * @throws 
     */
    public String queryUsers4SysOrUserStatus(
            int userID, int sysID, int userStatus, String userName, 
            int first, int size, boolean isCount){
        Gson gson = new Gson();
        List<Object> list = 
                selectUserService.queryUsersByNameLike4SysOrUserStatus(
                        userID, sysID, userStatus, userName, first, size, isCount);
        String json = gson.toJson(list);
        return json;
    }
	
	/**
	 * 
	 * @Title: findUsersWithoutID4SysOrUserStatus
	 * @Description:不同系统，不同身份的人员
	 * @param depid 部门
	 * @param userID 排除人员
	 * @param sysID 系统
	 * @param userStatus 身份
	 * @param first 第几页
	 * @param size 每页显示多少行
	 * @param isCount 是否分页
	 * @return String        返回类型
	 * @throws
	 */
	public String findUsersWithoutID4SysOrUserStatus(
	        String depid,int userID, int sysID, int userStatus, 
	        int first, int size, boolean isCount){
	    Gson gson = new Gson();
        List<Object> list = 
                selectUserService.findUsersWithoutID4SysOrUserStatus(
                        depid, userID, sysID, userStatus, first, size, isCount);
        String json = gson.toJson(list);
        return json;
	}
}
