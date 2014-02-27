package com.sccl.framework.entity;

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

/**
 * MsNotice entity. @author MyEclipse Persistence Tools
 */
@Entity
@SequenceGenerator(name = "GEN_MS_NOTICE", sequenceName = "SEQ_FRAME", allocationSize=1)
@Table(name = "MS_NOTICE", schema = "ITSM")
@NamedQueries({
	@NamedQuery(name="MsNotice.findAllNotice",query="SELECT a FROM MsNotice a where a.dr=:dr order by a.infoDate desc"),
	@NamedQuery(name="MsNotice.findAllNoticeCount",query="SELECT count(a) FROM MsNotice a WHERE a.dr=:dr order by a.infoDate desc"),
	@NamedQuery(name="MsNotice.findOneMonthNotice",query="SELECT a FROM MsNotice a where a.dr=:dr and infoDate>=:infoDate order by a.infoDate desc")
	
})
public class MsNotice extends TableEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7603245316156241805L;
	private Long id;
	private String title;
	private String content;
	private Date infoDate;
	private Boolean dr;

	// Constructors

	/** default constructor */
	public MsNotice() {
	}

	/** minimal constructor */
	public MsNotice(Long id) {
		this.id = id;
	}

	/** full constructor */
	public MsNotice(Long id, String title, String content, Date infoDate, Boolean dr) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.infoDate=infoDate;
		this.dr = dr;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "GEN_MS_NOTICE", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TITLE", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", length = 4000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INFODATE", nullable = false)
	public Date getInfoDate() {
		return this.infoDate;
	}

	public void setInfoDate(Date infoDate) {
		this.infoDate = infoDate;
	}
	
	@Column(name = "DR", precision = 2, scale = 0)
	public Boolean getDr() {
		return this.dr;
	}

	public void setDr(Boolean dr) {
		this.dr = dr;
	}

}