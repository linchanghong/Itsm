package com.sccl.flow.service;

import java.util.List;

import com.sccl.flow.vo.ViewPerson;
import com.sccl.framework.entity.Organization;

public interface IFlowDataService {
	/**
	 * 根据条件查询公司
	 * @param where
	 * @param orderby
	 * @return
	 */
	public List<Organization> GetAllFromViewEffective(String where, String orderby);
	public List<Organization> getAllDepartmentEffective(String where, String orderby);
	public List<ViewPerson> getCusUsers(String where, String orderby);
	public int getAllCount(String where);
}
