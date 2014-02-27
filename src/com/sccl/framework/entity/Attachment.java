package com.sccl.framework.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@Entity
@Table(name = "MS_ATTACHMENT", schema = "ITSM")
@NamedQueries({
	@NamedQuery(name="Attachment.findRelateAttachments",query="SELECT a FROM Attachment a where a.busTableName=:busTableName and a.busDataId=:busDataId")
    })
public class Attachment extends TableEntity implements Serializable {

	private static final long serialVersionUID = 691983369183665087L;
	
	private Integer id;//附件id
	private String attachName;//附件名	
	private String attachContent;//描述
	private String attachUrl;//存放的url
	private Integer userId;//上传用户的id
	private String userName;//上传用户名
	private String attachType;//后缀名，如.doc
	private String uploadDate;//上传时间 
	private String busTableName;//关联表名
	private String busDataId;//关联数据的id
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MS_ATTACHMENT")
	@SequenceGenerator(name = "GEN_MS_ATTACHMENT", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "ATTACH_ID", nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "ATTACH_NAME", length = 100, nullable = false)
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	

	@Column(name = "ATTACH_CONTENT", length = 500)
	public String getAttachContent() {
		return attachContent;
	}
	public void setAttachContent(String attachContent) {
		this.attachContent = attachContent;
	}

	@Column(name = "ATTACH_URL", length = 200, nullable = false)
	public String getAttachUrl() {
		return attachUrl;
	}
	public void setAttachUrl(String attachUrl) {
		this.attachUrl = attachUrl;
	}

	@Column(name = "USER_ID",nullable = false)
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 400, nullable = false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "ATTACH_TYPE", length = 10)
	public String getAttachType() {
		return attachType;
	}
	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	@Column(name = "UPLOAD_DATE", length = 20)
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "BUS_TABLE_NAME", length = 50, nullable = false)
	public String getBusTableName() {
		return busTableName;
	}
	public void setBusTableName(String busTableName) {
		this.busTableName = busTableName;
	}

	@Column(name = "BUS_DATA_ID", length = 50, nullable = false)
	public String getBusDataId() {
		return busDataId;
	}
	public void setBusDataId(String busDataId) {
		this.busDataId = busDataId;
	}

}
