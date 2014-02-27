package com.sccl.flow.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sccl.flow.common.FlowTools;
import com.sccl.flow.entity.TestFlow;
import com.sccl.flow.vo.TestFlowAndState;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.utils.Uid;
import com.sccl.framework.entity.MsUser;

@Component("testFlowService")
public class TestFlowService implements ITestFlowService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	/**
	 * 查询全部
	 */
	@SuppressWarnings("unchecked")
	public List<TestFlowAndState> findAll(){
		
		String sql = "select a.*, b.flowId, b.flowState, c.person_name sendName, d.const_detail_name state"
			+" from TestFlow a, FlowInstence b, Ms_Person c, Const_Detail d"
			+" where a.id=b.billid and a.sendid=c.person_id and b.flowstate=d.com_const_detail_id"
			+" order by a.id desc";
		return (List<TestFlowAndState>)dataManager.nativeQuery(sql, TestFlowAndState.class);
	}
	
	/**
	 * 添加
	 * @param testFlow
	 * @param compId
	 * @param personId
	 * @return -2保存数据失败 -1 后台异常 0请建Model 1成功
	 */
	@Transactional
	public int add(TestFlow testFlow, String compId, String personId){
		try {
			FlowTools tools = FlowTools.getInstence();
			tools.setDataManager(dataManager);
			Long oprtTypeID = Long.valueOf("4");
			Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
			if(flowModelId==-1){
				return -1;
			}else if(flowModelId==0){
				return 0;
			}else{
				dataManager.add(testFlow);
				Long billID = Long.valueOf(testFlow.getId());
				String flowTitle = "测试流程："+testFlow.getName();
				tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	@Override
	public TestFlow getFlowData(long id) {
		
		return (TestFlow)dataManager.findById(TestFlow.class, id);
	}
	
	public MsUser login(String userCode){
		MsUser user = (MsUser)dataManager.createQuery(MsUser.class).queryWhere("user_Code='"+"'").single();
		if(user!=null){
			user.setLoginUid(Uid.getUidUtil().createUID());
			dataManager.update(user);
		}
		return user;
	}
	
	public boolean isLogin(MsUser user){
		
		MsUser u = (MsUser)dataManager.findById(MsUser.class, user.getId());
		if(u!=null && u.getLoginUid().equals(user.getLoginUid())){
			return false;
		}
		return true;
	}
}
