package com.sccl.flow.vo;

import com.sccl.framework.entity.QueryEntity;


public class RoleAndNodeModel extends QueryEntity{
	private Long NodeID;
    private String NodeName;
    private Integer NodeAttr;
    private String NodeNumber;
    private Long FlowID;
    private String ModuleCode;
    private String NodeMark;
    private Double PointX;
    private Double PointY;
    private String NextID;
    private Long PreviewID;
    private String ApposeNodeIDs;
    private Integer DealWithDay;
    private Boolean IsNodeOneself;
    private Boolean IsNeedCheckDate;
    
    private String FlowRoleID;
    private String FlowRoleName;
    
	public Long getNodeID() {
		return NodeID;
	}
	public void setNodeID(Long nodeID) {
		NodeID = nodeID;
	}
	public String getNodeName() {
		return NodeName;
	}
	public void setNodeName(String nodeName) {
		NodeName = nodeName;
	}
	public Integer getNodeAttr() {
		return NodeAttr;
	}
	public void setNodeAttr(Integer nodeAttr) {
		NodeAttr = nodeAttr;
	}
	public String getNodeNumber() {
		return NodeNumber;
	}
	public void setNodeNumber(String nodeNumber) {
		NodeNumber = nodeNumber;
	}
	public Long getFlowID() {
		return FlowID;
	}
	public void setFlowID(Long flowID) {
		FlowID = flowID;
	}
	public String getModuleCode() {
		return ModuleCode;
	}
	public void setModuleCode(String moduleCode) {
		ModuleCode = moduleCode;
	}
	public String getNodeMark() {
		return NodeMark;
	}
	public void setNodeMark(String nodeMark) {
		NodeMark = nodeMark;
	}
	public Double getPointX() {
		return PointX;
	}
	public void setPointX(Double pointX) {
		PointX = pointX;
	}
	public Double getPointY() {
		return PointY;
	}
	public void setPointY(Double pointY) {
		PointY = pointY;
	}
	public String getNextID() {
		return NextID;
	}
	public void setNextID(String nextID) {
		NextID = nextID;
	}
	public Long getPreviewID() {
		return PreviewID;
	}
	public void setPreviewID(Long previewID) {
		PreviewID = previewID;
	}
	public String getApposeNodeIDs() {
		return ApposeNodeIDs;
	}
	public void setApposeNodeIDs(String apposeNodeIDs) {
		ApposeNodeIDs = apposeNodeIDs;
	}
	public Integer getDealWithDay() {
		return DealWithDay;
	}
	public void setDealWithDay(Integer dealWithDay) {
		DealWithDay = dealWithDay;
	}
	public Boolean getIsNodeOneself() {
		return IsNodeOneself;
	}
	public void setIsNodeOneself(Boolean isNodeOneself) {
		IsNodeOneself = isNodeOneself;
	}
	public Boolean getIsNeedCheckDate() {
		return IsNeedCheckDate;
	}
	public void setIsNeedCheckDate(Boolean isNeedCheckDate) {
		IsNeedCheckDate = isNeedCheckDate;
	}
	public String getFlowRoleID() {
		return FlowRoleID;
	}
	public void setFlowRoleID(String flowRoleID) {
		FlowRoleID = flowRoleID;
	}
	public String getFlowRoleName() {
		return FlowRoleName;
	}
	public void setFlowRoleName(String flowRoleName) {
		FlowRoleName = flowRoleName;
	}
    
}
