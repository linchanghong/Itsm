package com.sccl.flow.common;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Date;

import javax.annotation.Resource;

import com.sccl.flow.entity.FlowInstence;
import com.sccl.flow.entity.FlowModel;
import com.sccl.framework.DataManager;

/**
 * 流程工具类
 * @author Administrator
 *
 */
public class FlowTools {
	private static FlowTools tools;
	
	/**
	 * 单位编制
	 */
	public static final String UNIT_PREPARATION = "1";				//单位编制
	
	/**
	 * 部门变更
	 */
	public static final String DEPARTMENT_CHANGE = "2";			//部门变更
	
	/**
	 * 培训预算
	 */
	public static final String TRAINING_CHANGE = "4";			//培训预算

	public static final String PSNDEPTCHG="5";//入职管理
	
	public static final String TRAINING_PLAN="13";//培训计划
	
	public static final String TRAINING_REIM = "23";//培训报账
	
	/**
	 * 退休返聘
	 */
	public static final String RETIRE_RECALL="15";//退休返聘
	/**
	 * 计划外申请
	 */
	public static final String PX_UnscheduledReq = "3";			//计划外申请
	
	/**
	 * 借调交流
	 */
	public static final String PSN_PersonalChange = "14";			//借调交流
	/**
	 * 人事转正
	 */
	public static final String PSN_PersonalChange_rszz = "16";			//人事转正
	
	/**
	 * 人员调配管理
	 */
	public static final String Psn_PersonalChange_rydpgl = "17";			//人员调配管理
	/**
	 * 人员死亡
	 */
	public static final String PSN_DIED = "18";			//人员死亡
	
	/**
	 * 人员退休
	 */
	public static final String PSN_RETIRE = "19";			//人员退休
	/**
	 * 离职管理
	 */
	public static final String PSN_PersonalChange_lzgl = "20";	//离职管理
	
	/**
	 * 人员变动类型
	 */
	public static final int Psn_PersonalChange_Type = 1271;	//人员变动类型
	/**
	 * 人员变动类型
	 */
	public static final int Psn_Personal_Status = 3;	//人员状态
	
	/**
   * 岗位晋级
   */
  public static final int Psn_Job_Promotion_Type = 21;
  /**
   * 人员调动
   */
  public static final int Psn_Transfer_Type = 22;
  /**
   * 人员调动
   */
  public static final int Flow_Type_id_138 = 138;
	
	
	public static FlowTools getInstence() {
		if (tools == null) {
			tools= new FlowTools();
		}
		
		return tools;
	}
	
	private DataManager dataManager;
	
	
	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	/**
	 * 保存FlowInstence
	 * @param oprtTypeID Type id
	 * @param flowModelId Mode id
	 * @param billID 数据ID
	 * @param user 发起人
	 * @param flowTitle 显示名
	 */
	public void savaFlowInstence(Long oprtTypeID, Long flowModelId, Long billID, String personId, String flowTitle){
		FlowInstence instence = new FlowInstence();

	    
//		instence.setFlowId(Long.valueOf(0));
	    instence.setOprtTypeID(Long.valueOf(oprtTypeID));
	    instence.setFlowModelId(flowModelId);
		instence.setBillID(Long.valueOf(billID));
		instence.setFlowState(42);	//未进入流程
		instence.setSenderId(Long.valueOf(personId));
		instence.setSendTime(new Date());
		instence.setFlowTitle(flowTitle);
//		instence.setOther1("");
//		instence.setOther2("");
//		instence.setCustomerName("");
//		instence.setSubOrBgnName("");
//		instence.setBusinessMoney(Double.valueOf(0));
//		instence.setFlowDeptId(Long.valueOf(0));
		
		dataManager.add(instence);
	}
	
	/**
	 * 根据公司的type查询现在使用的Model
	 * @param oprtTypeId
	 * @param compId
	 * @return
	 */
	public Long getFlowModelId(Long oprtTypeId, Long compId){
		//String where = " oprtTypeId='"+oprtTypeId+"' and compId='"+compId+"' and ISActive='1' ";
		String where = " oprtTypeId='"+oprtTypeId+"' and ISActive='1' ";
		FlowModel model = null;
		try{
			model=(FlowModel)dataManager.createQuery(FlowModel.class).queryWhere(where).single();
			if(model==null){
				return Long.valueOf(0);
			}
			return model.getFlowID();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return Long.valueOf(-1);
	}
	
	/**
	 * 转换map
	 * @param <T>
	 * @param map
	 * @param cls
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static <T> T mapToObject(AbstractMap<String, Object> map,
			Class<T> cls) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, ParseException {
		Field[] fields = cls.getDeclaredFields();

		T t = null;
		if (fields.length > 0) t = cls.newInstance();

		boolean flag = false;
		for (Field field : fields) {
			Object obj = map.get(field.getName());
			
			if (map.containsKey(field.getName())&& obj != null) {
				flag = false;
				if (!field.isAccessible()) {
					field.setAccessible(true);
					flag = true;
				}
				
				String value = String.valueOf(obj);
				
				if ((field.getType() == java.util.Date.class || field.getType() == java.sql.Date.class)
						&& value.getClass() != field.getType()) {// 时间
					//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					field.set(t, new Date(value));
				} else if (field.getType() == java.sql.Timestamp.class
						&& value.getClass() != field.getType()) {// Timestamp
					field.set(t, java.sql.Timestamp.valueOf(value));
				} else if (field.getType() == java.lang.Long.class
						&& value.getClass() != field.getType()) {// Long
					if(value.lastIndexOf(".")!=-1) value = value.substring(0, value.lastIndexOf("."));
					field.set(t, Long.parseLong(value));
				} else if ((field.getType() == int.class || field.getType() == java.lang.Integer.class)
						&& value.getClass() != field.getType()) {// int
					if(value.lastIndexOf(".")!=-1) value = value.substring(0, value.lastIndexOf("."));
					field.set(t, Integer.parseInt(value));
				} else if ((field.getType() == Byte.class || field.getType() == java.lang.Byte.class)
						&& value.getClass() != field.getType()) {// Byte
					if(value.lastIndexOf(".")!=-1) value = value.substring(0, value.lastIndexOf("."));
					field.set(t, Byte.parseByte(value));
				} else if ((field.getType() == Double.class || field.getType() == java.lang.Double.class)
						&& value.getClass() != field.getType()) {// Double
					field.set(t, Double.parseDouble(value));
				} else {
					field.set(t, map.get(field.getName()));
				}

				if (flag) field.setAccessible(false);
			}
		}

		return t;
	}
}
