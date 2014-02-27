package com.sccl.serviceManager.bugManager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "T_SERVICE_ORDER", schema = "ITSM")
public class ServiceOrder extends TableEntity implements Serializable {
    
	// Fields
	
	/**
	 * @Description: TODO-
	 */
	private static final long serialVersionUID = 2545851117634498333L;

	/**
	 * 支撑服务单id
	 */
	private Long serviceOrderId;
	
	/**
	 * 支撑服务单编号
	 **/
	private String serviceOrderCode;
	
	/**
	 * 服务支撑人员
	 * */
	private User serviceStaff;
	
	/**
	 * 服务受理时间
	 * */
	private Date replyDate;
	
	/**
	 * 处理完成时间
	 * */
	private Date realFinishDate;
	
	/**
	 * 用户评价
	 * */
	private Integer assess;
	
	/**
	 * 评价说明
	 * */
	private String assessRemark;
	
	/**
	 * 服务单状态
	 * */
	private Integer orderStatus;
	
	/**
	 * 服务单类型	1:问题。2：Bug。3：需求
	 * */
	private Integer serviceType;
	
	/**
	 * 请求主键
	 * */
	private Long applyId;
	
	/**
	 * 计划解决时间
	 * */
	private Date planFinishDate;
	
	/**
	 *  删除标志
	 * */
	private Integer dr;
	
    // Constructors
	
	/** default constructor */
	public ServiceOrder() {
		
	}
	
	
    // Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SERVICE_ORDER")
	@SequenceGenerator(name = "GEN_SERVICE_ORDER", sequenceName = "SEQ_SERVICEORDER", allocationSize = 1)
	@Column(name = "SERVICE_ORDER_ID", nullable = false)
	public Long getServiceOrderId() {
		return serviceOrderId;
	}

	public void setServiceOrderId(Long serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	@Column(name = "SERVICE_ORDER_CODE", length = 100)
	public String getServiceOrderCode() {
		return serviceOrderCode;
	}

	public void setServiceOrderCode(String serviceOrderCode) {
		this.serviceOrderCode = serviceOrderCode;
	}

	@ManyToOne
    @JoinColumn(name="SERVICE_STAFF")
	public User getServiceStaff() {
		return serviceStaff;
	}

	public void setServiceStaff(User serviceStaff) {
		this.serviceStaff = serviceStaff;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "REPLY_DATE", length = 7)
	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "REAL_FINISH_DATE", length = 7)
	public Date getRealFinishDate() {
		return realFinishDate;
	}

	public void setRealFinishDate(Date realFinishDate) {
		this.realFinishDate = realFinishDate;
	}

	@Column(name = "ASSESS")
	public Integer getAssess() {
		return assess;
	}

	public void setAssess(Integer assess) {
		this.assess = assess;
	}

	@Column(name = "ASSESS_REMARK", length = 1000)
	public String getAssessRemark() {
		return assessRemark;
	}

	public void setAssessRemark(String assessRemark) {
		this.assessRemark = assessRemark;
	}

	@Column(name = "ORDER_STATUS")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "SERVICE_TYPE")
	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	@Column(name = "APPLY_ID")
	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "PLAN_FINISH_DATE", length = 7)
	public Date getPlanFinishDate() {
		return planFinishDate;
	}

	public void setPlanFinishDate(Date planFinishDate) {
		this.planFinishDate = planFinishDate;
	}

	@Column(name = "DR")
	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}
	
}
