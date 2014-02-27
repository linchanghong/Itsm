package com.sccl.itsm.assessManager.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.itsm.assessManager.entity.Assess;

@Component("assessService")
public class AssessService implements IAssessService{

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	@Log
	@Transactional
	public Long addAssess(Assess assess) {
		try{
			dataManager.add(assess);
			return assess.getId();
		} catch (Exception e) {
			
			e.printStackTrace();
			return (long) -2;
		}
	}

	@Transactional
	@Log
	public Long addAssesses(String assessJsons) {
		try {
			Type AssessType = new TypeToken<List<Assess>>() {}.getType();
			List<Assess> assesses = new Gson().fromJson(assessJsons, AssessType);
			Iterator<Assess> iter = assesses.iterator();
			while (iter.hasNext()) {
				Assess assess = iter.next();
				dataManager.add(assess);
			}
			return (long) 1;
		} catch (Exception e) {
			e.printStackTrace();
			return (long) -2;
		}
	}

	@Transactional
	@Log
	public Long updateAssess(Assess assess) {
		try{
			dataManager.update(assess);
			return assess.getId();
		} catch (Exception e) {
			
			e.printStackTrace();
			return (long) -2;
		}
	}

	@Transactional
	@Log
	public Long updateAssesses(String assessJsons) {
		try {
			Type AssessType = new TypeToken<List<Assess>>(){}.getType();
			Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			List<Assess> assesses = gson.fromJson(assessJsons, AssessType);
			Iterator<Assess> iter = assesses.iterator();
			while (iter.hasNext()) {
				Assess assess = iter.next();
				dataManager.update(assess);
			}
			return (long) 1;
		} catch (Exception e) {
			e.printStackTrace();
			return (long) -2;
		}
	}

	@Transactional
	@Log
	public String deleteAssessById(String assessId) {
		try {
			dataManager.deleteById(Assess.class, Long.parseLong(assessId));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String deleteAssessByIds(String assessIds) {
		try {
			Long[] ids = new Gson().fromJson(assessIds, Long[].class);
			dataManager.deleteByIdBatch(Assess.class, ids);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@SuppressWarnings("unchecked")
	@Log
	public String findAllAssessPage(String sqlWhere, int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			String whereStr=" where 1=1";
			if(sqlWhere!=null && !sqlWhere.equals("")){
				whereStr+=sqlWhere;
			}
			List<Assess> assesses = dataManager.createNativeQuery("select p.* from t_assess p "+whereStr+" order by p.assess_id", Assess.class).setFirstResult(first).setMaxResults(size).getResultList();
			
			reList.add(0, assesses);
			if(isCount) {
				reList.add(1, assesses.size());
			}
			
			return StaticMethods.getDateGson().toJson(reList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
