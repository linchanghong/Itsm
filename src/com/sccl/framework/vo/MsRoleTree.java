package com.sccl.framework.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.MsRole;

@XmlRootElement
public class MsRoleTree extends MsRole {
	
	private static final long serialVersionUID = 2883202253197888499L;
	private List<MsRoleTree> children;

	public MsRoleTree() {
		super();
	}

	public List<MsRoleTree> getChildren() {
		return children;
	}

	public void setChildren(List<MsRoleTree> children) {
		this.children = children;
	}

}
