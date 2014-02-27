package com.sccl.flow.common;

public class FlowException extends Exception {
	private static final long serialVersionUID = -746856267231428278L;
	
	public FlowException(){
		super();
	}
	
	public FlowException(String expStr){
		super(expStr);
	}
}
