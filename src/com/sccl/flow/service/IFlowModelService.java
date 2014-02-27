package com.sccl.flow.service;

import java.util.List;

import com.sccl.flow.entity.FlowModel;
import com.sccl.flow.entity.FlowNodeModel;
import com.sccl.flow.entity.FlowOprtModule;
import com.sccl.flow.vo.RoleAndNodeModel;


public interface IFlowModelService {
	public String getTypeAndModel(String whereType, String whereModel);
	public int addModel(FlowModel flowModel);
	public void updateModel(FlowModel flowModel);
	public List<RoleAndNodeModel> getAllNodeByModelId(String id);
	public List<FlowOprtModule> getAllFlowOprtModule(String where,String orderby);
	public boolean saveFlowNodeModels(List<FlowNodeModel> flowNodeModelList);

	
	/**
	 * 查询转办
	 * @param flowOprtID
	 * @param zhuanBan
	 * @return
	 */
	public List<FlowOprtModule> getZhuanBan(int flowOprtID, String zhuanBan);
}
