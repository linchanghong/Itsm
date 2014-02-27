package com.sccl.itsm.assessManager.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "T_ASSESS_SCHEME", schema = "ITSM")
@NamedQueries({
	@NamedQuery(name="AssessScheme.status",query="UPDATE AssessScheme a SET a.status=:statusId where a.id=:id"),
    @NamedQuery(name="AssessScheme.delete",query="UPDATE AssessScheme a SET a.dr=1 where a.id=:id"),
    @NamedQuery(name="AssessScheme.querByPeriod",query="select a from AssessScheme a where a.dr=0 and a.period.id=:id")
    })
public class AssessScheme extends TableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6633086689908480406L;
	
	private Long id;               //主键
    private String schemeName;          //方案名称
    private String schemeCode;        //方案编码
    private Period period;        //考核期间
    private Integer status;        //方案状态,0:未提交,1:考核中,2:已考核
    private Integer dr;            //删除标志
    private Date creatDate;        //创建日期
	
    public String statusName;
    /**
     * @Title:WorkReport
     * @Description:无参构造
     */
    public AssessScheme() {
        super();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ASSESS_SCHEME")
	@SequenceGenerator(name = "GEN_ASSESS_SCHEME", sequenceName = "SEQ_ASSESS_SCHEME", allocationSize = 1)
    @Column(name = "SCHEME_ID", nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "SCHEME_NAME")
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	
	@Column(name = "SCHEME_CODE")
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	
	@ManyToOne
    @JoinColumn(name="PERIOD_ID")
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	
	@Column(name = "SCHEME_STATUS")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "DR")
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	@Column(name = "CREAT_DATE")
	@Temporal(TemporalType.DATE)
	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	
	

    
}
