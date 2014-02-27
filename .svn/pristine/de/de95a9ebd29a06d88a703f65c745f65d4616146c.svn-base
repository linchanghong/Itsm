package com.sccl.framework.service.constant;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.common.utils.ExportAsExcel;
import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.ConstType;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.vo.ConstTree;

@Component
public class ConstService implements IConstService {
	
	private DataManager dataManager;
	private ExportAsExcel exportAsExcel;
	
	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Resource
	public void setExportAsExcel(ExportAsExcel exportAsExcle) {
		this.exportAsExcel = exportAsExcle;
	}

	public ExportAsExcel getExportAsExcel() {
		return exportAsExcel;
	}

	@Log
	@Transactional
	public String addConstType(String constTypeJson) {
		try {
			Gson gson = new Gson(); 
			ConstType constType = dataManager.add(gson.fromJson(constTypeJson, ConstType.class));
			return gson.toJson(constType);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String addConstDetail(String constDetailJson) {
		try {
			Gson gson = new Gson(); 
			ConstDetail constDetail = dataManager.add(gson.fromJson(constDetailJson, ConstDetail.class));
			return gson.toJson(constDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String addConstDetails(String constDetailsJson) {
		try {
			Type userType = new TypeToken<List<ConstDetail>>(){}.getType();
			List<ConstDetail> constDetails = new Gson().fromJson(constDetailsJson, userType);
			Iterator<ConstDetail> iter = constDetails.iterator();
			while (iter.hasNext()) {
				ConstDetail constDetail = iter.next();
				dataManager.add(constDetail);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateConstType(String constTypeJson) {
		try {
			dataManager.update(new Gson().fromJson(constTypeJson, ConstType.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateConstTypes(String constTypesJson) {
		try {
			Type userType = new TypeToken<List<ConstType>>(){}.getType();
			List<ConstType> constTypes = new Gson().fromJson(constTypesJson, userType);
			Iterator<ConstType> iter = constTypes.iterator();
			while (iter.hasNext()) {
				ConstType constType = iter.next();
				dataManager.update(constType);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateConstDetail(String constDetailJson) {
		try {
			dataManager.update(new Gson().fromJson(constDetailJson, ConstDetail.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateConstDetails(String constDetailsJson) {
		try {
			Type userType = new TypeToken<List<ConstDetail>>(){}.getType();
			List<ConstDetail> constDetails = new Gson().fromJson(constDetailsJson, userType);
			Iterator<ConstDetail> iter = constDetails.iterator();
			while (iter.hasNext()) {
				ConstDetail constDetail = iter.next();
				dataManager.update(constDetail);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String deleteConstDetailById(String constDetailId) {
		try {
			dataManager.deleteById(ConstDetail.class, Integer.parseInt(constDetailId));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String deleteConstDetailByIds(String constDetailIds) {
		try {
			Integer[] ids = new Gson().fromJson(constDetailIds, Integer[].class);
			dataManager.deleteByIdBatch(ConstDetail.class, ids);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String deleteConstByTypeId(String constTypeId) {
		try {
			dataManager.createQuery("DELETE  FROM ConstDetail d where d.constType.constTypeId=:constTypeId").setParameter("constTypeId", Integer.parseInt(constTypeId)).executeUpdate();
			dataManager.createQuery("DELETE  FROM ConstType c where c.constTypeId=:constTypeId").setParameter("constTypeId", Integer.parseInt(constTypeId)).executeUpdate();
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public List<ConstType> findAllConstType() {
		return dataManager.findAll(ConstType.class);
	}

	@Log
	public List<ConstDetail> findAllConstDetail() {
		return dataManager.findAll(ConstDetail.class);
	}
	
	public String findAllConstTree() {
		try {
			List<ConstType> constTypes = findAllConstType();
			List<ConstDetail> constDetails = findAllConstDetail();
			List<ConstTree> constTrees = new ArrayList<ConstTree>();
			Iterator<ConstType> iter = constTypes.iterator();
			while (iter.hasNext()) {
				ConstType constType = iter.next();
				ConstTree constTree = StaticMethods.toConstTree(constType);
				List<ConstDetail> children = new ArrayList<ConstDetail>();
				Iterator<ConstDetail> iter1 = constDetails.iterator();
				while (iter1.hasNext()) {
					ConstDetail constDetail = iter1.next();
					if(constDetail.getConstType().getId().equals(constType.getId())) {
						children.add(constDetail);
					}
				}
				constTree.setChildren(children);
				constTrees.add(constTree);
			}
			return new Gson().toJson(constTrees);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}   
	}
	
	@SuppressWarnings("rawtypes")
	public String exportByData (List dataArr, String[][] headerArray, String templateFileName){
		return exportAsExcel.exportByData(dataArr, headerArray, templateFileName);
	}

	public String exportByMethod (String methodObj, String[][] headerArray, String templateFileName) {
		return exportAsExcel.exportByMethod(methodObj, headerArray, templateFileName);
	}
	
}
