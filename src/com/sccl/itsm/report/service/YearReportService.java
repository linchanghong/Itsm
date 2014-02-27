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
import com.sccl.itsm.report.entity.YearReport;

@Component("yearReportService")
public class YearReportService implements IYearReportService {

	private static final String ERROR = "0";
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
	public Long addYearReport(YearReport yearReport, String compId, String personId) {
		Long oprtTypeID=(long)7;
		String flowTitle="个人工作报告审批流程："+yearReport.getYear();
		try{
			FlowTools tools = FlowTools.getInstence();
			tools.setDataManager(dataManager);
			Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
			if(flowModelId==-1){
				return (long) -1;     //后台异常
			}else if(flowModelId==0){
				return (long) 0;      //缺少流程model
			}else{
				dataManager.add(yearReport);
				Long billID = Long.valueOf(yearReport.getId());
				tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
			}
			return (long) yearReport.getId();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return (long) -2;            //保存数据失败
	}
	@Transactional
	@Log
	public Long updateYearReport(String yearReportJson) {
        try{
        	YearReport yearReport=StaticMethods.getDateGson().fromJson(yearReportJson,YearReport.class);
        	dataManager.update(yearReport);
        	return yearReport.getId();
        }catch(Exception e){
        	e.printStackTrace();
			return (long) 0;
        }
	}

	@Transactional
	@Log
	public String deleteYearReport(String yearReportId) {
		try{
			Long id=Long.parseLong(yearReportId);
			dataManager.createNamedQuery("YearReport.delete").setParameter("id", id).executeUpdate();
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	public String statusYearReport(String yearReportId, String status) {

		return null;
	}
	@SuppressWarnings("unchecked")
	@Log
	public String findAllYearReportPage(String sqlWhere, int first, int size, boolean isCount) {
		try{
			List<Object> reList = new ArrayList<Object>();
			String whereStr=" where p.moduleid=1";
			if(sqlWhere!=null && !sqlWhere.equals("")){
				whereStr+=sqlWhere;
			}
			List<YearReport> yearReport=dataManager.createNativeQuery("select p.* from t_plan_year p "+whereStr+" order by p.t_plan_year_id", YearReport.class).setFirstResult(first).setMaxResults(size).getResultList();
			for(YearReport y:yearReport){
				if(y.getStatus()==0){
					y.statusName="未呈报";
				}
				if(y.getStatus()==1){
					y.statusName="流程中";
				}
				if(y.getStatus()==2){
					y.statusName="已审批";
				}
			}
			reList.add(0, yearReport);
			if(isCount) {
				reList.add(1, yearReport.size());
			}
			return StaticMethods.getDateGson().toJson(reList);
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
    
	@SuppressWarnings("unchecked")
	public String initYears(){
		try{
			List<YearReport> years=new ArrayList<YearReport>();
			years=dataManager.createQuery(YearReport.class).list();
			return StaticMethods.getDateGson().toJson(years);
		}catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public String findYearReportById(String yearReportId) {

		return null;
	}

}
