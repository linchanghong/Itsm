package com.sccl.framework.service.roleRight;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.common.utils.Uid;
import com.sccl.framework.entity.MenuButton;
import com.sccl.framework.entity.MsRole;
import com.sccl.framework.entity.MsUser;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.vo.MenuButtonTree;
import com.sccl.framework.vo.MsRoleTree;

@Component("roleRightService")
public class RoleRightService implements IRoleRightService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Log
	@Transactional
	public String addMenuButton(String menuJson) {
		try {
			Gson gson = new Gson(); 
			MenuButton menuButton = dataManager.add(gson.fromJson(menuJson, MenuButton.class));
			return gson.toJson(menuButton);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String addMenuButtons(String menusJson) {
		try {
			Type menuType = new TypeToken<List<MenuButton>>(){}.getType();
			List<MenuButton> menuButtons =  new Gson().fromJson(menusJson, menuType);
			Iterator<MenuButton> iterator = menuButtons.iterator();
			while (iterator.hasNext()) {
				MenuButton menuButton = iterator.next();
				dataManager.add(menuButton);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateMenuButton(String menuJson) {
		try {
			dataManager.update(new Gson().fromJson(menuJson, MenuButton.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateMenuButtons(String menusJson) {
		try {
			Type menuType = new TypeToken<List<MenuButton>>(){}.getType();
			List<MenuButton> menuButtons =  new Gson().fromJson(menusJson, menuType);
			Iterator<MenuButton> iterator = menuButtons.iterator();
			while (iterator.hasNext()) {
				MenuButton menuButton = iterator.next();
				dataManager.update(menuButton);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String deleteMenuById(String menuId) {
		try {
			dataManager.deleteById(MenuButton.class, Integer.parseInt(menuId));
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	@Log
	@Transactional
	public String deleteMenuByIds(String menuIds) {
		try {
			Integer[] ids = new Gson().fromJson(menuIds, Integer[].class);
			
			dataManager.deleteByIdBatch(MenuButton.class, ids);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}
	
	@Log
	public String findMenuById(String menuId) {
		try {
			MenuButton menuButton = dataManager.findById(MenuButton.class, Integer.parseInt(menuId));
			return menuButton.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public String findMenuByRoleId(String roleId) {
		try {
			List<MenuButton> menuButtons = dataManager.createNamedQuery("findMenuByRoleId", MenuButton.class).setParameter("roleId", roleId).getResultList();
			return new Gson().toJson(menuButtons);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public String findAllMenuButton() {
		try {
			return new Gson().toJson(findAllMenu());
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public List<MenuButton> findAllMenu() {
		return dataManager.createNamedQuery("MenuButton.findOrdered", MenuButton.class).getResultList();
	}

	@Log
	public String findAllMenuButtonTree(String rights) {
		try {
			List <MenuButtonTree> menuButtonTrees = new ArrayList<MenuButtonTree>();
			List<MenuButton> menuButtons = findAllMenu();
			Iterator<MenuButton> iter = menuButtons.iterator();
			while(iter.hasNext()) {
				MenuButton menuButton = iter.next();
				
				if(rights != "" && rights.indexOf(","+ menuButton.getId() +",") == -1) {//没有权限
					continue;
				}
				
				if(menuButton.getParentId() == 0) {//大菜单
					MenuButtonTree menuButtonTree = StaticMethods.toMenuButtonTree(menuButton);
					List<MenuButtonTree> menuButtonTrees2 = getChildrenMenus(menuButtons, menuButtonTree.getId(), rights);
					if(menuButtonTrees2 != null && menuButtonTrees2.size() != 0) {
						menuButtonTree.setChildren(menuButtonTrees2);
					}
					menuButtonTrees.add(menuButtonTree) ;
				}
			}
			return new Gson().toJson(menuButtonTrees);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	private List<MenuButtonTree> getChildrenMenus(List<MenuButton> menuButtons, int parentId, String rights) {
		List <MenuButtonTree> menuButtonTrees = new ArrayList<MenuButtonTree>();
		Iterator<MenuButton> iter = menuButtons.iterator();
		while(iter.hasNext()) {
			MenuButton menuButton = iter.next();
			
			if(rights != "" && rights.indexOf(","+ menuButton.getId() +",") == -1) {//没有权限
				continue;
			}
			
			if(menuButton.getParentId() == parentId) {
				MenuButtonTree menuButtonTree = StaticMethods.toMenuButtonTree(menuButton);
				List<MenuButtonTree> menuButtonTrees2 = getChildrenMenus(menuButtons, menuButtonTree.getId(), rights);
				if(menuButtonTrees2 != null && menuButtonTrees2.size() != 0) {
					menuButtonTree.setChildren(menuButtonTrees2);
				}
				menuButtonTrees.add(menuButtonTree);
			}
		}
		return menuButtonTrees;
	}
	
	@Log
	@Transactional
	public String addRole(String roleJson) {
		try {
			Gson gson = new Gson(); 
			MsRole msRole = dataManager.add(gson.fromJson(roleJson, MsRole.class));
			return gson.toJson(msRole);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String addRoles(String rolesJson) {
		try {
			Type roleType = new TypeToken<List<MsRole>>(){}.getType();
			List<MsRole> msRoles =  new Gson().fromJson(rolesJson, roleType);
			Iterator<MsRole> iterator = msRoles.iterator();
			while (iterator.hasNext()) {
				MsRole msRole = iterator.next();
				dataManager.add(msRole);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	@Transactional
	public String updateRole(String roleJson) {
		try {
			dataManager.update(new Gson().fromJson(roleJson, MsRole.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateRoles(String rolesJson) {
		try {
			Type roleType = new TypeToken<List<MsRole>>(){}.getType();
			List<MsRole> msRoles =  new Gson().fromJson(rolesJson, roleType);
			Iterator<MsRole> iterator = msRoles.iterator();
			while (iterator.hasNext()) {
				MsRole msRole = iterator.next();
				dataManager.update(msRole);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public MsRole findByRoleName(String roleName) {
		try {
			MsRole msRole = dataManager.createQuery("select t from MsRole t where t.roleName = :roleName", MsRole.class).setParameter("roleName", roleName).getSingleResult();
			return msRole;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Log
	@Transactional
	public String deleteRoleById(String roleId) {
		try {
			dataManager.deleteById(MsRole.class, Integer.parseInt(roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}
	
	@Log
	@Transactional
	public String deleteRoleByIds(String roleIds) {
		try {
			Integer[] ids = new Gson().fromJson(roleIds, Integer[].class);
			dataManager.deleteByIdBatch(MsRole.class, ids);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	@Log
	public String findAllRoles() {
		try {
			List<MsRole> msRoles = findAllRole();
			Iterator<MsRole> iter = msRoles.iterator();
			while(iter.hasNext()) {
				MsRole msRole = iter.next();
				dataManager.detachEntity(msRole);
			}
			return new Gson().toJson(msRoles); 
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public List<MsRole> findAllRole() {
		return dataManager.createNamedQuery("MsRole.findAllJoinMenu", MsRole.class).getResultList();
	}

	@Log
	public String findAllRoleTree(int roleId) {
		try {
			List <MsRoleTree> msRoleTrees = new ArrayList<MsRoleTree>();
			List<MsRole> msRoles = findAllRole();
			Iterator<MsRole> iter = msRoles.iterator();
			List<MsRoleTree> rootTree = new ArrayList<MsRoleTree>();
			MsRoleTree root = new MsRoleTree();
			while(iter.hasNext()) {
				MsRole msRole = iter.next();
				if(msRole.getParentId() == roleId) {
					MsRoleTree msRoleTree = StaticMethods.toMsRoleTree(msRole);
					
					List<MsRoleTree> msRoleTrees2 = getChildrenRoles(msRoles, msRoleTree.getId());
					if(msRoleTrees2 != null && msRoleTrees2.size() != 0) {
						msRoleTree.setChildren(msRoleTrees2);
					}
					msRoleTrees.add(msRoleTree) ;
				}

				if(msRole.getId().intValue() == roleId) {
					root = StaticMethods.toMsRoleTree(msRole);
				}
			}

			root.setChildren(msRoleTrees);
			rootTree.add(root);
			return new Gson().toJson(rootTree);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	private List<MsRoleTree> getChildrenRoles(List<MsRole> msRoles, int parentId) {
		List <MsRoleTree> msRoleTrees = new ArrayList<MsRoleTree>();
		Iterator<MsRole> iter = msRoles.iterator();
		while(iter.hasNext()) {
			MsRole msRole = iter.next();
			if(msRole.getParentId() == parentId) {
				MsRoleTree msRoleTree = StaticMethods.toMsRoleTree(msRole);
				
				List<MsRoleTree> msRoleTrees2 = getChildrenRoles(msRoles, msRoleTree.getId());
				if(msRoleTrees2 != null && msRoleTrees2.size() != 0) {
					msRoleTree.setChildren(msRoleTrees2);
				}
				msRoleTrees.add(msRoleTree);
			}
		}
		return msRoleTrees;
	}

	@Log
	public String login(String loginJson) {
		MsUser flexUser = new Gson().fromJson(loginJson, MsUser.class);
		MsUser javaUser =  dataManager.createNamedQuery("findUserByUserCode", MsUser.class).setParameter("userCode", flexUser.getUserCode()).getSingleResult();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		Gson gson = new Gson();
		if(javaUser != null) {
			if(javaUser.getIsEnable() == 1) {
				if(javaUser.getPassword().equals(flexUser.getPassword())) {
					List<MsRole> roleList = javaUser.getMsRoles();
					if(roleList.size() != 0) {
						List<MenuButton> menuButton_list = new ArrayList<MenuButton>();
						Iterator<MsRole> iterator = roleList.iterator();
						while (iterator.hasNext()) {
							MsRole msRole = iterator.next();
							List<MenuButton> menuButton_list1 = msRole.getMenuButton();
							if(menuButton_list1 != null) {
								Iterator<MenuButton> iterator2 = menuButton_list1.iterator();
								while (iterator2.hasNext()) {
									MenuButton menuButton = iterator2.next();
									if(!menuButton_list.contains(menuButton)) {
										menuButton_list.add(menuButton);
									}
								}
							}
						}
						
						if(menuButton_list.size() != 0) {
							//更新登录Uid
							javaUser.setLoginUid(Uid.getUidUtil().createUID());
							dataManager.update(javaUser);
							

							// 保存用户基本信息
							hm.put("msUser", javaUser);
							// 登录时间
							hm.put("loginTime", new Date());
							//权限菜单
							hm.put("menuButton", menuButton_list);
							//用户的角色和权限也传前台去，节省前台资源。
							hm.put("role", roleList);
							hm.put("msg", "登陆成功。");
							hm.put("msgState", "1");
						} else {
							hm.put("msg", "用户还未分配功能权限!");
							hm.put("msgState", "0");
						}
					} else {
						hm.put("msg", "用户还没有分配角色!");
						hm.put("msgState", "0");
					}
				} else {
					hm.put("msg", "用户名或密码输入错误!");
					hm.put("msgState", "0");
				}
			} else {
				hm.put("msg", "该用户已注销!");
				hm.put("msgState", "0");
			}
		} else {
			hm.put("msg", "系统中没有此用户!");
			hm.put("msgState", "0");
		}

		return gson.toJson(hm);
	}
}
