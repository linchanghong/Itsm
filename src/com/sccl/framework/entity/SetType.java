package com.sccl.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@Table(name="SET_TYPE", schema = "ITSM")
public class SetType extends  TableEntity implements Serializable {
	
	private static final long serialVersionUID = -104067471341439350L;
	
	private Integer id;
	private String setTypeName;
	private String setPage; //参数设置页面的路径
	private String remark;
	
	public SetType() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SET_TYPE")
	@SequenceGenerator(name = "GEN_SET_TYPE", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "SET_TYPE_ID", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "SET_TYPE_NAME", length = 50, nullable = false)
	public String getSetTypeName() {
		return setTypeName;
	}
	public void setSetTypeName(String setTypeName) {
		this.setTypeName = setTypeName;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "SET_PAGE", length = 100)
	public String getSetPage() {
		return setPage;
	}

	public void setSetPage(String setPage) {
		this.setPage = setPage;
	}

}
