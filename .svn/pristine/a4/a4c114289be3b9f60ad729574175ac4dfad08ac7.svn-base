package com.sccl.serviceManager.common.tree.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.SystemStatic;
import com.sccl.framework.entity.Organization;

@SuppressWarnings("all")
@Component("treeService")
public class TreeService implements ITreeService {
	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	/**
	 * 查询管理公司所有部门
	 * 
	 * @param companyid
	 * @param isAdmin
	 * @return
	 */
	public List<Organization> findAllDepartment(String companyid, int isAdmin, boolean isShowDepartment) {

		StringBuilder sql = new StringBuilder();
		if(SystemStatic.IS_ADMIN_ZERO==isAdmin && SystemStatic.COMPANY_ID.equals(companyid)){
			sql.append("select * from organization t where t.org_id='"+SystemStatic.COMPANY_ID+"'");
			if(isShowDepartment)
				sql.append(" union select * from organization t where t.company_id='"+SystemStatic.COMPANY_ID+"'");
		}else{
			sql.append("select * from organization t");
			if(!isShowDepartment)
				sql.append(" where t.org_type='1'");
			
			sql.append(" start with t.org_id='"+ companyid + "' connect by t.parent_id = prior t.org_id");
		}

		List<Organization> list = (List<Organization>) dataManager.nativeQuery(
				sql.toString(), Organization.class);

		return list;
	}
	
	
	@Override
	public String initUnitAndDeptTree(String companyId, int isAdmin) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<Organization> orgList = this.initOrgTree(companyId,isAdmin);
		return gson.toJson(orgList);
	}
	
	@Override
	public String initAddDeptTree(String companyId,String currentNodeId) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String sql = "select * from organization t where t.org_type='0' and parent_id = "+currentNodeId;
		List<Organization> lists = (List<Organization>) dataManager.nativeQuery(sql.toString(), Organization.class);
		return gson.toJson(lists);
	}
	
	public List<Organization> initOrgTree(String companyId,int isAdmin){
		String sql ="";
		if(SystemStatic.IS_ADMIN_ZERO == isAdmin && SystemStatic.COMPANY_ID.equals(companyId)){
			sql = "select * from organization t where t.org_id = '"+SystemStatic.COMPANY_ID+"'";
		}else{
			sql = "select * from organization t where t.org_type='1' start with t.org_id='"+ companyId + "' connect by t.parent_id = prior t.org_id";
		}
		List<Organization> lists = (List<Organization>) dataManager.nativeQuery(sql.toString(), Organization.class);
		return lists;
	}
}
