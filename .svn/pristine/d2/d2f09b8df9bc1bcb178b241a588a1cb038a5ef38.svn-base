package com.sccl.framework.service.set;

import java.util.List;

import com.sccl.framework.entity.SetDetail;
import com.sccl.framework.entity.SetType;

public interface ISetService {
	
	public String addSetType(String setTypeJson);
	public String updateSetType(String setTypeJson);
	public String deleteSetTypeById(String id);
	public String findAllSetTypes();
	public String addSetDetail(String setDetailJson);
	public String updateSetDetail(String setDetailJson);
	public String deleteSetDetailById(String id);
	public String findSetDetailsByTypeId(String id);
	public List<SetType> findAllSetType();
	public List<SetDetail> findAllSetDetail();
	public String findAllSetTree();
	
	public String addCustomcolumns(String columnsJson);
	public String findCustomcolumns(String moduleName, Integer userId);
}
