package com.sccl.itsm.assessManager.service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.flow.common.FlowTools;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.itsm.assessManager.dto.AssessDTO;
import com.sccl.itsm.assessManager.entity.Assess;
import com.sccl.itsm.assessManager.entity.AssessScheme;
import com.sccl.itsm.assessManager.entity.Period;
import com.sccl.serviceManager.bugManager.entity.User;

@Component("assessSchemeService")
public class AssessSchemeService implements IAssessSchemeService {

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
	public Long addAssessScheme(AssessScheme assessScheme, String compId, String personId) {
		try {
			FlowTools tools = FlowTools.getInstence();
			tools.setDataManager(dataManager);
			Long oprtTypeID = Long.valueOf("9");
			Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
			if(flowModelId==-1){
				return (long) -1;  //后台异常
			}else if(flowModelId==0){
				return (long) 0;  //缺少流程model
			}else{
				dataManager.add(assessScheme);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				//添加考核方案后自动添加考核明细
				String startDate=format.format(assessScheme.getPeriod().getStartDate());
				String endDate=format.format(assessScheme.getPeriod().getEndDate());
				String sql="select * from (select distinct user_id as userId,sys_master as examiner from view_system_person where user_status = 0) t left join ("
						+"select service_staff,sum(decode(service_type,1,1,0)) as problemCount,sum(decode(service_type,2,1,0)) as bugCount,"
						+"sum(decode(service_type,3,1,0)) as demandCount from t_service_order where dr=0 and (reply_date between "
						+"to_date('"+startDate+" 00:00:01','yyyy-mm-dd HH24:Mi:SS') and to_date('"+endDate+" 23:59:59','yyyy-mm-dd HH24:Mi:SS'))"
						+" group by service_staff) a on t.userId=a.service_staff left join (select reporter,sum(decode(report_type,1,1,0))"
						+" as dailyCount,sum(decode(report_type,2,1,0)) as weeklyCount from t_work_report where report_status=2 and dr=0 "
						+"and (report_date between to_date('"+startDate+" 00:00:01','yyyy-mm-dd HH24:Mi:SS') and to_date('"+endDate+" 23:59:59'"
						+",'yyyy-mm-dd HH24:Mi:SS')) group by reporter) b on t.userId=b.reporter";
				List<AssessDTO> assessDTOs = (List<AssessDTO>)dataManager.nativeQuery(sql,AssessDTO.class);
				List<Assess> assesses=new ArrayList<Assess>();
				for(AssessDTO a:assessDTOs){
					Assess assess=new Assess();
					assess.setScheme(assessScheme);
					assess.setBugCount((a.getBugCount()==null)?0:a.getBugCount());
					assess.setTroubleCount((a.getProblemCount()==null)?0:a.getProblemCount());
					assess.setDemandCount((a.getDemandCount()==null)?0:a.getDemandCount());
					assess.setDailyCount((a.getDailyCount()==null)?0:a.getDailyCount());
					assess.setWeeklyCount((a.getWeeklyCount()==null)?0:a.getWeeklyCount());
					User u=new User();
					u.setId(a.getUserId());
					User e=new User();
					e.setId(a.getExaminer());
					assess.setExaminer(e);
					assess.setAssessObj(u);
					assesses.add(assess);
				}
				for(Assess ae:assesses){
					dataManager.add(ae);
				}
				
				Long billID = Long.valueOf(assessScheme.getId());
				String flowTitle = "支撑人员考核流程："+assessScheme.getSchemeName();
				tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
			}
			return (long) assessScheme.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (long) -2;  //保存数据失败
	}

	@Transactional
	@Log
	public Long updateAssessScheme(AssessScheme assessScheme) {
		try {
			dataManager.update(assessScheme);
			return assessScheme.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return (long) 0;
		}
	}

	@Transactional
	@Log
	public String deleteAssessScheme(String assessSchemeId) {
		try{
			Long id=Long.parseLong(assessSchemeId);
			dataManager.createNamedQuery("AssessScheme.delete").setParameter("id", id).executeUpdate();
			List<Assess> assesses = dataManager.createNamedQuery("SELECT w FROM Assess w where w.scheme.id="+assessSchemeId, Assess.class).getResultList();
			for(Assess ae:assesses){
				dataManager.deleteById(Assess.class, ae.getId());
			}
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	@Log
	public String statusAssessScheme(String assessSchemeId, String status) {
		try{
			Long id=Long.parseLong(assessSchemeId);
			int statusId=Integer.parseInt(status);
			dataManager.createNamedQuery("AssessScheme.status").setParameter("id", id).setParameter("statusId",statusId).executeUpdate();
			return "1";
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@SuppressWarnings("unchecked")
	@Log
	public String findAllAssessSchemePage(String sqlWhere, int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			String whereStr=" where p.dr=0";
			if(sqlWhere!=null && !sqlWhere.equals("")){
				whereStr+=sqlWhere;
			}
			List<AssessScheme> assessScheme = dataManager.createNativeQuery("select p.* from t_assess_scheme p "+whereStr+" order by p.scheme_id", AssessScheme.class).setFirstResult(first).setMaxResults(size).getResultList();
			
			for(AssessScheme a:assessScheme){
				if(a.getStatus()==0){
					a.statusName="未提交";
				}
				if(a.getStatus()==1){
					a.statusName="考评中";
				}
				if(a.getStatus()==2){
					a.statusName="已考评";
				}
			}
			
			reList.add(0, assessScheme);
			if(isCount) {
				reList.add(1, assessScheme.size());
			}
			
			return StaticMethods.getDateGson().toJson(reList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAssessSchemeById(String assessSchemeId) {
		try{
			AssessScheme a=dataManager.findById(AssessScheme.class, Long.parseLong(assessSchemeId));
			if(a.getStatus()==0){
				a.statusName="未提交";
			}
			if(a.getStatus()==1){
				a.statusName="考核中";
			}
			if(a.getStatus()==2){
				a.statusName="已考核";
			}
			return StaticMethods.getDateGson().toJson(a);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public int findSchemeByPeriodId(String periodId) {
		try{
			List<AssessScheme> assessSchemes = dataManager.createNamedQuery("AssessScheme.querByPeriod", AssessScheme.class).setParameter("id", Long.parseLong(periodId)).getResultList();
			if(assessSchemes.size()>0){
				return assessSchemes.size();
			}else{
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Log
	public String findAllPeriodPage(String sqlWhere, int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			String whereStr=" where p.dr=0";
			if(sqlWhere!=null && !sqlWhere.equals("")){
				whereStr+=sqlWhere;
			}
			List<Period> period = dataManager.createNativeQuery("select p.* from t_period p "+whereStr+" order by p.period_id", Period.class).setFirstResult(first).setMaxResults(size).getResultList();
			
			reList.add(0, period);
			if(isCount) {
				reList.add(1, period.size());
			}
			
			return StaticMethods.getDateGson().toJson(reList);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllPeriod() {
		try {
			List<Period> periods = dataManager.createNamedQuery("Period.findPeriods", Period.class).getResultList();
			return StaticMethods.getDateGson().toJson(periods);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
