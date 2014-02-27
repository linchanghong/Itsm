package com.sccl.serviceManager.common.selectWindow.person.service;

import java.util.List;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.SystemStatic;
import com.sccl.framework.entity.MsPerson;
import com.sccl.framework.entity.Organization;
import com.sccl.serviceManager.common.selectWindow.person.dto.PsnInfoDTO;
import com.sccl.serviceManager.common.selectWindow.person.dto.SimplifyPsnInfoDTO;

@SuppressWarnings("all")
@Component("selectPersonService")
public class SelectPersonService implements ISelectPersonService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	/**
	 * 根据部门Id查询用户
	 * 
	 * @param companyid
	 * @param depid
	 * @return
	 */
	public List<Object> findPersons(String depid, int first, int size,
			boolean isCount) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		if(depid.equals(SystemStatic.COMPANY_ID)){
			sql = "select a.*, b.org_code, b.org_name, b.org_type, b.parent_id, b.company_id, b.parent_path from ms_person a, organization b where a.org_id=b.org_id";
			
		}else{
			String ids = findAllSonDepatment(depid);
			sql = "select a.*, b.org_code, b.org_name, b.org_type, b.parent_id, b.company_id, b.parent_path from ms_person a, organization b where a.org_id=b.org_id and b.org_id in(" + ids + ")";
			
		}
		
		List<MsPerson> listPerson = (List<MsPerson>) dataManager.nativeQuery(sql, PsnInfoDTO.class, first, size);
