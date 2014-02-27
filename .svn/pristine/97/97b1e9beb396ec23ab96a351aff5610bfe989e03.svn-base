package com.sccl.framework.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "MENU_BUTTON", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findMenuByRoleId",query="SELECT m FROM MenuButton m left join m.msRole r WHERE r.id=:roleId"),
    @NamedQuery(name="MenuButton.findOrdered",query="SELECT m FROM MenuButton m order by m.orderNum, m.id")
    })
public class MenuButton extends TableEntity implements java.io.Serializable {

	private static final long serialVersionUID = -848127900898569930L;
	
	@Expose private Integer id;
	@Expose private Integer type;// menu 1, button 2 menubutton 3;
	@Expose private String label;
	@Expose private Integer parentId; // 上级菜单
	@Expose private String parentPath; // 所有父节点路径，如：,1,2,3,4,5,6,
	@Expose private Integer orderNum; // 同级排序
	@Expose private String clazz; // 最低一级菜单 关联 的module文件路径
	@Expose private String btnId;// button 的id，比如add_btn，点击时会触发add_btn_handler事件；
	@Expose private String groupId; // button所在容器的id，加载时把按钮添加到此容器中；
	@Expose private String classId; // button的class属性，此属性在css中定义样式；
	@Expose private String state; // module的state，比如"apply,modify"，此按钮只在这两种状态下显示
	private List<MsRole> msRole;

	// Constructors
	public MenuButton() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MENU_BUTTON")
	@SequenceGenerator(name = "GEN_MENU_BUTTON", sequenceName = "SEQ_FRAME", allocationSize = 1)
	@Column(name = "MENU_ID", nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "LABEL", length = 50, nullable = false)
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "GROUP_ID", length = 30)
	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "CLASS_ID", length = 30)
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Column(name = "PARENT_ID", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "CLAZZ", length = 200)
	public String getClazz() {
		return this.clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Column(name = "BTN_ID", length = 30)
	public String getBtnId() {
		return this.btnId;
	}

	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "ORDER_NUM")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "STATE", length = 30)
	public String getState() {
		return state;
	}

	@ManyToMany(mappedBy = "menuButton")
	public List<MsRole> getMsRole() {
		return msRole;
	}

	public void setMsRole(List<MsRole> msRole) {
		this.msRole = msRole;
	}

	@Column(name = "PARENT_PATH", length = 500)
	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
}