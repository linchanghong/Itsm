package com.sccl.serviceManager.bugManager.flow.service;

import java.math.BigInteger;
import java.util.List;

import com.sccl.flow.common.FlowException;
import com.sccl.flow.entity.FlowNodeInstence;
import com.sccl.flow.vo.ViewFlowApproval;
import com.sccl.flow.vo.ViewFlowNodeInstence;
import com.sccl.framework.entity.MsPerson;


public interface IBugFlowGlobalService {
	/**
	 * 呈报
	 * @param user 操作人
	 * @param sendId 发起人ID
	 * @param flowTypeId 流程业务ID
	 * @param billid 单据ID
	 * @return
	 */
	public List<FlowNodeInstence> startFlowInstence(MsPerson user, Long sendId, Long flowTypeId,
			BigInteger billid) throws FlowException;
	
	public int getAllCount(String wherestr);
	
	@SuppressWarnings("unchecked")
	public List getPageData(int pageIndex, int pageSize, String wherestr, String orderBy);
	
	/**
     * 该流程的所有历史审批记录
     * @param typeid
     * @param billid
     * @return
     */
	public List<ViewFlowNodeInstence> getHistory(String typeid, String billid);
	
	public List<ViewFlowNodeInstence> getFlowNodeInstenceList(String typeId, String billid, String compId);
	
	/**
	 * 获取审批历史数据 根据业务类型、业务ID
	 * @param flowInsteceId
	 * @return
	 */
	public List<ViewFlowNodeInstence> getHistoryByFlowInsteceId(String flowInsteceId);
	
	/**
	 * 处理审批
	 * @param currentFni 这个是当前用户处理的节点
	 * @param turnUser
	 * @param moduecode
	 * @return
	 */
	public List<FlowNodeInstence> sendFlowTodo(ViewFlowApproval currentFni, MsPerson turnUser, String moduecode) throws FlowException;
}
