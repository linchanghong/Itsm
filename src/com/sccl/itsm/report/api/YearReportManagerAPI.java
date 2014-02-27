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
import com.sccl.itsm.report.entity.YearReport;
import com.sccl.itsm.report.service.IYearReportService;
import com.sccl.itsm.report.service.YearReportService;
import com.sun.jersey.api.core.InjectParam;

@SuppressWarnings("all")
@RemotingDestination
@Component("yearReportManagerAPI")
@Path(value = "/itsm/report/yearReportManagerAPI")
public class YearReportManagerAPI {
   @InjectParam private IYearReportService yearReportService;
   @Transactional
   @POST
   @Path(value = "/addKnowledgeInfo")
   public String addYearReport(String yearReportJson){
	   Gson gson = StaticMethods.getDateGson();
	   Long type = (long) -2; 
	   YearReport yearReport=null;
	   try{
		   yearReport=gson.fromJson(yearReportJson, YearReport.class);
		   String compId=yearReport.getOpid().companyID.toString().trim();
		   String personId =yearReport.getOpid().personId.toString().trim();
		   type=yearReportService.addYearReport(yearReport, compId, personId);
	   }catch (IllegalArgumentException e) {
           e.printStackTrace();
       }
	   DataInteraction di = new DataInteraction();
	   di.setType(type.intValue());
	   return gson.toJson(di);
   }
   

   @GET
   @Path(value = "/findAllYearReportPage/query/{searchInfo}/{pageNum}/{lines}/{isCount}")
   @Produces(value = {"application/json"})
   public String findAllYearReportPage(@PathParam(value = "sqlWhere") String sqlWhere,@PathParam(value = "first") int first,
			@PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount){
	   return yearReportService.findAllYearReportPage(sqlWhere, first, size, isCount);
   }
   
   @Transactional
   public String deleteYearReport(String yearReportId){
	   return yearReportService.deleteYearReport(yearReportId);
   }
   
   @Transactional
   @POST
   @Path(value = "/updateYearReport")
   public Long updateYearReport (String yearReportJson){
	   return yearReportService.updateYearReport(yearReportJson);
   }
   
   /**
    * 
    * @Title: initYears
    * @Description:MonthReport增、删、改页面的所属系统下拉单初始化
    * @return 
    * @retunType:List<YearReport>      返回类型
    * @throws:
    */
  @GET
  @Path(value = "years")
  @Produces(value = {"application/json"})
  public String initYears() {
      return yearReportService.initYears();
  }
}
