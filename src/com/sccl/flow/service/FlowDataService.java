package com.sccl.flow.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sccl.flow.vo.ViewPerson;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.SystemStatic;
import com.sccl.framework.entity.Organization;

@Component("flowDataService")
public class FlowDataService implements IFlowDataService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	/**
	 * 根据条件查询公司
	 * 
	 * @param where
	 * @param orderby
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Organization> GetAllFromViewEffective(String where,
			String orderby) {

		List<Organization> list = new ArrayList<Organization>();
		list = (List<Organization>) dataManager.createQuery(Organization.class)
				.queryWhere("org_type=1").asc("org_code").list();
		return list;
	}

	/**
	 * 查询部门
	 * 
	 * @param where
	 *            查询条件
	 * @param orderby
	 *            排序条件
	 * @return 部门信息
	 */
	@SuppressWarnings("unchecked")
	public List<Organization> getAllDepartmentEffective(String where,
			String orderby) {
		if (orderby == null || orderby.equals(""))
			orderby = "org_code asc";

//		List<Organization> list = new ArrayList<Organization>();
//		List<Organization> newList = new ArrayList<Organization>();
//
//		if (isAdmin == 1 && where.equals("510101")) {
//
//			list = (List<Organization>) dataManager.findAll(Organization.class);
//
//			return list;
//		}
		
//		return dataManager.createQuery(Organization.class)
//		.queryWhere("org_id='"+where+"' or company_id='"+where+"'").asc(orderby).list();
		
		String sql ="SELECT * FROM ORGANIZATION t START WITH org_id = '"+where+"' CONNECT BY parent_id = PRIOR org_id";
		if(SystemStatic.COMPANY_ID.equals(where)){
			sql="SELECT * FROM ORGANIZATION t where t.org_id='"+where+"' or company_id='"+where+"'";
		}
		
		return (List<Organization>)dataManager.nativeQuery(sql, Organization.class);
	}

	/**
	 * 查询用户
	 * 
	 * @param where
	 *            查询条件
	 * @param orderby
	 *            排序条件
	 * @return 用户信息
	 */
	@SuppressWarnings("unchecked")
	public List<ViewPerson> getCusUsers(String where, String orderby) {
		if (orderby == null || orderby.equals(""))
			orderby = "PERSON_ID ASC";

		String sql = "select * from View_Person";
		if (where != null && !where.equals(""))
			sql += " where " + where;

		if (orderby != null && !orderby.equals(""))
			sql += " order by " + orderby;

		return (List<ViewPerson>) dataManager
				.nativeQuery(sql, ViewPerson.class);
	}

	/**
	 * 记录条数
	 * 
	 * @param where
	 *            查询条件
	 * @return
	 */
	public int getAllCount(String where) {
		String sql = "select count(t.userid) count from cususers t where "
				+ where;
		return dataManager.getCountBySql(sql);
	}
}
