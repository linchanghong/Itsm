package com.sccl.flow.api;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sccl.flow.common.FlowException;
import com.sccl.flow.entity.FlowNodeInstence;
import com.sccl.flow.service.IFlowGlobalService;
import com.sccl.flow.vo.DataInteraction;
import com.sccl.flow.vo.ViewFlowApproval;
import com.sccl.flow.vo.ViewFlowNodeInstence;
import com.sccl.framework.entity.MsPerson;
import com.sccl.framework.utils.Tools;

@Component("flowGlobal")
@RemotingDestination
public class FlowGlobal {
	private IFlowGlobalService flowGlobalService;

	public IFlowGlobalService getFlowGlobalService() {
		return flowGlobalService;
	}

	@Resource(name = "flowGlobalService")
	public void setFlowGlobalService(IFlowGlobalService flowGlobalService) {
		this.flowGlobalService = flowGlobalService;
	}
	
	/**
	 * 呈报
	 * @param user 操作人
	 * @param sendId 发起人ID
	 * @param flowTypeId 流程业务ID
	 * @param billid 单据ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String startFlowInstence(String json){
		Gson gson = Tools.getGson();
		List<Object> list = gson.fromJson(json, new TypeToken<List<Object>>(){}.getType());
		String msg="";
		boolean result = false;
		DataInteraction di = new DataInteraction();
		
		if(list.size()==4){
			try {
				Map<String, Object> map = (Map<String, Object>)list.get(0);
				MsPerson person = (MsPerson)Tools.parseMapToQueryEntity(map, MsPerson.class);
				//List<FlowNodeInstence> list = 
				long sendId = Long.valueOf(String.valueOf(list.get(1)));
				long flowTypeId = Long.valueOf(String.valueOf(list.get(2)));
				long billid = Long.valueOf(String.valueOf(list.get(3)));
				
				flowGlobalService.startFlowInstence(person, sendId, flowTypeId, billid);
				result = true;
				msg="呈报成功！";
			} catch (FlowException e){
				msg=e.getMessage();
				e.printStackTrace();
			} catch (Exception e){
				msg="呈报失败！";
				e.printStackTrace();
			}
		}else{
			msg="呈报失败！";
		}
		
		di.setResult(result);
		di.setMsg(msg);
		
//		MsPerson user, Long sendId, Long flowTypeId, long billid
		json = gson.toJson(di);
		return json;
	}
	
	public int getAllCount(String wherestr){
		
        return flowGlobalService.getAllCount(wherestr);
    }

    @SuppressWarnings("unchecked")
	public List getPageData(int pageIndex, int pageSize, String wherestr, String orderBy){
        return flowGlobalService.getPageData(pageIndex, pageSize, wherestr, orderBy);
    }
    
    /**
     * 该流程的所有历史审批记录
     * @param typeid
     * @param billid
     * @return
     */
    public List<ViewFlowNodeInstence> getHistory(String typeid, String billid){
    	
    	return flowGlobalService.getHistory(typeid, billid);
    }
    
    public List<ViewFlowNodeInstence> getFlowNodeInstenceList(String typeId, String billid, String compId){
    	
    	return flowGlobalService.getFlowNodeInstenceList(typeId, billid, compId);
    }
    
    /**
	 * 获取审批历史数据 根据业务类型、业务ID
	 * @param flowInsteceId
	 * @return
	 */
	public List<ViewFlowNodeInstence> getHistoryByFlowInsteceId(String flowInsteceId){
		return flowGlobalService.getHistoryByFlowInsteceId(flowInsteceId);
	}
	// '苏照华', '余建文'
	
	/**
	 * 处理审批
	 * @param flowNodeInstence 这个是当前用户处理的节点
	 * @param turnUser
	 * @param moduecode
	 * @return
	 */
//	@Transactional
//	public String sendFlowTodo(ViewFlowNodeInstence currentFni, CusUsers turnUser, String moduecode){
//		try {
//			flowGlobalService.sendFlowTodo(currentFni, turnUser, moduecode);
//		} catch (FlowException e) {
//			
//			e.printStackTrace();
//		}
//		
//		return "";
//	}
	
//	@Transactional
//	public String sendFlowTodo(ASObject obj, MsPerson turnUser, String moduecode){
	
	@Transactional
	public String sendFlowTodo(String jsonFlowNodeInstence, String jsonTurnUser, String moduecode){
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		DataInteraction di = new DataInteraction();
		String msg="";
		boolean result=false;
		try {
			ViewFlowApproval currentFni = gson.fromJson(jsonFlowNodeInstence, ViewFlowApproval.class);
			MsPerson turnUser = gson.fromJson(jsonTurnUser, MsPerson.class);
			List<FlowNodeInstence> list = flowGlobalService.sendFlowTodo(currentFni, turnUser, moduecode);
			msg = gson.toJson(list);
			result=true;
		} catch (FlowException e) {
			msg = e.getMessage();
		}
		
		di.setResult(result);
		di.setMsg(msg);
		
		return gson.toJson(di);
	}
}
