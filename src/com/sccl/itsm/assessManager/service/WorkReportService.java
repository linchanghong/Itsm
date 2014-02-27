package com.sccl.itsm.assessManager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sccl.flow.common.FlowTools;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.itsm.assessManager.entity.WorkReport;

@Component("workReporService")
public class WorkReportService implements IWorkReportService{

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
	public Long addWorkReport(WorkReport workReport, String compId,String personId) {
		Long oprtTypeID=(long)7;
		String flowTitle="";
		if(workReport.getType()==1||workReport.getType()==2||workReport.getType()==6){
			oprtTypeID=(long)7;
			flowTitle = "个人工作报告审批流程："+workReport.getTitle();
		}
		if(workReport.getType()==3||workReport.getType()==4){
			oprtTypeID=(long)8;
			flowTitle = "项目工作报告审批流程："+workReport.getTitle();
		}
		try {
			FlowTools tools = FlowTools.getInstence();
			tools.setDataManager(dataManager);
			Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
			if(flowModelId==-1){
				return (long) -1;     //后台异常
			}else if(flowModelId==0){
				return (long) 0;      //缺少流程model
			}else{
				workReport.setReportDate(new Date());
				dataManager.add(workReport);
				Long billID = Long.valueOf(workReport.getId());
				tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
			}
			return (long) workReport.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (long) -2;            //保存数据失败
	}

	@Transactional
	@Log
	public Long updateWorkReport(String workReportJson) {
		try {
			WorkReport workReport = StaticMethods.getDateGson().fromJson(workReportJson, WorkReport.class);
			//WorkReport w=dataManager.findById(WorkReport.class, workReport.getId());
			workReport.setReportDate(new Date());
			dataManager.update(workReport);
			return workReport.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return (long) 0;
		}
	}

	@Transactional
	@Log
	public String deleteWorkReport(String workReportId) {
		try{
			Long id=Long.parseLong(workReportId);
			dataManager.createNamedQuery("WorkReport.delete").setParameter("id", id).executeUpdate();
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Transactional
	@Log
	public String statusWorkReport(String workReportId,String status) {
		try{
			Long id=Long.parseLong(workReportId);
			int statusId=Integer.parseInt(status);
			dataManager.createNamedQuery("WorkReport.status").setParameter("statusId",statusId).setParameter("id", id).executeUpdate();
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@SuppressWarnings("unchecked")
	@Log
	public String findAllWorkReportPage(String sqlWhere, int first, int size,boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			String whereStr=" where p.dr=0";
			if(sqlWhere!=null && !sqlWhere.equals("")){
				whereStr+=sqlWhere;
			}
			List<WorkReport> workReport = dataManager.createNativeQuery("select p.* from t_work_report p "+whereStr+" order by p.work_report_id", WorkReport.class).setFirstResult(first).setMaxResults(size).getResultList();
			
			for(WorkReport w:workReport){
				if(w.getType()==1){
					w.typeName="个人日报";
				}
				if(w.getType()==2){
					w.typeName="个人周报";
				}
				if(w.getType()==3){
					w.typeName="项目周报";
				}
				if(w.getType()==4){
					w.typeName="项目月报";
				}
				if(w.getType()==6){
					w.typeName="个人月报";
				}
				if(w.getStatus()==0){
					w.statusName="未呈报";
				}
				if(w.getStatus()==1){
					w.statusName="流程中";
				}
				if(w.getStatus()==2){
					w.statusName="已审批";
				}
			}
			
			reList.add(0, workReport);
			if(isCount) {
				reList.add(1, workReport.size());
			}
			
			//return StaticMethods.getExposeGson().toJson(reList);
			return StaticMethods.getDateGson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public String findWorkReportById(String workReportId){
		try {
			WorkReport w=dataManager.findById(WorkReport.class, Long.parseLong(workReportId));
			if(w.getType()==1){
				w.typeName="个人日报";
			}
			if(w.getType()==2){
				w.typeName="个人周报";
			}
			if(w.getType()==3){
				w.typeName="项目周报";
			}
			if(w.getType()==4){
				w.typeName="项目月报";
			}
			if(w.getType()==6){
				w.typeName="个人月报";
			}
			if(w.getStatus()==0){
				w.statusName="未呈报";
			}
			if(w.getStatus()==1){
				w.statusName="流程中";
			}
			if(w.getStatus()==2){
				w.statusName="已审批";
			}
			return StaticMethods.getDateGson().toJson(w);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
