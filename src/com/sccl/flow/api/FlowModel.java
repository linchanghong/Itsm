package com.sccl.flow.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.flow.entity.FlowNodeModel;
import com.sccl.flow.entity.FlowOprtModule;
import com.sccl.flow.service.IFlowModelService;
import com.sccl.flow.vo.DataInteraction;
import com.sccl.framework.utils.Tools;

@Component("flowModel")
@RemotingDestination
public class FlowModel {
	public IFlowModelService flowModelService;

	public IFlowModelService getFlowModelService() {
		return flowModelService;
	}
	
	@Resource(name = "flowModelService")
	public void setFlowModelService(IFlowModelService flowModelService) {
		this.flowModelService = flowModelService;
	}
	
	/**
	 * 查询流程类型和模型
	 * @param json 查询条件
	 * @return
	 */
	public String getTypeAndModel(String json){
		Gson gson = Tools.getGson();
		List<String> list = gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
		json = "[]";
		if(list.size()==2){
			json = flowModelService.getTypeAndModel(list.get(0), list.get(1));
		}
		return json;
	}
	
	/**
	 * 新增流程模型
	 * @param json 模型信息
	 * @return
	 */
	@Transactional
	public String addModel(String json){
		
		Gson gson = new Gson();
		DataInteraction di = new DataInteraction();
		com.sccl.flow.entity.FlowModel flowModel = gson.fromJson(json, com.sccl.flow.entity.FlowModel.class);
		di.setType(flowModelService.addModel(flowModel));
		return gson.toJson(di);
	}
	
	/**
	 * 修改流程模型
	 * @param json 模型信息
	 * @return
	 */
	@Transactional
	public void updaetModel(String json){
		
		Gson gson = new Gson();
		com.sccl.flow.entity.FlowModel flowModel = gson.fromJson(json, com.sccl.flow.entity.FlowModel.class);
		flowModelService.updateModel(flowModel);
	}
	
	/**
	 * 查询模型全部节点
	 * @param 模型ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAllNodeByModelId(String id){
		List list = flowModelService.getAllNodeByModelId(id);
		
		String json = Tools.getGson().toJson(list);
		//System.out.println(json);
        return json;
    }
	
	public String getAllFlowOprtModule(String where,String orderby){
		List<FlowOprtModule> list = flowModelService.getAllFlowOprtModule(where, orderby);
		
		String json = Tools.getGson().toJson(list);
//		System.out.println(json);
		return json;
	}
	
	/**
	 * 保存节点
	 * @param flownodemodellist
	 * @return
	 */
	@Transactional
	public String saveFlowNodeModels(String json){
		Gson gson = new Gson();
		List<FlowNodeModel> flowNodeModelList = gson.fromJson(json, new TypeToken<List<FlowNodeModel>>(){}.getType());
		DataInteraction di = new DataInteraction();
		di.setResult(flowModelService.saveFlowNodeModels(flowNodeModelList));
		return gson.toJson(di);
	}
	
	/**
	 * 查询转办
	 * @param flowOprtID
	 * @param zhuanBan
	 * @return
	 */
	public String getZhuanBan(int flowOprtID, String zhuanBan)
    {
		
		List<FlowOprtModule> list = flowModelService.getZhuanBan(flowOprtID, zhuanBan);
		
		
		return Tools.getGson().toJson(list);
    }
}
