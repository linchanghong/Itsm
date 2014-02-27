package com.sccl.serviceManager.common.selectWindow.person.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccl.framework.entity.MsPerson;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.utils.Tools;
import com.sccl.serviceManager.common.selectWindow.person.service.ISelectPersonService;

@SuppressWarnings("all")
@Component("selectPersonAPI")
@RemotingDestination
public class SelectPersonAPI {
	
	private ISelectPersonService selectPersonService;
	
	public ISelectPersonService getSelectPersonService() {
		return selectPersonService;
	}
	
	@Resource(name = "selectPersonService")
	public void setSelectPersonService(ISelectPersonService selectPersonService) {
		this.selectPersonService = selectPersonService;
	}
	
	/**
	 * 根据部门Id查询用户
	 * @param companyid
	 * @param depid
	 * @return
	 */
	public String findPersons(String depid, int first, int size, boolean isCount){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<Object> list = selectPersonService.findPersons(depid, first, size, isCount);
		String json = gson.toJson(list);
		return json;
	}
	public String findPersonsSimplify(String depid, int first, int size, boolean isCount){
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    List<Object> list = selectPersonService.findPersonsSimplify(depid, first, size, isCount);
    String json = gson.toJson(list);
    return json;
  }
	
	/**
	 * 根据用户输入模糊查询
	 * @param where
	 * @return
	 */
	public String queryPersons(int companyid, String userName, int first, int size, boolean isCount){
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<Object> list = selectPersonService.queryPersons(companyid, userName, first, size, isCount);
		String json = gson.toJson(list);
		return json;
	}
	/**
   * 根据用户输入模糊查询
   * @param where
   * @return
   */
  public String queryPersonsSimplify(String companyid, String userName, int first, int size, boolean isCount){
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    List<Object> list = selectPersonService.queryPersonsSimplify(companyid, userName, first, size, isCount);
    String json = gson.toJson(list);
    return json;
  }
  
  /**
   * 根据用户输入模糊查询
   * @param where
   * @return
   */
  public String queryPersonsSimplifyBatch(String companyid, String userName,String personCode,Integer deptId,Integer jobId, int first, int size,
      boolean isCount){
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    List<Object> list = selectPersonService.queryPersonsSimplifyBatch(companyid, userName, personCode, deptId, jobId, first, size, isCount);
    String json = gson.toJson(list);
    return json;
  }
}
