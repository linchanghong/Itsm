package com.sccl.flow.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sccl.flow.entity.FlowModel;
import com.sccl.flow.entity.FlowNodeModel;
import com.sccl.flow.entity.FlowOprtModule;
import com.sccl.flow.entity.FlowOprtType;
import com.sccl.flow.vo.RoleAndNodeModel;
import com.sccl.framework.DataManager;
import com.sccl.framework.query.DataQuery;
import com.sccl.framework.utils.Tools;

@SuppressWarnings("all")
@Component("flowModelService")
public class FlowModelService implements IFlowModelService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource(name = "dataManager")
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
	
	public String getTypeAndModel(String whereType, String whereModel){
		List<FlowOprtType> listType = dataManager.createQuery(FlowOprtType.class).queryWhere(whereType).list();
		List<FlowModel> listModel = dataManager.createQuery(FlowModel.class).queryWhere(whereModel).list();
		List list= new ArrayList();
		list.add(listType);
		list.add(listModel);
//		StringBuffer sb = new StringBuffer();
//		sb.append("[[");
//		for(FlowOprtType flowOprtType:listType){
//			sb.append("{");
//			sb.append("\"OprtTypeID\":"+flowOprtType.getOprtTypeID());
//			sb.append(",\"OprtTypeName\":\""+flowOprtType.getOprtTypeName()+"\"");
//			sb.append(",\"Effective\":\""+flowOprtType.getEffective()+"\"");
//			sb.append(",\"CompFlag\":\""+flowOprtType.getCompFlag()+"\"");
//			sb.append("},");
//		}
//		if(sb.toString().endsWith(",")) sb.deleteCharAt(sb.toString().lastIndexOf(","));
//		sb.append("],[");
//		for(FlowModel flowModel:listModel){
//			sb.append("{");
//			sb.append("\"FlowID\":"+flowModel.getFlowID());
//			sb.append(",\"FlowName\":\""+flowModel.getFlowName()+"\"");
//			sb.append(",\"CompId\":"+flowModel.getCompId());
//			sb.append(",\"OprtTypeID\":"+flowModel.getOprtTypeID());
//			sb.append(",\"IsOneByOne\":\""+flowModel.getIsOneByOne()+"\"");
//			sb.append(",\"ISActive\":\""+flowModel.getISActive()+"\"");
//			sb.append(",\"IsSubmitModify\":\""+flowModel.getIsSubmitModify()+"\"");
//			sb.append("},");
//		}
//		if(sb.toString().endsWith(",")) sb.deleteCharAt(sb.toString().lastIndexOf(","));
//		sb.append("]]");
//		String json = sb.toString();
		
		String json = Tools.getGson().toJson(list);
//		System.out.println(json);
		return json;
	}
	
	public int addModel(FlowModel flowModel){
		try{
			if (flowModel.getISActive()){
				String where = "oprtTypeId="+ flowModel.getOprtTypeID() 
				+ " and compId=" + flowModel.getCompId();
				List<FlowModel> list = dataManager.createQuery(FlowModel.class).queryWhere(where).list();
				for(FlowModel model : list){
					model.setISActive(false);
					dataManager.update(model);
				}
			}
			
	        dataManager.add(flowModel);
	        return 1;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void updateModel(FlowModel flowModel){
		if (flowModel.getISActive()){
			String where = "oprtTypeId="+ flowModel.getOprtTypeID() 
			+ " and compId=" + flowModel.getCompId();
			List<FlowModel> list = dataManager.createQuery(FlowModel.class).queryWhere(where).list();
			for(FlowModel model : list){
				model.setISActive(false);
				dataManager.update(model);
			}
		}
		dataManager.update(flowModel);
	}
	
	public List<RoleAndNodeModel> getAllNodeByModelId(String id){
//		String sql ="select a.*, case when a.NodeMark = '-10' then a.NodeMark else TO_CHAR(b.FlowRoleID) end as FlowRoleID,"
//			+" case when a.NodeMark = '-10' then '��Ŀ����(��Ŀ����)' else b.FlowRoleName end as FlowRoleName from FlowNodeModel a"
//+" Left Outer Join FlowRole b ON (case when a.NodeMark is null then '' else a.NodeMark end) = b.FlowRoleID where a.FlowID = '"+id+"'";
		
		String sql ="select a.*, case when a.NodeMark = '-10' then a.NodeMark else TO_CHAR(b.FlowRoleID) end as FlowRoleID,"
			+" case when a.NodeMark = '-10' then '项目经理(项目所属)' else b.FlowRoleName end as FlowRoleName from FlowNodeModel a"
+" Left Outer Join FlowRole b ON (case when regexp_like(a.NodeMark,'^\\d+$') then a.NodeMark else '' end) = b.FlowRoleID where a.FlowID = '"+id+"'";
		
		
		return (List<RoleAndNodeModel>)dataManager.nativeQuery(sql, RoleAndNodeModel.class);
	}
	
	public List<FlowOprtModule> getAllFlowOprtModule(String where,String orderby){
		DataQuery dq = dataManager.createQuery(FlowOprtModule.class);
		if(where!=null && !where.equals("")) dq = dq.queryWhere(where);
		if(orderby!=null && !orderby.equals("")) dq = dq.asc(orderby);
		return dq.list();
	}
	
	public boolean saveFlowNodeModels(List<FlowNodeModel> flowNodeModelList){
		try {
			FlowNodeModel deletefnm = (FlowNodeModel)flowNodeModelList.get(0);
			
			List<FlowNodeModel> list = dataManager.createQuery(FlowNodeModel.class).queryWhere("FlowID="+deletefnm.getFlowID()).list();
            
			for(FlowNodeModel newmodel:flowNodeModelList){
				Long nodeid = newmodel.getNodeID();
				if(nodeid==null)	dataManager.add(newmodel);
				else{
					for(FlowNodeModel oldmodel:list){
						if(nodeid == oldmodel.getNodeID()){
							dataManager.update(newmodel);
							list.remove(oldmodel);
							break;
						}
					}
				}
			}
			
			if(list.size()>0){
				for(FlowNodeModel model:list){
					dataManager.deleteById(FlowNodeModel.class, model.getNodeID());
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 查询转办
	 * @param flowOprtID
	 * @param zhuanBan
	 * @return
	 */
	public List<FlowOprtModule> getZhuanBan(int flowOprtID, String zhuanBan){
		List<FlowOprtModule> list = new ArrayList<FlowOprtModule>();
		if(flowOprtID==0 || zhuanBan==null || "".equals(zhuanBan.trim())) return list;
		long id = findFlowOprtId(flowOprtID, zhuanBan);
		if(id !=0){
			String strSQL = "SELECT   *  FROM    FlowOprtModule where FlowOprtID='"+id+"' and FlowOprtModuleID in(" + zhuanBan + ")";
			list = (List<FlowOprtModule>)dataManager.nativeQuery(strSQL, FlowOprtModule.class);
		}
		
		return list;
	}
	
	private long findFlowOprtId(long flowOprtID, String zhuanBan){
		long id = 0;
		if(flowOprtID==0) return id;
		List<FlowOprtModule> list = (List<FlowOprtModule>)dataManager.nativeQuery("select * from FlowOprtModule t where t.flowoprtid='"+flowOprtID+"'", FlowOprtModule.class);
		
		if(list!=null){
			
			for(FlowOprtModule flowOprtModule : list){
				String zb = flowOprtModule.getZhuanBan();
				if(zb!=null && zb.equals(zhuanBan)){
					
					return flowOprtID;
				}
			}

			FlowOprtType flowOprtType = dataManager.findById(FlowOprtType.class, (long)flowOprtID);
			if(flowOprtType!=null && flowOprtType.getTFOprtTypeID()!=0){
				id = findFlowOprtId(flowOprtType.getTFOprtTypeID(), zhuanBan);
			}
		}
		
		return id;
	}
}
