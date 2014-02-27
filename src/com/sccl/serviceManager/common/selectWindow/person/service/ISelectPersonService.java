package com.sccl.serviceManager.common.selectWindow.person.service;

import java.util.List;

public interface ISelectPersonService {
	
	/**
	 * 根据部门Id查询用户
	 * @param companyid
	 * @param depid
	 * @return
	 */
	public List<Object> findPersons(String depid, int first, int size, boolean isCount);
	public List<Object> findPersonsSimplify(String depid, int first, int size, boolean isCount);
	/**
	 * 根据用户输入模糊查询
	 * @param where
	 * @return
	 */
	public List<Object> queryPersons(int companyid, String userName, int first, int size, boolean isCount);
	
	/**
	 * @author 张银祥
	 * 查询人员信息
	 * @param companyid
	 * @param userName
	 * @param first
	 * @param size
	 * @param isCount
	 * @return
	 */
  public List<Object> queryPersonsSimplify(String companyid, String userName, int first, int size,boolean isCount) ;
 
  /**@author  张银祥
  * 查询人员信息
  * @param companyid
  * @param userName
  * @param personCode
  * @param deptId
  * @param personType
  * @param first
  * @param size
  * @param isCount
  * @return
  */
  public List<Object> queryPersonsSimplifyBatch(String companyid, String userName,String personCode,Integer deptId,Integer jobId, int first, int size,
      boolean isCount);
}
