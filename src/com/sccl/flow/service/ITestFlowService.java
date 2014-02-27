package com.sccl.flow.service;

import java.util.List;

import com.sccl.flow.entity.TestFlow;
import com.sccl.flow.vo.TestFlowAndState;
import com.sccl.framework.entity.MsUser;

public interface ITestFlowService {
	/**
	 * 查询
	 * @return
	 */
	public List<TestFlowAndState> findAll();
	/**
	 * 添加
	 * @param testFlow
	 * @param compId
	 * @param personId
	 * @return -2保存数据失败 -1 后台异常 0请建Model 1成功
	 */
	public int add(TestFlow testFlow, String compId, String personId);
	
	/**
	 * 根据ID查询数据
	 * @param id
	 * @return
	 */
	public TestFlow getFlowData(long id);
	
	public MsUser login(String userCode);
	
	public boolean isLogin(MsUser user);
}
