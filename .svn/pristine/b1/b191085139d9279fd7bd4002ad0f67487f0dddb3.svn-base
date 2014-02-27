package com.sccl.serviceManager.common.tree.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.utils.Tools;
import com.sccl.serviceManager.common.tree.service.ITreeService;

@SuppressWarnings("all")
@Component("treeAPI")
@RemotingDestination
public class TreeAPI {
	private ITreeService treeService;

	public ITreeService getTreeService() {
		return treeService;
	}

	@Resource(name = "treeService")
	public void setTreeService(ITreeService treeService) {
		this.treeService = treeService;
	}
	
	/**
	 * 查询管理公司所有部门
	 * @param companyid
	 * @param isAdmin
	 * @return
	 */
	public String findAllDepartment(String companyid, int isAdmin, boolean isShowDepartment){
		Gson gson = Tools.getGson();
		List<Organization> list = treeService.findAllDepartment(companyid, isAdmin, isShowDepartment);
		String json = gson.toJson(list);
		return json;
	}
	
	/**
	 * 初始化公司树
	 * @return
	 */
	public String initUnitAndDeptTree(String companyId,int isAdmin){
        return treeService.initUnitAndDeptTree(companyId,isAdmin);
	}
	
	/**
	 * 为指定节点下加载部门
	 * @param companyId
	 * @param currentNodeId
	 * @return
	 */
	public String initAddDeptTree(String companyId,String currentNodeId){
		return treeService.initAddDeptTree(companyId,currentNodeId);
	}
}
