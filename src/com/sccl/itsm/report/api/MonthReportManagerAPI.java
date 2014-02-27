package com.sccl.itsm.report.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sccl.flow.vo.DataInteraction;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.itsm.report.entity.MonthReport;
import com.sccl.itsm.report.service.IMonthReportService;
import com.sun.jersey.api.core.InjectParam;

@SuppressWarnings("all")
@RemotingDestination
@Component("monthReportManagerAPI")
@Path(value = "/itsm/report/monthReportManagerAPI")
public class MonthReportManagerAPI {
	   @InjectParam private IMonthReportService monthReportService;
	   @Transactional
	   @POST
	   @Path(value = "/addKnowledgeInfo")
	   public String addMonthReport(String monthReportJson){
		   Gson gson = StaticMethods.getDateGson();
		   System.out.println(gson);
		   Long type = (long) -2; 
		   MonthReport monthReport=null;
		   try{
			   monthReport=gson.fromJson(monthReportJson, MonthReport.class);
			   String compId=monthReport.getOpid().companyID.toString().trim();
			   String personId =monthReport.getOpid().personId.toString().trim();
			   type=monthReportService.addMonthReport(monthReport, compId, personId);
		   }catch (IllegalArgumentException e) {
	           e.printStackTrace();
	       }
		   DataInteraction di = new DataInteraction();
		   di.setType(type.intValue());
		   return gson.toJson(di);
	   }
	 
	   @GET
	   @Path(value = "/findAllMonthReportPage/query/{searchInfo}/{pageNum}/{lines}/{isCount}")
	   @Produces(value = {"application/json"})
	   public String findAllMonthReportPage(@PathParam(value = "sqlWhere") String sqlWhere,@PathParam(value = "first") int first,
				@PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount){
		   return monthReportService.findAllMonthReportPage(sqlWhere, first, size, isCount);
	   }
       
	   @Transactional
	   public String deleteMonthReport(String monthReportId){
		   return monthReportService.deleteMonthReport(monthReportId);
	   }
	   
	   @Transactional
	   @POST
	   @Path(value = "/updateMonthReport")
	   public Long updateMonthReport (String monthReportJson){
		   return monthReportService.updateMonthReport(monthReportJson);
	   }
}
