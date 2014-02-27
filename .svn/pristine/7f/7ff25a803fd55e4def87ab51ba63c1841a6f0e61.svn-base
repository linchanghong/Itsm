package com.sccl.framework.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "MS_LOG", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findLogByUserCode",query="SELECT l FROM MsLog l WHERE l.userCode=:userCode order by l.id"),
    @NamedQuery(name="findAllLogOrdered",query="SELECT l FROM MsLog l WHERE 1=1 ORDER BY l.id")
    })
public class MsLog extends TableEntity implements Serializable {

	private static final long serialVersionUID = -8718017118231574478L;
	
	private String id;
	private String userCode;
	private String className;
	private String methodName;
	private String userBehave;
	private String isSucceed;
	private String ipAddress;
	private String theContent;
	private String parameters;
	private Date createtime;

	// Constructors

	/** default constructor */
	public MsLog() {
	}

	// Property accessors
	@Id    
	@Column(name = "LOG_ID", nullable = false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USER_CODE", length = 20)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "CLASS_NAME", length = 200)
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "METHOD_NAME", length = 50)
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "USER_BEHAVE", length = 500)
	public String getUserBehave() {
		return userBehave;
	}

	public void setUserBehave(String userBehave) {
		this.userBehave = userBehave;
	}

	@Column(name = "IS_SUCCEED", length = 20)
	public String getIsSucceed() {
		return this.isSucceed;
	}

	public void setIsSucceed(String isSucceed) {
		this.isSucceed = isSucceed;
	}

	@Column(name = "IP_ADDRESS", length = 100)
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "THE_CONTENT", length = 500)
	public String getTheContent() {
		return this.theContent;
	}

	public void setTheContent(String theContent) {
		this.theContent = theContent;
	}

	@Column(name = "PARAMETERS", length = 500)
	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		if(parameters != null && parameters.length() >= 500) {
			this.parameters = parameters.substring(0, 499);
		} else {
			this.parameters = parameters;
		}
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "CREATETIME", nullable = false)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}