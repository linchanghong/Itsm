package com.sccl.serviceManager.common.selectWindow.user.service;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Component;

import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.SystemStatic;
import com.sccl.framework.entity.MsPerson;
import com.sccl.framework.entity.Organization;
import com.sccl.serviceManager.common.selectWindow.person.dto.PsnInfoDTO;
import com.sccl.serviceManager.common.selectWindow.person.dto.SimplifyPsnInfoDTO;
import com.sccl.serviceManager.common.selectWindow.user.dto.UserInfoDTO;

@SuppressWarnings("all")
@Component("selectUserService")
public class SelectUserService implements ISelectUserService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	/**
	 * 根据部门Id查询用户
	 * 
	 * @param companyid
	 * @param depid
	 * @return
	 */
	public List<Object> findUsers(String depid, int first, int size,
			boolean isCount) {
	    
		List<Object> list = new ArrayList<Object>();
		String sql="select t1.userId userId, t1.userName userName, t1.userCode userCode, " +
				"t1.password password, t1.orgId orgId, t1.orgName orgName, t1.orgCode orgCode, " +
				"t1.companyId companyId, t1.companyName companyName, " +
				"case when t2.bugcount is null then 0 else t2.bugcount end bugCount, " +
				"case when t2.problemcount is null then 0 else t2.problemcount end problemCount, " +
				"case when t2.demandcount is null then 0 else t2.demandcount end demandCount from (";
		
		if(depid.equals(SystemStatic.COMPANY_ID)){
		    //select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, 
		    //u.PASSWORD password,  b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, 
		    //b.company_id companyId,c.org_name companyName 
		    //from ms_user u,  ms_person a, organization b,organization c 
		    //where 1=1  and c.org_id=b.company_id and a.org_id=b.org_id 
		    //and u.PERSON_ID=a.PERSON_ID 
		    //and b.org_id in(120,123,145,121,143,164,163,165,144,122)
		    
			sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
					" b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
					"c.org_name companyName " +
					"from ms_user u,  ms_person a, organization b," +
					"organization c " +
					"where c.org_id=b.company_id " +
					"and a.org_id=b.org_id " +
					"and u.PERSON_ID=a.PERSON_ID";
			
		}else{
		    
			String ids = findAllSonDepatment(depid);
			
			sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                    " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                    "c.org_name companyName " +
                    "from ms_user u,  ms_person a, organization b," +
                    "organization c " +
                    "where c.org_id=b.company_id " +
                    "and a.org_id=b.org_id " +
                    "and u.PERSON_ID=a.PERSON_ID " +
                    "and b.org_id in(" + ids + ")";
			
		}
		
		sql += ") t1 left join view_current_workload t2 on t1.userId = t2.userId order by t1.userId desc";
		List<UserInfoDTO> listUser = (List<UserInfoDTO>) dataManager.nativeQuery(sql, UserInfoDTO.class, first, size);
		
		list.add(listUser);
		
		if (isCount) {
			int count = dataManager.getCountBySql(sql);
			list.add(1, count);
		}
		
		return list;
	}
	
	/**
	 * 根据部门Id查询用户,且不包含某一用户
	 */
	public List<Object> findUsersWithoutID(String depid, int userID, int first, int size,
            boolean isCount) {
        
        List<Object> list = new ArrayList<Object>();
        String sql="select t1.userId userId, t1.userName userName, t1.userCode userCode, " +
                "t1.password password, t1.orgId orgId, t1.orgName orgName, t1.orgCode orgCode, " +
                "t1.companyId companyId, t1.companyName companyName, " +
                "case when t2.bugcount is null then 0 else t2.bugcount end bugCount, " +
                "case when t2.problemcount is null then 0 else t2.problemcount end problemCount, " +
                "case when t2.demandcount is null then 0 else t2.demandcount end demandCount from (";
        String ifsqlString = " ";
        if(userID != -1)
            ifsqlString =  " and (u.USER_ID <= "+(userID-1)+" " +
                    "or u.USER_ID >= "+(userID+1)+") ";
       
        if(depid.equals(SystemStatic.COMPANY_ID)){
            
            sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                    " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                    "c.org_name companyName " +
                    "from ms_user u,  ms_person a, organization b," +
                    "organization c " +
                    "where 1=1 " + ifsqlString +
                    "and c.org_id=b.company_id " +
                    "and a.org_id=b.org_id " +
                    "and u.PERSON_ID=a.PERSON_ID";
            
        }else{
            
            String ids = findAllSonDepatment(depid);
            
            sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                    " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                    "c.org_name companyName " +
                    "from ms_user u,  ms_person a, organization b," +
                    "organization c " +
                    "where 1=1 " + ifsqlString +
                    "and c.org_id=b.company_id " +
                    "and a.org_id=b.org_id " +
                    "and u.PERSON_ID=a.PERSON_ID " +
                    "and b.org_id in(" + ids + ")";
            
        }
        
        sql += ") t1 left join view_current_workload t2 on t1.userId = t2.userId order by t1.userId desc";
        List<UserInfoDTO> listUser = (List<UserInfoDTO>) dataManager.nativeQuery(sql, UserInfoDTO.class, first, size);
        
        list.add(listUser);
        
        if (isCount) {
            int count = dataManager.getCountBySql(sql);
            list.add(1, count);
        }
        
        return list;
    }

	/**
	 * 查找全部下属部门ID
	 * 
	 * @param depid
	 * @return
	 */
	private String findAllSonDepatment(String depid) {
	    
		String sql = "select * from organization t start with t.org_id='"
				+ depid + "' connect by t.parent_id = prior t.org_id";
		
		List<Organization> list = (List<Organization>) dataManager.nativeQuery(
				sql, Organization.class);

		StringBuilder ids = new StringBuilder();
		
		for (int i = 0; i < list.size(); i++) {
			Organization org = list.get(i);
			Integer id = org.getId();
			ids.append(id + ",");
		}

		String tempIds = ids.toString().toString();
		
		if (tempIds.endsWith(",")) {
			int index = tempIds.lastIndexOf(",");
			ids.deleteCharAt(index);
		}

		return ids.toString();
	}

	/**
	 * 根据用户输入模糊查询
	 * 
	 * @param where
	 * @return
	 */
	public List<Object> queryUsersByNameLike(int companyid, String userName, int first, int size,
			boolean isCount) {

		List<Object> list = new ArrayList<Object>();
		
		String sql = "select t1.userId userId, t1.userName userName, t1.userCode userCode, " +
                "t1.password password, t1.orgId orgId, t1.orgName orgName, t1.orgCode orgCode, " +
                "t1.companyId companyId, t1.companyName companyName, " +
                "case when t2.bugcount is null then 0 else t2.bugcount end bugCount, " +
                "case when t2.problemcount is null then 0 else t2.problemcount end problemCount, " +
                "case when t2.demandcount is null then 0 else t2.demandcount end demandCount from (";
		sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                "c.org_name companyName " +
                "from ms_user u,  ms_person a, organization b," +
                "organization c " +
                "where c.org_id=b.company_id " +
                "and a.org_id=b.org_id " +
                "and u.PERSON_ID=a.PERSON_ID";
		
		if (null != userName && !("").equals(userName.trim()))
			sql += " and u.USER_NAME like '%" + userName + "%'";
		
		sql += ") t1 left join view_current_workload t2 on t1.userId = t2.userId order by t1.userId desc";
		List<UserInfoDTO> listUser = (List<UserInfoDTO>) dataManager.nativeQuery(sql, UserInfoDTO.class, first, (first+size));
		list.add(listUser);
		
		if (isCount) {
			int count = dataManager.getCountBySql(sql);
			list.add(count);
		}
		
		return list;
	}

    /* 
     * <p>Title: findUsersWithoutID4SysOrUserStatus</p>
     * <p>Description: </p>
     * @param depid
     * @param userID
     * @param sysID
     * @param userStatus
     * @param first
     * @param size
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.common.selectWindow.user.service.ISelectUserService#findUsersWithoutID4SysOrUserStatus(java.lang.String, int, int, int, int, int, boolean)
     */
    @Override
    public List<Object> findUsersWithoutID4SysOrUserStatus(String depid,
            int userID, int sysID, int userStatus, int first, int size,
            boolean isCount) {
        List<Object> list = new ArrayList<Object>();
        try {
            
            String sql="select t1.userId userId, t1.userName userName, t1.userCode userCode, " +
                    "t1.password password, t1.orgId orgId, t1.orgName orgName, t1.orgCode orgCode, " +
                    "t1.companyId companyId, t1.companyName companyName, " +
                    "case when t2.bugcount is null then 0 else t2.bugcount end bugCount, " +
                    "case when t2.problemcount is null then 0 else t2.problemcount end problemCount, " +
                    "case when t2.demandcount is null then 0 else t2.demandcount end demandCount from (";
            String ifsqlString = " ";
            String sysOrstatuString = " select t.user_id " +
            		"from t_system_person t " +
            		"where 1 = 1 ";
            if(sysID > 0)
                sysOrstatuString += "and t.system_id = " + sysID;
            if(userStatus > 0)
                sysOrstatuString += " and t.user_status = " + userStatus;
            if(sysOrstatuString.length() > 53)
                ifsqlString += "and u.USER_ID in (" + sysOrstatuString + ")";
            if(userID != -1)
                ifsqlString +=  " and (u.USER_ID <= "+(userID-1)+" " +
                        "or u.USER_ID >= "+(userID+1)+") ";
                
            if(depid.equals(SystemStatic.COMPANY_ID)){
                
                sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                        " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                        "c.org_name companyName " +
                        "from ms_user u,  ms_person a, organization b," +
                        "organization c " +
                        "where 1=1 " + ifsqlString +
                        "and c.org_id=b.company_id " +
                        "and a.org_id=b.org_id " +
                        "and u.PERSON_ID=a.PERSON_ID";
                
            }else{
                
                String ids = findAllSonDepatment(depid);
                
                sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                        " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                        "c.org_name companyName " +
                        "from ms_user u,  ms_person a, organization b," +
                        "organization c " +
                        "where 1=1 " + ifsqlString +
                        "and c.org_id=b.company_id " +
                        "and a.org_id=b.org_id " +
                        "and u.PERSON_ID=a.PERSON_ID " +
                        "and b.org_id in(" + ids + ")";
                
            }
            
            sql += ") t1 left join view_current_workload t2 on t1.userId = t2.userId order by t1.userId desc";
            List<UserInfoDTO> listUser = (List<UserInfoDTO>) dataManager.nativeQuery(sql, UserInfoDTO.class, first, size);
            
            list.add(listUser);
            
            if (isCount) {
                int count = dataManager.getCountBySql(sql);
                list.add(1, count);
            }
            
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    /* 
     * <p>Title: queryUsersByNameLike4SysOrUserStatus</p>
     * <p>Description: </p>
     * @param userID
     * @param sysID
     * @param userStatus
     * @param userName
     * @param first
     * @param size
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.common.selectWindow.user.service.ISelectUserService#queryUsersByNameLike4SysOrUserStatus(int, int, int, java.lang.String, int, int, boolean)
     */
    @Override
    public List<Object> queryUsersByNameLike4SysOrUserStatus(int userID,
            int sysID, int userStatus, String userName, int first, int size,
            boolean isCount) {
        List<Object> list = new ArrayList<Object>();
        try {
            
            String sql = "select t1.userId userId, t1.userName userName, t1.userCode userCode, " +
                    "t1.password password, t1.orgId orgId, t1.orgName orgName, t1.orgCode orgCode, " +
                    "t1.companyId companyId, t1.companyName companyName, " +
                    "case when t2.bugcount is null then 0 else t2.bugcount end bugCount, " +
                    "case when t2.problemcount is null then 0 else t2.problemcount end problemCount, " +
                    "case when t2.demandcount is null then 0 else t2.demandcount end demandCount from (";
            sql += "select u.USER_ID userId, a.PERSON_NAME userName, u.USER_CODE userCode, u.PASSWORD password, " +
                    " b.ORG_ID orgId, b.org_name orgName, b.org_code orgCode, b.company_id companyId," +
                    "c.org_name companyName " +
                    "from ms_user u,  ms_person a, organization b," +
                    "organization c " +
                    "where c.org_id=b.company_id " +
                    "and a.org_id=b.org_id " +
                    "and u.PERSON_ID=a.PERSON_ID";
            
            String sysOrstatuString = " select t.user_id " +
                    "from t_system_person t " +
                    "where 1 = 1 ";
            if(sysID > 0)
                sysOrstatuString += "and t.system_id = " + sysID;
            if(userStatus > 0)
                sysOrstatuString += " and t.user_status = " + userStatus;
            if(sysOrstatuString.length() > 53)
                sql += "and u.USER_ID in (" + sysOrstatuString + ")";
            
            if (null != userName && !("").equals(userName.trim()))
                sql += " and u.USER_NAME like '%" + userName + "%'";
            
            sql += ") t1 left join view_current_workload t2 on t1.userId = t2.userId order by t1.userId desc";
            List<UserInfoDTO> listUser = (List<UserInfoDTO>) dataManager.nativeQuery(sql, UserInfoDTO.class, first, (first+size));
            list.add(listUser);
            
            if (isCount) {
                int count = dataManager.getCountBySql(sql);
                list.add(count);
            }
            
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }
	
}