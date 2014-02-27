package com.sccl.framework.service.log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.DataManager;
import com.sccl.framework.entity.MsLog;
import com.sccl.framework.utils.Tools;

@Component
public class LogService implements ILogService {
	
	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public void addLog(MsLog msLog) {
		try {
			dataManager.add(msLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String findAllLog() {
		return new Gson().toJson(dataManager.findAll(MsLog.class));
	}

	@Override
	public String findAllLogPage(int first, int max, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsLog> msLogs = dataManager.createNamedQuery("findAllLogOrdered", MsLog.class).setFirstResult(first).setMaxResults(max).getResultList();
			reList.add(0, msLogs);
			if(isCount) {
				Long count = dataManager.createQuery("select count(l) from MsLog l ", Long.class).getSingleResult();
				reList.add(1, count);
			}
			return new Gson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String findLogByUserCode(String userCode) {
		try {
			List<MsLog> msLogs = dataManager.createNamedQuery("findLogByUserCode", MsLog.class).setParameter("userCode", userCode).getResultList();
			return new Gson().toJson(msLogs);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String findLogByUserCodePage(String userCode, int first, int max, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsLog> msLogs = dataManager.createNamedQuery("findLogByUserCode", MsLog.class).setParameter("userCode", userCode).setFirstResult(first).setMaxResults(max).getResultList();
			reList.add(0, msLogs);
			if(isCount) {
				Long count = dataManager.createQuery("select count(l) from MsLog l where l.userCode=:userCode", Long.class).setParameter("userCode", userCode).getSingleResult();
				reList.add(1, count);
			}
			return new Gson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String findAllLogPageByCondition(String conditions, int first, int max, boolean isCount) {
		Type hashType = new TypeToken<HashMap<String, Object>>(){}.getType();
		Map<String, Object> conditionMap = Tools.getGson().fromJson(conditions, hashType); 
		
		String jpql = " from MsLog l where 1=1 ";
		if(conditionMap.containsKey("userCode") && conditionMap.get("userCode").toString().indexOf("-1") == -1) {
			jpql += " and l.userCode like '%" + conditionMap.get("userCode").toString() + "%' ";
		} 

		if(conditionMap.containsKey("className") && conditionMap.get("className").toString().indexOf("-1") == -1) {
			jpql += " and l.className like '%" + conditionMap.get("className").toString() + "%' ";
		} 

		if(conditionMap.containsKey("methodName") && conditionMap.get("methodName").toString().indexOf("-1") == -1) {
			jpql += " and l.methodName like '%" + conditionMap.get("methodName").toString() + "%' ";
		} 

		if(conditionMap.containsKey("userBehave") && conditionMap.get("userBehave").toString().indexOf("-1") == -1) {
			jpql += " and l.userBehave like '%" + conditionMap.get("userBehave").toString() + "%' ";
		} 

		if(conditionMap.containsKey("isSucceed") && conditionMap.get("isSucceed").toString().indexOf("-1") == -1) {
			jpql += " and l.isSucceed like '%" + conditionMap.get("isSucceed").toString() + "%' ";
		} 

		if(conditionMap.containsKey("ipAddress") && conditionMap.get("ipAddress").toString().indexOf("-1") == -1) {
			jpql += " and l.ipAddress like '%" + conditionMap.get("ipAddress").toString() + "%' ";
		} 

		if(conditionMap.containsKey("theContent") && conditionMap.get("theContent").toString().indexOf("-1") == -1) {
			jpql += " and l.theContent like '%" + conditionMap.get("theContent").toString() + "%' ";
		} 

		if(conditionMap.containsKey("parameters") && conditionMap.get("parameters").toString().indexOf("-1") == -1) {
			jpql += " and l.theContent like '%" + conditionMap.get("theContent").toString() + "%' ";
		}
		
		if(conditionMap.containsKey("columns") && conditionMap.get("columns").toString().indexOf("-1") == -1) {
			jpql += " order by l." + conditionMap.get("columns").toString();
		} else {
			jpql += " order by l.id ";
		}
		
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsLog> msLogs = dataManager.createQuery("select l " + jpql, MsLog.class).setFirstResult(first).setMaxResults(max).getResultList();
			reList.add(0, msLogs);
			if(isCount) {
				Long count = dataManager.createQuery("select count(l) " + jpql, Long.class).getSingleResult();
				reList.add(1, count);
			}
			return new Gson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
