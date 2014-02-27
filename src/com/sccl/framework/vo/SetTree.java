package com.sccl.framework.vo;

import java.util.List;

import com.sccl.framework.entity.SetDetail;
import com.sccl.framework.entity.SetType;

public class SetTree extends SetType {
	
	private static final long serialVersionUID = -8977773026110194932L;
	
	private List<SetDetail> children;

	public List<SetDetail> getChildren() {
		return children;
	}

	public void setChildren(List<SetDetail> children) {
		this.children = children;
	}
}
