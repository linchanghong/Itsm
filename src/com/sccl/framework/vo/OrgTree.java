package com.sccl.framework.vo;

import java.util.List;

import com.sccl.framework.entity.Organization;

public class OrgTree extends Organization {

	private static final long serialVersionUID = 2531077101031808809L;
	
	private List<OrgTree> children;

	public List<OrgTree> getChildren() {
		return children;
	}

	public void setChildren(List<OrgTree> children) {
		this.children = children;
	}

}
