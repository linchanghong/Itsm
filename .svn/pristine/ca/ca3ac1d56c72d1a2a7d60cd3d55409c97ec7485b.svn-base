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

@XmlRootElement
@Entity
@Table(name = "SET_DETAIL", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="SetDetail.findAllByTypeId",query="SELECT DISTINCT s FROM SetDetail s where s.setType.id = :typeId ORDER BY s.id, s.setType.id")
    })
public class SetDetail extends  TableEntity implements Serializable {
	
	private static final long serialVersionUID = -3847699715410881776L;
	
	private Integer id;
	private String setValue;
	private SetType setType;
	
	public SetDetail() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SET_DETAIL")
	@SequenceGenerator(name = "GEN_SET_DETAIL", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "SET_DETAIL_ID", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "SET_VALUE", length = 100, nullable = false)
	public String getSetValue() {
		return setValue;
	}
	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "SET_TYPE_ID", nullable = false)
	public SetType getSetType() {
		return setType;
	}
	public void setSetType(SetType setType) {
		this.setType = setType;
	}

}
