package com.sccl.framework.vo;

public class JavaMethod {
	
	/**spring bean名*/
	private String bean;
	/**方法名*/
	private String method;
	/**方法参数*/
	private Object[] parameters;
	
	public JavaMethod() {
	}
	
	public String getBean() {
		return bean;
	}
	public void setBean(String bean) {
		this.bean = bean;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Object[] getParameters() {
		return parameters;
	}
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	
}
