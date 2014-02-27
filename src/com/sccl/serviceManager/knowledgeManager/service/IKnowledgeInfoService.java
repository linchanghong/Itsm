package com.sccl.serviceManager.knowledgeManager.service;

import com.sccl.serviceManager.knowledgeManager.entity.KnowledgeInfo;


public interface IKnowledgeInfoService {
	
	public Long addKnowledgeInfo(KnowledgeInfo knowledgeInfo,String compId, String personId);
	public String addKnowledgeInfos(String knowledgeInfosJson);
	public Long updateKnowledgeInfo(String knowledgeInfoJson);
	public String updateKnowledgeInfos(String knowledgeInfosJson);
	public String deleteKnowledgeInfoById(String knowledgeInfoId);
	public String deleteKnowledgeInfoByIds(String knowledgeInfoIds);
	public String findKnowledgeInfoById(String knowledgeInfoId);
	public String findKnowledgeInfoByUserId(String userId);
//	public String findAllKnowledgeInfoPage(String knowledgeInfoCondtion, int first,int size, boolean isCount);
//	public List<KnowledgeInfo> findAllKnowledgeInfo();
	public String deleteKnowledgeInfo(String knowledgeInfoId);
	public String examineKnowledgeInfo(String knowledgeInfoId,String examine);
	public String findAllKnowledgeInfoPage(String sqlWhere,int first,int size, boolean isCount);
	
}
