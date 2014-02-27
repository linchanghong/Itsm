package com.sccl.serviceManager.common.tree.service;

import java.util.List;

import com.sccl.framework.entity.Organization;

public interface ITreeService {
	/**
	 * 查询管理公司所有部门
	 * 
	 * @param companyid
	 * @param isAdmin
	 * @return
	 */
	public List<Organization> findAllDepartment(String companyid, int isAdmin, boolean isShowDepartment);
	
	public String initUnitAndDeptTree(String companyId,int isAdmin);
	public String initAddDeptTree(String companyId,String currentNodeId);
}
