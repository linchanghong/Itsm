package com.sccl.framework.service.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sccl.framework.DataManager;
import com.sccl.framework.common.utils.Uid;
import com.sccl.framework.entity.MenuButton;
import com.sccl.framework.entity.MsRole;
import com.sccl.framework.entity.MsUser;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.service.roleRight.IRoleRightService;
import com.sptpc.util.LtpaUtils;

import flex.messaging.FlexContext;

@Component
@Transactional
public class UserLoginServive implements IUserLoginServive {
	
	private DataManager dataManager;
	private IRoleRightService roleRightService;

	public IRoleRightService getRoleRightService() {
		return roleRightService;
	}

	@Resource
	public void setRoleRightService(IRoleRightService roleRightService) {
		this.roleRightService = roleRightService;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	/**
	 * 用户登录  --- 不记日志
	 * 
	 * @param us
	 * @return
	 */
	//@Log
	public HashMap<String, Object> userLogin(MsUser us) {
		
		//MsUser us = new Gson().fromJson(usJson, MsUser.class);
		// 存放用户的权限，为了消除用户的重复权限
		String rights = ",";
		String menuButton_list = "";
		//SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		//sdf.applyPattern("yyyy-MM-dd");
		// 返回前台的hm
		HashMap<String, Object> hm = new HashMap<String, Object>();

		// 取用户的基本信息
		MsUser msUser = finduser(us.getUserCode());
		
		//用户不存在
		if (msUser == null) {
			hm.put("msg", "系统中没有此用户。");
			hm.put("msgState", "0");

			return hm;
		}
		
		//是否是admin管理员，admin管理员默认拥有所有权限，所有角色
		boolean isSuperAdmin = msUser.getIsAdmin()==2?true:false;
		
		// 用户不可用
		if (msUser.getIsEnable() != 1) {
			hm.put("msg", "该用户已注销。");
			hm.put("msgState", "0");
	
			return hm;
		}
			
		// 密码是否匹配
		if (!msUser.getPassword().equals(us.getPassword())) {
			hm.put("msg", "用户名或密码输入错误。");
			hm.put("msgState", "0");

			return hm;
		}

		// 存放用户的角色
		List<MsRole> roleList = null;
		if(isSuperAdmin) {
			roleList = dataManager.createNamedQuery("MsRole.findAll", MsRole.class).getResultList();
		} else {
			roleList = msUser.getMsRoles();
		}

		//还未分配角色
		if(roleList.size() == 0) {
			hm.put("msg", "用户还没有分配角色。");
			hm.put("msgState", "0");

			return hm;
		}

		List<Organization> organizations = new ArrayList<Organization>();
		//角色菜单按钮权限
		if(isSuperAdmin) {
			organizations = dataManager.createNamedQuery("Organization.findAllCompany", Organization.class).getResultList();
			List<Integer> idList = dataManager.createQuery("select mb.id from MenuButton mb", Integer.class).getResultList();
			rights = getAllRights(idList);;
		} else {
			organizations = msUser.getOrganizations();
			rights = getRoleRights(roleList);
		}
		
		//还未分配功能权限
		if(rights.equals(",")) {
			hm.put("msg", "用户还未分配功能权限。");
			hm.put("msgState", "0");

			return hm;
		}
		
		// 存放有权限的菜单
		menuButton_list = roleRightService.findAllMenuButtonTree(rights);
		
		//更新登录Uid
		msUser.setLoginUid(Uid.getUidUtil().createUID());
		dataManager.update(msUser);
		
		//session
		FlexContext.getFlexSession().setAttribute("msUser", msUser);
		FlexContext.getFlexSession().setAttribute("loginTime", new Date());
		FlexContext.getFlexSession().setAttribute("userBehave",  new String[2]);

		// 保存用户基本信息
		hm.put("msUser", msUser);
		// 返回服务器登录时间
		hm.put("loginTime", new Date());
		//所有有权限的菜单
		hm.put("menuButton", menuButton_list);
		//用户的角色和权限也传前台去，节省前台资源。
		hm.put("role", roleList);
		hm.put("orgs", organizations);
		hm.put("rights", rights);
		hm.put("msg", "登陆成功。");
		hm.put("msgState", "1");

		return hm;
	}
	
	public String getRoleRights(List<MsRole> roleList) {
		String rights = ",";
		Iterator<MsRole> roleIter = roleList.iterator();
		while (roleIter.hasNext()) {
			MsRole msRole = roleIter.next();
			List<MenuButton> menuButtons = msRole.getMenuButton();
			if (menuButtons.size() != 0) {
				Iterator<MenuButton> mbIter = menuButtons.iterator();
				while(mbIter.hasNext()) {
					MenuButton mb =mbIter.next();
					String right = mb.getId().toString();
					if(rights.indexOf(","+ right + ",") == -1) {
						rights += right + ",";
					}
				}
			}
		}
		return rights;
	}
	

	public String getAllRights(List<Integer> idList) {
		String rights = ",";
		Iterator<Integer> idIter = idList.iterator();
		while (idIter.hasNext()) {
			String right = idIter.next().toString();
			if(rights.indexOf(","+ right + ",") == -1) {
				rights += right + ",";
			}
		}
		
		return rights;
	}

	public String autoLogin(String ltpaToken, String ltpa3DESKey,
			String ltpaPassword) {
		byte[] secretKey = null;
		try {
			secretKey = LtpaUtils.getSecretKey(ltpa3DESKey, ltpaPassword);
		} catch (Exception e) {
			e.printStackTrace();
			return "reMsg:自动登录失败，请手动登录。";
		}

		String ltpaPlaintext = "";
		try {
			ltpaPlaintext = new String(LtpaUtils.decryptLtpaToken(ltpaToken,
					secretKey));
		} catch (Exception e) {
			e.printStackTrace();
			return "reMsg:自动登录失败，请手动登录。";
		}
		LtpaUtils.displayTokenData(ltpaPlaintext);

		String userCode = getCookieCode(ltpaPlaintext);
		MsUser msUser = finduser(userCode);
		if (msUser == null) {
			return "reMsg:系统没有" + ltpaPlaintext + "用户，自动登录失败，请手动登录。";
		}
		return userCode + "," + msUser.getPassword();
	}

	private String getCookieCode(String ltpaPlaintext) {
		int codeIndex = ltpaPlaintext.indexOf("0409");
		if (codeIndex != -1) {
			return ltpaPlaintext.substring(codeIndex, codeIndex + 8);
		} else {
			return ltpaPlaintext;
		}
	}

	/**
	 * 根据code查询用户信息
	 * 
	 * @param logcode
	 * @return
	 */
	public MsUser finduser(String userCode) {
		try {
			return dataManager.createNamedQuery("findUserByUserCode", MsUser.class).setParameter("userCode", userCode).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据code查询用户信息
	 * 
	 * @param logcode
	 * @return
	 */
	@Transactional
	public MsUser isLogin(String userCode, String userBehave, String parameters) {
		FlexContext.getFlexSession().setAttribute("userBehave",  userBehave);
		FlexContext.getFlexSession().setAttribute("parameters",  parameters);
		return finduser(userCode);
	}

}
