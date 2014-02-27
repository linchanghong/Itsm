package com.sccl.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "CONST_DETAIL", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findConstDetailsByTypeId", query="SELECT d FROM ConstDetail d where d.constType.id=:constTypeId"),
    //根据ID查找常量，并且只查询出可用的值
    @NamedQuery(name="findConstDetailsByTypeIds",query = "SELECT d FROM ConstDetail d where d.constType.id=:constTypeId and d.enabled='1'")
})
public class ConstDetail extends TableEntity implements Serializable {

	private static final long serialVersionUID = 7432639031424609445L;

	private Integer id;
	private String constDetailCode;
	private String constDetailName;
	private Byte enabled;
	private String remark;
	private ConstType constType;

	public ConstDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CONST_DETAIL")
	@SequenceGenerator(name = "GEN_CONST_DETAIL", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "COM_CONST_DETAIL_ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CONST_DETAIL_CODE", length = 10, nullable = false)
	public String getConstDetailCode() {
		return this.constDetailCode;
	}

	public void setConstDetailCode(String constDetailCode) {
		this.constDetailCode = constDetailCode;
	}

	@Column(name = "CONST_DETAIL_NAME", nullable = false, length = 100)
	public String getConstDetailName() {
		return this.constDetailName;
	}

	public void setConstDetailName(String constDetailName) {
		this.constDetailName = constDetailName;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setConstType(ConstType constType) {
		this.constType = constType;
	}

	@ManyToOne
	@JoinColumn(name = "CONST_TYPE_ID", nullable = false)
	public ConstType getConstType() {
		return constType;
	}

	@Column(name = "ENABLED")
	public Byte getEnabled() {
		return enabled;
	}

	public void setEnabled(Byte enabled) {
		this.enabled = enabled;
	}

}