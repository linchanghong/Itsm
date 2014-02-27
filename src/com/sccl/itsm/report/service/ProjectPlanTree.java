package com.sccl.itsm.report.service;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.itsm.report.entity.ProjectPlan;

@XmlRootElement
public class ProjectPlanTree extends ProjectPlan {

	private static final long serialVersionUID = 8522359730802868505L;
	
	private List<ProjectPlanTree> children;
	
	public ProjectPlanTree(){
		super();
	}

	public List<ProjectPlanTree> getChildren() {
		return children;
	}

	public void setChildren(List<ProjectPlanTree> children) {
		this.children = children;
	}
	

}