//		if(first+size>listPerson.size()){
//
//			list.add(listPerson.subList(first, listPerson.size()));
//		}else{
//
//			list.add(listPerson.subList(first, first+size));
//		}
		list.add(listPerson);
		if (isCount) {
			int count = dataManager.getCountBySql(sql);
			list.add(1, count);
		}
		return list;
	}
	public List<Object> findPersonsSimplify(String depid, int first, int size,
      boolean isCount) {
    List<Object> list = new ArrayList<Object>();
    String ids = findAllSonDepatment(depid);
    String sql = "select p.psninfo_id          personId,"+
    "p.psncode             personCode,"+
    "p.psnname             personName,"+
    "o.corp_id             orgId,"+
    "o.unitname            orgName,"+
    "od.dept_id            deptId,"+
    "od.deptname           deptName,"+
    "cd.const_detail_name  jobLevelName,"+
    "cd1.const_detail_name jobTypeName "+
"from psn_info p "+
"left join ms_person m "+
 "on p.psninfo_id = m.person_id "+
"left join org_unit o "+
 "on p.corp_id = o.corp_id "+
"left join org_dept od "+
 "on p.dept_id = od.dept_id "+
"left join const_detail cd "+
 "on p.job_level = cd.com_const_detail_id "+
" join const_detail cd1 "+
 "on p.job_type = cd1.com_const_detail_id" +
 " where od.dept_id in(" + ids + ")";
    List<MsPerson> listPerson = (List<MsPerson>) dataManager.nativeQuery(
        sql, SimplifyPsnInfoDTO.class);
    if(first+size>listPerson.size()){

      list.add(listPerson.subList(first, listPerson.size()));
    }else{

      list.add(listPerson.subList(first, first+size));
    }
    if (isCount) {
      int count = dataManager.getCountBySql(sql);
      list.add(1, count);
    }
    return list;
  }

	/**
	 * 查找全部下属部门ID
	 * 
	 * @param depid
	 * @return
	 */
	private String findAllSonDepatment(String depid) {
		String sql = "select * from organization t start with t.org_id='"
				+ depid + "' connect by t.parent_id = prior t.org_id";
		List<Organization> list = (List<Organization>) dataManager.nativeQuery(
				sql, Organization.class);

		StringBuilder ids = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			Organization org = list.get(i);
			Integer id = org.getId();
			ids.append(id + ",");
		}

		String tempIds = ids.toString().toString();
		if (tempIds.endsWith(",")) {
			int index = tempIds.lastIndexOf(",");
			ids.deleteCharAt(index);
		}

		return ids.toString();
	}

	/**
	 * 根据用户输入模糊查询
	 * 
	 * @param where
	 * @return
	 */
	public List<Object> queryPersons(int companyid, String userName, int first, int size,
			boolean isCount) {

		List<Object> list = new ArrayList<Object>();
		String sql = "select a.*, b.org_code, b.org_name, b.org_type, b.parent_id, b.company_id, b.parent_path from ms_person a, organization b where a.org_id=b.org_id";
		if (null != userName && !("").equals(userName.trim())) {
			sql += " and a.person_name like '%" + userName + "%'";
		}
		List<MsPerson> listPerson = (List<MsPerson>) dataManager.nativeQuery(sql, PsnInfoDTO.class, first, (first+size));
		list.add(listPerson);
		if (isCount) {
			int count = dataManager.getCountBySql(sql);
			list.add(count);
		}
		return list;
	}
	
	
	/**
   * 根据用户输入模糊查询
   * 
   * @param where
   * @return
   */
  public List<Object> queryPersonsSimplify(String companyid, String userName, int first, int size,
      boolean isCount) {

    List<Object> list = new ArrayList<Object>();
    String ids = findAllSonDepatment(companyid);
    String sql = "select p.psninfo_id          personId,"+
       "p.psncode             personCode,"+
       "p.psnname             personName,"+
       "o.corp_id             orgId,"+
       "o.unitname            orgName,"+
       "od.dept_id            deptId,"+
       "od.deptname           deptName,"+
       "cd.const_detail_name  jobLevelName,"+
       "cd1.const_detail_name jobTypeName "+
  "from psn_info p "+
  "left join ms_person m "+
    "on p.psninfo_id = m.person_id "+
  "left join org_unit o "+
    "on p.corp_id = o.corp_id "+
  "left join org_dept od "+
    "on p.dept_id = od.dept_id "+
  "left join const_detail cd "+
    "on p.job_level = cd.com_const_detail_id "+
  " join const_detail cd1 "+
    "on p.job_type = cd1.com_const_detail_id  where o.corp_id ='"+companyid+"'";
    if (null != userName && !("").equals(userName.trim())) {
      sql += " and psnname like '%" + userName + "%'";
    }
    List<SimplifyPsnInfoDTO> listPerson = (List<SimplifyPsnInfoDTO>) dataManager.nativeQuery(
        sql, SimplifyPsnInfoDTO.class);
    if(first+size>listPerson.size()){
      list.add(listPerson.subList(first, listPerson.size()));
    }else{
      list.add(listPerson.subList(first, first+size));
    }
    if (isCount) {
      int count = dataManager.getCountBySql(sql);
      list.add(1, count);
    }
    return list;
  }
  
  /**
   * 根据用户输入模糊查询
   * 
   * @param where
   * @return
   */
  public List<Object> queryPersonsSimplifyBatch(String companyid, String userName,String personCode,Integer deptId,Integer jobId, int first, int size,
      boolean isCount) {

    List<Object> list = new ArrayList<Object>();
    String ids = findAllSonDepatment(companyid);
    String sql = "select p.psninfo_id          personId,"+
       "p.psncode             personCode,"+
       "p.psnname             personName,"+
       "o.corp_id             orgId,"+
       "o.unitname            orgName,"+
       "od.dept_id            deptId,"+
       "od.deptname           deptName,"+
       "cd.const_detail_name  jobLevelName,"+
       "cd1.const_detail_name jobTypeName "+
  "from psn_info p "+
  "left join ms_person m "+
    "on p.psninfo_id = m.person_id "+
  "left join org_unit o "+
    "on p.corp_id = o.corp_id "+
  "left join org_dept od "+
    "on p.dept_id = od.dept_id "+
  "left join const_detail cd "+
    "on p.job_level = cd.com_const_detail_id "+
  " join const_detail cd1 "+
    "on p.job_type = cd1.com_const_detail_id  where o.corp_id ='"+companyid+"'";
    if (null != userName && !("").equals(userName.trim())) {
      sql += " and psnname like '%" + userName + "%'";
    }
    if(personCode != null && !personCode.equals("")){
      sql += " and p.psncode like '%" + personCode + "%'";
    }
    if(deptId != null && deptId != 0){
      sql += " and od.dept_id =" + deptId ;
    }
    if(jobId != null && jobId != 0){
      sql += " and p.job_id =" + jobId ;
    }
    List<SimplifyPsnInfoDTO> listPerson = (List<SimplifyPsnInfoDTO>) dataManager.nativeQuery(
        sql, SimplifyPsnInfoDTO.class);
    if(first+size>listPerson.size()){
      list.add(listPerson.subList(first, listPerson.size()));
    }else{
      list.add(listPerson.subList(first, first+size));
    }
    if (isCount) {
      int count = dataManager.getCountBySql(sql);
      list.add(1, count);
    }
    return list;
  }
}
