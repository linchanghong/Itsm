package com.sccl.itsm.report.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sccl.itsm.report.service.IProjectPlanService;
import com.sun.jersey.api.core.InjectParam;

@SuppressWarnings("all")
@RemotingDestination
@Component("planAPI")
@Path(value = "/plan")
public class PlanAPI {

	@InjectParam private IProjectPlanService projectPlanService;
	
	@Transactional
	@POST
	@Path(value = "/plan")
	public String addProjectPlan(String planJson) {
		return projectPlanService.addProjectPlan(planJson);
	}
	
	@Transactional
	@PUT 
	@Path(value = "/plan")
	public String updatePlan(String planJson){
		return projectPlanService.updatePlan(planJson);
	}
	
	@GET
	@Path(value = "/plan/id/{planId}")
	@Produces(value = {"application/json"})
	public String findPlanById(@PathParam(value = "planId") String planId){
		return projectPlanService.findPlanById(planId);
	}
	
	@GET
	@Path(value = "/plans")
	@Produces(value = {"application/json"})
	public String findAllProjectPlan(){
		return projectPlanService.findAllProjectPlan();
	}
	
	@GET
	@Path(value = "/plans/tree")
	@Produces(value = {"application/json"})
	public String findAllPlanTree(){
		return projectPlanService.findAllPlanTree("");
	}
	
	@DELETE
	@Transactional
	@Path(value = "/plan/id/{planId}")
	public String deletePlanById(@PathParam(value = "planId") String planId){
		return projectPlanService.deletePlanById(planId);
	}
	
	@DELETE
	@Transactional
	@Path(value = "/plan/ids/{planIds}")
	public String deletePlanByIds(@PathParam(value = "planIds") String planIds){
		return projectPlanService.deletePlanByIds(planIds);
	}
}
