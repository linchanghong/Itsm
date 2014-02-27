package com.sccl.flow.service;

import java.util.List;

import com.sccl.flow.entity.FlowRole;
import com.sccl.flow.entity.FlowRoleUsers;
import com.sccl.flow.vo.ViewFlowRoleUsers;

public interface IFlowRoleService {
	public List<ViewFlowRoleUsers> getAllRoleAndUser(String where, String orderby);
	public int addRole(FlowRole flowRole);
	public int updateRole(FlowRole flowRole);
	public int addRoleUsers(FlowRoleUsers flowRoleUsers);
	public int updateRoleUsers(FlowRoleUsers flowRoleUsers);
	public boolean deleteRoleUsers(Long id);
	//通用节点流程角色绑定
	public List<FlowRole> getAllRole(String where, String orderby);
}
