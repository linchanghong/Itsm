package com.sccl.framework.service.roleRight;

import java.util.List;

import com.sccl.framework.entity.MenuButton;
import com.sccl.framework.entity.MsRole;

public interface IRoleRightService {
	
	public String addMenuButton(String menuJson);
	public String addMenuButtons(String menusJson);
	public String updateMenuButton(String menuJson);
	public String updateMenuButtons(String menusJson);
	public String findMenuById(String menuId) ;
	public String findMenuByRoleId(String roleId) ;
	public String findAllMenuButton();
	public String findAllMenuButtonTree(String rights);
	public List<MenuButton> findAllMenu();
	public String deleteMenuById(String menuId);
	public String deleteMenuByIds(String menuIds);
	
	public String addRole(String roleJson);
	public String addRoles(String rolesJson);
	public String updateRole(String roleJson);
	public String updateRoles(String rolesJson);
	public String deleteRoleById(String roleId);
	public String deleteRoleByIds(String roleIds);
	public MsRole findByRoleName(String roleName);
	public List<MsRole> findAllRole();
	public String findAllRoles() ;
	public String findAllRoleTree(int roleId);

	public String login(String loginJson);

}
