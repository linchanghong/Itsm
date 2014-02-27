package com.sccl.framework.service.user;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
//import com.hr.psn.gwjj.vo.PsnJobPromInfo;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.entity.MsUser;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.vo.QueryUserVo;

@Component("userService")
public class UserService implements IUserService {

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
	public String addUser(String userJson) {
		try {
			
			MsUser msUser = StaticMethods.getDateGson().fromJson(userJson, MsUser.class);
			
			dataManager.add(msUser);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String addUsers(String usersJson) {
		try {
			Type userType = new TypeToken<List<MsUser>>() {}.getType();
			List<MsUser> users = new Gson().fromJson(usersJson, userType);
			Iterator<MsUser> iter = users.iterator();
			while (iter.hasNext()) {
				MsUser msUser = (MsUser) iter.next();
				
				dataManager.add(msUser);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String updateUser(String userJson) {
		try {
			MsUser msUser = StaticMethods.getDateGson().fromJson(userJson, MsUser.class);
			dataManager.update(msUser);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String updateUsers(String usersJson) {
		try {
			Type userType = new TypeToken<List<MsUser>>() {}.getType();
			List<MsUser> users = new Gson().fromJson(usersJson, userType);
			Iterator<MsUser> iter = users.iterator();
			while (iter.hasNext()) {
				MsUser msUser = (MsUser) iter.next();
				dataManager.update(msUser);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String deleteUserById(String userId) {
		try {
			dataManager.deleteById(MsUser.class, Integer.parseInt(userId));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String deleteUserByIds(String userIds) {
		try {
			Integer[] ids = new Gson().fromJson(userIds, Integer[].class);
			dataManager.deleteByIdBatch(MsUser.class, ids);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	

	@Log
	public String findUserById(String userId) {
		try {
			return StaticMethods.getExposeGson().toJson(dataManager.findById(MsUser.class, Integer.parseInt(userId)));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findUserByName(String userName) {
		try {
			MsUser msUser = dataManager.createNamedQuery("findUserByName", MsUser.class).setParameter("userName", userName).getSingleResult();
			return StaticMethods.getExposeGson().toJson(msUser);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findUserByOrgId(String orgId) {
		try {
			List<MsUser> msUsers = dataManager.createNamedQuery("findUserByOrgId", MsUser.class).setParameter("orgId", Integer.parseInt(orgId)).getResultList();
			return StaticMethods.getExposeGson().toJson(msUsers);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllUserByOrgIdPage(String orgId, int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsUser> msUsers = dataManager.createNamedQuery("MsUser.findByOrgId", MsUser.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").setFirstResult(first).setMaxResults(size).getResultList();
			reList.add(0, msUsers);
			if(isCount) {
				Long count = dataManager.createNamedQuery("MsUser.findByOrgIdCount", Long.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").getSingleResult();
				reList.add(1, count);
			}
			return StaticMethods.getExposeGson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Transactional
	public void changPassWord(Integer id, String password) {
		dataManager.createNamedQuery("MsUser.changPassWord").setParameter("id", id).setParameter("password", password).executeUpdate();
	}
	
	public String findAllUser(int first, int size, boolean isCount){
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsUser> msPersons = dataManager.createQuery("select p from MsUser p order by p.id", MsUser.class).setFirstResult(first).setMaxResults(size).getResultList();
			reList.add(0, msPersons);
			if(isCount) {
				//Long count = dataManager.createQuery("select count(p) from MsPerson p ", Long.class).getSingleResult();
				Long count = dataManager.createQuery("select count(p) from MsUser p ", Long.class).getSingleResult();
				reList.add(1, count);
			}
			
			return StaticMethods.getExposeGson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	@Log
  public String findAllUser(String userInfoCondtion, int first,
      int size, boolean isCount) {
    try {
      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
      QueryUserVo userInfo = gson.fromJson(userInfoCondtion, QueryUserVo.class);
      List<Object> reList = new ArrayList<Object>();
      String query = "select p from MsUser p";
      String whereStr = " where 0=0";
      if(userInfo != null && userInfo.getPsnName() != null && !userInfo.getPsnName().equals("")){
        whereStr = whereStr+" and  p.msPerson.personName like '%"+userInfo.getPsnName().trim()+"%'";
      }
      if(userInfo != null && userInfo.getPsnUser() != null && !userInfo.getPsnUser().equals("")){
        whereStr = whereStr+" and p.userName like '%"+userInfo.getPsnUser().trim()+"%'";
      }
      if(userInfo != null && userInfo.getPsnLogin() != null && !userInfo.getPsnLogin().equals("")){
        whereStr = whereStr+" and p.userCode like '%"+userInfo.getPsnLogin().trim()+"%'";
      }
      if(userInfo != null && userInfo.getPsnUnit() != null && userInfo.getPsnUnit() != -1){
        whereStr = whereStr+" and p.msPerson.organization.id = "+userInfo.getPsnUnit();
      }
      List<MsUser> msPersons = dataManager.createQuery(query+whereStr + " order by p.id", MsUser.class).setFirstResult(first).setMaxResults(size).getResultList();
      reList.add(0, msPersons);
      reList.add(1, msPersons.size());
      return StaticMethods.getExposeGson().toJson(reList);
    } catch (Exception e) {
      e.printStackTrace();
      return "0";
    }
  }
}
