package com.sccl.framework.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "MS_USER", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findUserByName",query="SELECT distinct u FROM MsUser u where u.userName=:userName"),
    @NamedQuery(name="findUserByUserCode",query="SELECT distinct u FROM MsUser u where u.userCode=:userCode"),
    @NamedQuery(name="findUserByOrgId",query="SELECT u FROM MsUser u left join u.msPerson p  where p.organization.id=:orgId"),
    @NamedQuery(name="MsUser.findByOrgId",query="SELECT u FROM MsUser u WHERE u.msPerson.organization.id=:orgId or u.msPerson.organization.parentPath like :orgIdLike order by u.id, u.msPerson.organization.id"),
    @NamedQuery(name="MsUser.findByOrgIdCount",query="SELECT count(u) FROM MsUser u WHERE u.msPerson.organization.id=:orgId or u.msPerson.organization.parentPath like :orgIdLike order by u.id, u.msPerson.organization.id"),
    @NamedQuery(name="MsUser.changPassWord",query="UPDATE MsUser u SET u.password=:password where u.id=:id")
    
    })
public class MsUser extends TableEntity implements java.io.Serializable {

	private static final long serialVersionUID = -629044386078040898L;

	@Expose private Integer id;
	@Expose private String userName;
	@Expose private String userCode;
	@Expose private String password;
	
	//0业务员，只操作本机构的业务；1业务管理员，可操作本机构及管理机构的业务，拥有部分系统设置权限；2管理员，默认可分配所有权限，拥有所有系统设置权限，但没有业务权限；
	@Expose private Integer isAdmin; 
	@Expose private Integer isEnable;
	@Expose private String loginUid;// 登录串，唯一区别一次登录，用于单用户登录
	@Expose private Integer isSingleLogin; //是否单用户登录
	@Expose private Date createtime;
	@Expose private String remark;
	@Expose private String email;
	@Expose private MsPerson msPerson;
	@Expose private String manageDepartment;
	private List<Organization> organizations; //如果是管理员，可管理的机构
	@Expose private List<MsRole> msRoles;

	public MsUser() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_CUS_USERS")
	@SequenceGenerator(name = "GEN_CUS_USERS", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "USER_ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USER_NAME", length = 40, nullable = false)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_CODE", length = 20, nullable = false)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "PASSWORD", length = 40, nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "IS_ENABLE", nullable = false)
	public Integer getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "CREATETIME", nullable = false)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "LOGIN_UID", length = 40, nullable = false)
	public String getLoginUid() {
		return this.loginUid;
	}

	public void setLoginUid(String loginUid) {
		this.loginUid = loginUid;
	}

	@Column(name = "IS_SINGLE_LOGIN")
	public Integer getIsSingleLogin() {
		return isSingleLogin;
	}

	public void setIsSingleLogin(Integer isSingleLogin) {
		this.isSingleLogin = isSingleLogin;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	public MsPerson getMsPerson() {
		return msPerson;
	}

	public void setMsPerson(MsPerson msPerson) {
		this.msPerson = msPerson;
	}

	@ManyToMany
	@JoinTable(name = "USER_ROLE", 
		joinColumns = @JoinColumn(name= "USER_ID"), 
		inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	public List<MsRole> getMsRoles() {
		return msRoles;
	}
	public void setMsRoles(List<MsRole> msRoles) {
		this.msRoles = msRoles;
	}

	@Column(name = "IS_ADMIN")
	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	@OneToMany
	@JoinTable(name="USER_ORG", 
		joinColumns=@JoinColumn(name="USER_ID"), 
		inverseJoinColumns=@JoinColumn(name="ORG_ID"))
	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	@Column(name = "MANAGE_DEPARTMENT", length = 500)
	public String getManageDepartment() {
		return manageDepartment;
	}

	public void setManageDepartment(String manageDepartment) {
		this.manageDepartment = manageDepartment;
	}
}