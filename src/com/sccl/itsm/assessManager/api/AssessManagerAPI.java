package com.sccl.itsm.assessManager.api;

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
import com.sccl.itsm.assessManager.entity.Assess;
import com.sccl.itsm.assessManager.entity.AssessScheme;
import com.sccl.itsm.assessManager.service.IAssessSchemeService;
import com.sccl.itsm.assessManager.service.IAssessService;
import com.sun.jersey.api.core.InjectParam;

@SuppressWarnings("all")
@RemotingDestination
@Component("assessManagerAPI")
@Path(value = "/itsm/assessManager/assessManagerAPI")
public class AssessManagerAPI {
	
	@InjectParam private IAssessSchemeService assessSchemeService;
	@InjectParam private IAssessService assessService;
	
	@Transactional
	@POST
	@Path(value = "/addAssessScheme")
	public String addAssessScheme(String assessSchemeJosn,String compId, String personId){
		Gson gson = StaticMethods.getDateGson();
		Long type = (long) -2;
		AssessScheme assessScheme=null;
		try{
			assessScheme=gson.fromJson(assessSchemeJosn,AssessScheme.class);
			type = assessSchemeService.addAssessScheme(assessScheme, compId, personId);
		}catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        
		DataInteraction di = new DataInteraction();
	    di.setType(type.intValue());
	    return gson.toJson(di);
	}
	
	@Transactional
	@POST
	@Path(value = "/updateAssessScheme")
	public Long updateAssessScheme(String assessSchemeJosn){
		Gson gson = StaticMethods.getDateGson();
		AssessScheme assessScheme=gson.fromJson(assessSchemeJosn,AssessScheme.class);
		return assessSchemeService.updateAssessScheme(assessScheme);
	}
	
	@Transactional
	public String deleteAssessScheme(String assessSchemeId){
		return assessSchemeService.deleteAssessScheme(assessSchemeId);
	}
	
	@Transactional
	public String statusAssessScheme(String assessSchemeId,String status){
		return assessSchemeService.statusAssessScheme(assessSchemeId, status);
	}
	
	@GET
    @Path(value = "/findAllAssessSchemePage/query/{searchInfo}/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
	public String findAllAssessSchemePage(@PathParam(value = "sqlWhere") String sqlWhere, @PathParam(value = "first") int first,
			@PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount){
		return assessSchemeService.findAllAssessSchemePage(sqlWhere, first, size, isCount);
	}
	
	@GET
	@Path(value = "/findAssessSchemeById/id/{assessSchemeId}")
	@Produces(value = {"application/json"})
	public String findAssessSchemeById(@PathParam(value = "assessSchemeId") String assessSchemeId){
		return assessSchemeService.findAssessSchemeById(assessSchemeId);
	}
	
	@GET
    @Path(value = "/findAllPeriodPage/query/{searchInfo}/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
	public String findAllPeriodPage(@PathParam(value = "sqlWhere") String sqlWhere, @PathParam(value = "first") int first,
			@PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount){
		return assessSchemeService.findAllPeriodPage(sqlWhere, first, size, isCount);
	}
	
	@GET
    @Path(value = "findAllPeriod")
    @Produces(value = {"application/json"})
	public String findAllPeriod(){
		return assessSchemeService.findAllPeriod();
	}
	
	@Transactional
	@POST
	@Path(value = "/updateAssesses")
	public Long updateAssesses(String assessJsons){
		return assessService.updateAssesses(assessJsons);
	}
	
	@GET
    @Path(value = "/findAllAssessPage/query/{searchInfo}/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
	public String findAllAssessPage(@PathParam(value = "sqlWhere") String sqlWhere, @PathParam(value = "first") int first,
			@PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount){
		return assessService.findAllAssessPage(sqlWhere, first, size, isCount);
	}

	@GET
	@Path(value = "/findSchemeByPeriodId/id/{periodId}")
	@Produces(value = {"application/json"})
	public int findSchemeByPeriodId(@PathParam(value = "periodId") String periodId){
		return assessSchemeService.findSchemeByPeriodId(periodId);
	}
}
