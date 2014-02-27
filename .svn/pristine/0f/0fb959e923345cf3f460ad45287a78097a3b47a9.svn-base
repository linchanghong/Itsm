package com.sccl.flow.api;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;
import com.sccl.flow.common.FlowTools;
import com.sccl.flow.service.ITestFlowService;
import com.sccl.flow.vo.DataInteraction;
import com.sccl.framework.utils.Tools;

/**
 * 流程测试
 * @author Administrator
 *
 */
@Component("testFlow")
@RemotingDestination
public class TestFlow {
	private ITestFlowService testFlowService;

	public ITestFlowService getTestFlowService() {
		return testFlowService;
	}

	@Resource(name = "testFlowService")
	public void setTestFlowService(ITestFlowService testFlowService) {
		this.testFlowService = testFlowService;
	}
	
	/**
	 * 查询全部
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String findAll(){
		Gson gson = Tools.getGson();
		List list = testFlowService.findAll();
		String json=gson.toJson(list);
		return json;
	}
	
	/**
	 * 添加
	 * @param testFlow
	 * @param user
	 * @return -2保存数据失败 -1 后台异常 0请建Model 1成功
	 */
	@Transactional
	public String add(String json){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		List<StringMap<Object>> list = gson.fromJson(json, new TypeToken<List<StringMap<Object>>>(){}.getType());
		int type = -2;
		
		com.sccl.flow.entity.TestFlow testFlow = null;
//		CusUsers users = null;
		try {
			testFlow = FlowTools.mapToObject(list.get(0), com.sccl.flow.entity.TestFlow.class);
//			users = FlowTools.mapToObject(list.get(1), CusUsers.class);
			String compId = String.valueOf(list.get(1).get("CompId"));
			String personId = String.valueOf(list.get(1).get("UserId"));
			type = testFlowService.add(testFlow, compId, personId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		DataInteraction di = new DataInteraction();
		di.setType(type);
		return gson.toJson(di);
	}
	
	public String getFlowData(long id){
		com.sccl.flow.entity.TestFlow testFlow = testFlowService.getFlowData(id);
		Gson gson = Tools.getGson();
 		return gson.toJson(testFlow);
	}
	
	
	
	/**
	 * 删除
	 * @param json
	 * @return
	 */
	@Transactional
	public String delete(String json){
		
		return "";
	}
	
	/**
	 * 更新
	 * @param json
	 * @return
	 */
	@Transactional
	public String update(String json){
		
		return "";
	}
}