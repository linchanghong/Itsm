package com.sccl.serviceManager.common.selectWindow.user.service;

import java.util.List;

public interface ISelectUserService {
	
    /**
     * 
     * @Title: queryUsersByNameLike
     * @Description:根据用户输入模糊查询
     * @param companyid
     * @param userName
     * @param first
     * @param size
     * @param isCount
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    public List<Object> queryUsersByNameLike(int companyid, String userName, int first, int size,
            boolean isCount);
    
    /**
     * 
     * @Title: findUsers
     * @Description:根据部门Id查询用户
     * @param depid
     * @param first
     * @param size
     * @param isCount
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    public List<Object> findUsers(String depid, int first, int size,
            boolean isCount);
    
    /**
     * 
     * @Title: findUsers
     * @Description:根据部门Id查询用户,且不包含某一用户
     * @param depid
     * @param userID
     * @param first
     * @param size
     * @param isCount
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    public List<Object> findUsersWithoutID(String depid, int userID, int first, int size,
            boolean isCount);

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
     * @return List<Object>        返回类型
     * @throws
     */
    public List<Object> findUsersWithoutID4SysOrUserStatus(String depid,
            int userID, int sysID, int userStatus, int first, int size,
            boolean isCount);

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
    public List<Object> queryUsersByNameLike4SysOrUserStatus(int userID,
            int sysID, int userStatus, String userName, int first, int size,
            boolean isCount);
    
}
