package com.sccl.itsm.report.service;

import java.util.List;

import com.sccl.itsm.report.entity.ProjectPlan;

public interface IProjectPlanService {
	public String addProjectPlan(String PlanJson);
	public String updatePlan(String PlanJson);
	public String findPlanById(String PlanId) ;
	public String findAllProjectPlan();
	public String findAllPlanTree(String rights);
	public List<ProjectPlan> findAllPlan();
	public String deletePlanById(String PlanId);
	public String deletePlanByIds(String PlanIds);
}
