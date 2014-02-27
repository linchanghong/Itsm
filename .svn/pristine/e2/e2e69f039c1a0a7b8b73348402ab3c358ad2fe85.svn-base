package com.sccl.flow.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sccl.flow.service.IFlowDataService;
import com.sccl.flow.vo.ViewPerson;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.utils.Tools;

@Component("flowData")
@RemotingDestination
public class FlowData {
	public IFlowDataService flowDataService;

	public IFlowDataService getFlowDataService() {
		return flowDataService;
	}

	@Resource(name = "flowDataService")
	public void setFlowDataService(IFlowDataService flowDataService) {
		this.flowDataService = flowDataService;
	}
	
	/**
	 * 根据条件查询公司
	 * @param where
	 * @param orderby
	 * @return
	 */
	public String GetAllFromViewEffective(String where, String orderby){

		List<Organization> list = flowDataService.GetAllFromViewEffective(where, orderby);
		Gson gson = Tools.getGson();
		String json = gson.toJson(list);
        return json;
    }
	
	/**
	 * 根据条件查询部门
	 * @param where
	 * @param orderby
	 * @return
	 */
	public String getAllDepartmentEffective(String where, String orderby){

//		List<CusDepartment> list = flowDataService.getAllDepartmentEffective(where, orderby);
//		TargetStrategy ts = new TargetStrategy(CusDepartment.class);
		List<Organization> list = flowDataService.getAllDepartmentEffective(where, orderby);
//		TargetStrategy ts = new TargetStrategy(Organization.class);
//		ts.setFields(new String[]{"DeptId", "DeptCode", "DeptName", "PinyinCode", "ParentCode", "DeptLevel", "Effective", "LevelCode", "RealCompId"});
//		ts.setReverse(true);
//		Gson gson = new GsonBuilder().setExclusionStrategies(ts).registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();  
		Gson gson = Tools.getGson();
		String json = gson.toJson(list);
        return json;
    }
	
	/**
	 * 根据条件查询用户
	 * @param where
	 * @param orderby
	 * @return
	 */
	public String getCusUsers(String where, String orderby){
//		List<CusUsers> list = flowDataService.getCusUsers(where, orderby);
//		TargetStrategy ts = new TargetStrategy(CusUsers.class);
		
		List<ViewPerson> list = flowDataService.getCusUsers(where, orderby);
//		TargetStrategy ts = new TargetStrategy(MsPerson.class);
//		
//		ts.setFields(new String[]{"UserId", "UserName", "UserCode", "Password", "RoleTable", "Effective",
//				"CreateTime", "State", "Type", "AllocationRoles", "VersionId", "UpdateTime", "ManageCustLines", "ManageProfessionalLines",
//				"CostViewDepts", "CODE", "InforLevel", "IsCheck", "ViewControl", "RptRole", "DeptCode", "EmossUserId", "CustomerViewDepts",
//				"PhoneNumber", "UserCard", "Birthday", "Duty", "pwdAways", "UserProperty", "RoleTableIT", "IsViewIT", "Sex"});
//		ts.setReverse(true);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//.setExclusionStrategies(ts).registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create();
		String json = gson.toJson(list);
		return json;
	}
	
	/**
	 * 查询用户记录条数
	 * @param where
	 * @param orderby
	 * @return
	 */
	public String getAllCount(String where){
		String count = String.valueOf(flowDataService.getAllCount(where));
		return count;
	}
}
