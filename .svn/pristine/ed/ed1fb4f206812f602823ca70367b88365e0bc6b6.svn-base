package com.sccl.framework.service.constant;

import java.util.List;

import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.ConstType;


public interface IConstService {

	public String addConstType(String constTypeJson);
	public String addConstDetail(String constDetailJson);
	public String addConstDetails(String constDetailsJson);
	public String updateConstType(String constTypeJson);
	public String updateConstTypes(String constTypesJson);
	public String updateConstDetail(String constDetailJson);
	public String updateConstDetails(String constDetailsJson);
	public String deleteConstDetailById(String constDetailId);
	public String deleteConstDetailByIds(String constDetailIds);
	public String deleteConstByTypeId(String constTypeId);
	public List<ConstType> findAllConstType();
	public List<ConstDetail> findAllConstDetail();
	public String findAllConstTree();
	
	@SuppressWarnings("rawtypes")
	public String exportByData (List dataArr, String[][] headerArray, String templateFileName);

	public String exportByMethod (String methodObj, String[][] headerArray, String templateFileName);
	
}
