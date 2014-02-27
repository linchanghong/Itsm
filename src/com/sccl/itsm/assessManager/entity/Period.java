package com.sccl.itsm.assessManager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "T_PERIOD", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="Period.findPeriods", query="SELECT p FROM Period p where p.dr=0"),
    @NamedQuery(name="Period.delete",query="UPDATE Period p SET p.dr=1 where p.id=:id")
    })
public class Period extends TableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4788546110511412892L;
	
	private Long id;               //主键
    private String periodName;          //期间名称
    private String periodCode;        //报告内容
    private Date startDate;        //开始日期
    private Date endDate;          //截止日期
    private Integer dr;            //删除标志
    
    /**
     * @Title:WorkReport
     * @Description:无参构造
     */
    public Period() {
        super();
    }

    @Id
    @GeneratedValue
    @Column(name = "PERIOD_ID", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PERIOD_NAME")
	public String getPeriodName() {
		return periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	@Column(name = "PERIOD_CODE")
	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "DR")
	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}
    
    

}
