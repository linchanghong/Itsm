package com.sccl.itsm.report.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sccl.flow.common.FlowTools;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.itsm.report.entity.MonthReport;


@Component("monthReportService")
public class MonthReportService implements IMonthReportService {
 
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
	public Long addMonthReport(MonthReport monthReport, String compId, String personId) {
		Long oprtTypeID=(long)7;
		String flowTitle="个人工作报告审批流程："+monthReport.getMonth();
		try{
			FlowTools tools = FlowTools.getInstence();
			tools.setDataManager(dataManager);
			Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
			if(flowModelId==-1){
				return (long) -1;     //后台异常
			}else if(flowModelId==0){
				return (long) 0;      //缺少流程model
			}else{
				dataManager.add(monthReport);
				Long billID = Long.valueOf(monthReport.getId());
				tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
			}
			return (long) monthReport.getId();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return (long) -2;            //保存数据失败
	}

	@Transactional
	@Log
	public Long updateMonthReport(String monthReportJson) {
		try{
        	MonthReport monthReport=StaticMethods.getDateGson().fromJson(monthReportJson,MonthReport.class);
        	dataManager.update(monthReport);
        	return monthReport.getId();
        }catch(Exception e){
        	e.printStackTrace();
			return (long) 0;
        }
	}

	@Transactional
	@Log
	public String deleteMonthReport(String monthReportId) {
		try{
			Long id=Long.parseLong(monthReportId);
			dataManager.createNamedQuery("MonthReport.delete").setParameter("id", id).executeUpdate();
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}


	public String statusMonthReport(String monthReportId, String status) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Log
	public String findAllMonthReportPage(String sqlWhere, int first, int size, boolean isCount) {
		try{
			List<Object> reList = new ArrayList<Object>();
			String whereStr=" where p.moduleid=2";
			if(sqlWhere!=null && !sqlWhere.equals("")){
				whereStr+=sqlWhere;
			}
			List<MonthReport> monthReport=dataManager.createNativeQuery("select p.* from t_plan_month p "+whereStr+" order by p.t_plan_month_id", MonthReport.class).setFirstResult(first).setMaxResults(size).getResultList();
			for(MonthReport m:monthReport){
				if(m.getStatus()==0){
					m.statusName="未呈报";
				}
				if(m.getStatus()==1){
					m.statusName="流程中";
				}
				if(m.getStatus()==2){
					m.statusName="已审批";
				}
			}
			reList.add(0, monthReport);
			if(isCount) {
				reList.add(1, monthReport.size());
			}
			return StaticMethods.getDateGson().toJson(reList);
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}


	public String findMonthReportById(String monthReportId) {

		return null;
	}

}
