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
@Table(name = "CUSTOM_COLUMNS", schema = "ITSM")
public class Customcolumns extends TableEntity implements Serializable {
	
	private static final long serialVersionUID = 1922460862859233897L;
	
	private Integer id;
	private String moduleName;
	private Integer userId;
	private String columns;
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CUSTOM_COLUMNS")
	@SequenceGenerator(name = "GEN_CUSTOM_COLUMNS", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "CUSTOM_COLUMNS_ID", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "MODULE_NAME", length = 50)
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "COLUMNS", length = 500)
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}

}
