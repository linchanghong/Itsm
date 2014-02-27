package com.sccl.itsm.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.sccl.framework.DataManager;

import com.google.gson.Gson;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.itsm.report.entity.ProjectPlan;

@Component("projectPlanService")
public class ProjectPlanService implements IProjectPlanService {
	
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
	public String addProjectPlan(String planJson) {
		try {
			Gson gson = new Gson(); 
			ProjectPlan projectPlan = dataManager.add(gson.fromJson(planJson, ProjectPlan.class));
			return gson.toJson(projectPlan);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updatePlan(String planJson) {
		try {
			dataManager.update(new Gson().fromJson(planJson, ProjectPlan.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findPlanById(String planId) {
		try {
			ProjectPlan projectPlan = dataManager.findById(ProjectPlan.class, Integer.parseInt(planId));
			return projectPlan.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllProjectPlan() {
		try {
			return new Gson().toJson(findAllPlan());
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllPlanTree(String rights) {
		try {
			List <ProjectPlanTree> projectPlanTrees = new ArrayList<ProjectPlanTree>();
			List<ProjectPlan> projectPlans = findAllPlan();
			Iterator<ProjectPlan> iter = projectPlans.iterator();
			while(iter.hasNext()) {
				ProjectPlan projectPlan = iter.next();
				
				if(rights != "" && rights.indexOf(","+ projectPlan.getId() +",") == -1) {//没有权限
					continue;
				}
				
				if(projectPlan.getParentId() == 0) {//大菜单
					ProjectPlanTree projectPlanTree = StaticMethods.toProjectPlanTree(projectPlan);
					List<ProjectPlanTree> projectPlanTrees2 = getChildrenPlans(projectPlans, projectPlanTree.getId(), rights);
					if(projectPlanTrees2 != null && projectPlanTrees2.size() != 0) {
						projectPlanTree.setChildren(projectPlanTrees2);
					}
					projectPlanTrees.add(projectPlanTree) ;
				}
			}
			return new Gson().toJson(projectPlanTrees);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	private List<ProjectPlanTree> getChildrenPlans(List<ProjectPlan> projectPlans, int parentId, String rights) {
		List <ProjectPlanTree> projectPlanTrees = new ArrayList<ProjectPlanTree>();
		Iterator<ProjectPlan> iter = projectPlans.iterator();
		while(iter.hasNext()) {
			ProjectPlan projectPlan = iter.next();
			
			if(rights != "" && rights.indexOf(","+ projectPlan.getId() +",") == -1) {//没有权限
				continue;
			}
			
			if(projectPlan.getParentId() == parentId) {
				ProjectPlanTree projectPlanTree = StaticMethods.toProjectPlanTree(projectPlan);
				List<ProjectPlanTree> projectPlanTrees2 = getChildrenPlans(projectPlans, projectPlanTree.getId(), rights);
				if(projectPlanTrees2 != null && projectPlanTrees2.size() != 0) {
					projectPlanTree.setChildren(projectPlanTrees2);
				}
				projectPlanTrees.add(projectPlanTree);
			}
		}
		return projectPlanTrees;
	}

	@Log
	public List<ProjectPlan> findAllPlan() {
		return dataManager.createNamedQuery("ProjectPlan.findAll", ProjectPlan.class).getResultList();
	}

	@Log
	@Transactional
	public String deletePlanById(String planId) {
		try {
			dataManager.deleteById(ProjectPlan.class, Integer.parseInt(planId));
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	@Log
	@Transactional
	public String deletePlanByIds(String planIds) {
		try {
			Integer[] ids = new Gson().fromJson(planIds, Integer[].class);
			
			dataManager.deleteByIdBatch(ProjectPlan.class, ids);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

}
