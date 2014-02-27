package com.sccl.framework.service.user;

public interface IUserService {
	
	public String addUser(String userJson);
	public String addUsers(String usersJson);
	public String updateUser(String userJson);
	public String updateUsers(String usersJson);
	public String deleteUserById(String userId);
	public String deleteUserByIds(String userIds);
	public String findAllUser(String userInfoCondtion, int first,
      int size, boolean isCount);
	public String findUserById(String userId);
	public String findUserByName(String userName);
	public String findUserByOrgId(String orgId);

	public String findAllUserByOrgIdPage(String orgId, int first, int size, boolean isCount);
	
	public void changPassWord(Integer id, String password);
	public String findAllUser(int first, int size, boolean isCount);
}
