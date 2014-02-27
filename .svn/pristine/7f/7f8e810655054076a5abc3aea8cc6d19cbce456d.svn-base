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

import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "CONST_TYPE", schema = "ITSM")
public class ConstType extends TableEntity implements Serializable {

	private static final long serialVersionUID = -3058991564801923237L;

	private Integer id;
	private String constTypeCode;
	private String constTypeName;
	private String remark;

	public ConstType() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CONST_TYPE")
	@SequenceGenerator(name = "GEN_CONST_TYPE", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "CONST_TYPE_ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CONST_TYPE_CODE", length = 10, nullable = false)
	public String getConstTypeCode() {
		return this.constTypeCode;
	}

	public void setConstTypeCode(String constTypeCode) {
		this.constTypeCode = constTypeCode;
	}

	@Column(name = "CONST_TYPE_NAME", nullable = false, length = 40)
	public String getConstTypeName() {
		return this.constTypeName;
	}

	public void setConstTypeName(String constTypeName) {
		this.constTypeName = constTypeName;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}