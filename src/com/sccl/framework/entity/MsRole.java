package com.sccl.framework.entity;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "MS_ROLE", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="MsRole.findAllJoinMenu",query="SELECT DISTINCT m FROM MsRole m LEFT JOIN m.menuButton b where 1=1 ORDER BY m.id"),
    @NamedQuery(name="MsRole.findAllJoinUser",query="SELECT DISTINCT m FROM MsRole m LEFT JOIN m.msUser b ORDER BY m.id"),
    @NamedQuery(name="MsRole.findAll",query="SELECT m FROM MsRole m")
    })
public class MsRole extends TableEntity implements Serializable {

	private static final long serialVersionUID = -6451528713169561545L;

	@Expose private Integer id;
	@Expose private String roleName;
	@Expose private Date createTime = new Date();
	@Expose private Integer parentId;
	@Expose private String parentPath; // 所有父节点路径，如：,1,2,3,4,5,6,
	private List<MsUser> msUser;
	@Expose private List<MenuButton> menuButton;

	public MsRole() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUS_ROLE_SEQ")
	@SequenceGenerator(name = "CUS_ROLE_SEQ", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "ROLE_ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME", length = 30, nullable = false)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "PARENT_ID", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@ManyToMany(mappedBy = "msRoles")
	public List<MsUser> getMsUser() {
		return msUser;
	}

	public void setMsUser(List<MsUser> user) {
		this.msUser = user;
	}

	@ManyToMany
	@JoinTable(name = "ROLE_MENU", 
		joinColumns = @JoinColumn(name = "ROLE_ID"),
		inverseJoinColumns = @JoinColumn(name = "MENU_ID"))
	public List<MenuButton> getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(List<MenuButton> menuButton) {
		this.menuButton = menuButton;
	}

	@Column(name = "PARENT_PATH", length = 500)
	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
}