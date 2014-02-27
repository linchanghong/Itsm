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
@Table(name = "MS_POST", schema = "ITSM")
public class MsPost extends TableEntity implements Serializable {

	private static final long serialVersionUID = -7421581654484608076L;

	private Integer id;
	private String postName;
	private Integer postType;
	private Integer postLevel;

	public MsPost() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MS_POST")
	@SequenceGenerator(name = "GEN_MS_POST", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "POST_ID", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "POST_NAME", length = 50, nullable = false)
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(name = "POST_TYPE", nullable = false)
	public Integer getPostType() {
		return postType;
	}

	public void setPostType(Integer postType) {
		this.postType = postType;
	}

	@Column(name = "POST_LEVEL", nullable = false)
	public Integer getPostLevel() {
		return postLevel;
	}

	public void setPostLevel(Integer postLevel) {
		this.postLevel = postLevel;
	}
}
