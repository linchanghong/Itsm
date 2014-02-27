/**
 * @Title: KnowledgeInfo.java
 * @Package com.sccl.serviceManager.knowledgeManager.entity
 * @Description: TODO-
 * @author Liaoxuwei  
 * @date 2013-9-05 下午17:00:20
 * @version:V1.0 
 */
package com.sccl.serviceManager.knowledgeManager.entity;

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
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.SystemModule;
import com.sccl.serviceManager.bugManager.entity.User;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.knowledgeManager.entity.KnowledgeInfo.java
 * @ClassName: KnowledgeInfo
 * @Description: TODO-
 * @author:Liaoxuwei
 * @UpdateUser:Liaoxuwei
 * @UpdateDate:2013-9-06
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-06 上午11:13:20
 * @Version:v1.0
 */
@XmlRootElement
@Entity
@Table(name = "T_KNOWLEDGE_INFO", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="findKnowledgeInfoByUserId", query="SELECT k FROM KnowledgeInfo k where k.dr=0 and k.publishStaff.id=:userId"),
    @NamedQuery(name="findKnowledgeInfos", query="SELECT k FROM KnowledgeInfo k where k.dr=0"),
    @NamedQuery(name="KnowledgeInfo.examine",query="UPDATE KnowledgeInfo k SET k.isExamine=:examineId where k.id=:id"),
    @NamedQuery(name="KnowledgeInfo.delete",query="UPDATE KnowledgeInfo k SET k.dr=1 where k.id=:id")
    })
public class KnowledgeInfo extends TableEntity implements Serializable {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5769018309746356452L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 知识标题
     */
    private String knowledgeTitle;
    /**
     * 所属系统
     */
    private SupportSystem knowledgeType;
	/**
	 * 关联业务
	 */
    private SystemModule belongsBusiness;
	/**
	 * 是否审核 1审核，0未审核
	 */
    private Integer isExamine;
    
	/**
	 * 知识内容
	 */
    private String knowledgeContent;
	/**
	 * 发布日期
	 */
    private Date publishDate;
    /**
	 * 修改日期
	 */
    private Date modifyDate;
	/**
	 * 发布人
	 */
    private User publishStaff;
	/**
	 * 删除标志0未删除1删除
	 */
    private Integer dr;
	
    public String examineName;
	/**
     * @Title:KnowledgeInfo
     * @Description:无参构造
     */
    public KnowledgeInfo() {
        super();
    }
    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_KNOWLEDGE_INFO")
	@SequenceGenerator(name = "GEN_KNOWLEDGE_INFO", sequenceName = "SEQ_KNOWLEDGE_INFO", allocationSize = 1)
    @Column(name = "KNOWLEDGE_ID", nullable = false)
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name = "KNOWLEDGE_TITLE")
	public String getKnowledgeTitle() {
		return knowledgeTitle;
	}
	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}
	
	@ManyToOne
    @JoinColumn(name="KNOWLEDGE_TYPE")
	public SupportSystem getKnowledgeTyep() {
		return knowledgeType;
	}
	public void setKnowledgeTyep(SupportSystem knowledgeType) {
		this.knowledgeType = knowledgeType;
	}
	
	@ManyToOne
    @JoinColumn(name="BELONGS_BUSINESS")
	public SystemModule getBelongsBusiness() {
		return belongsBusiness;
	}
	public void setBelongsBusiness(SystemModule belongsBusiness) {
		this.belongsBusiness = belongsBusiness;
	}
	
	@Column(name = "IS_EXAMINE")
	public Integer getIsExamine() {
		return isExamine;
	}
	public void setIsExamine(Integer isExamine) {
		this.isExamine = isExamine;
	}
	
	
	
	@Column(name = "KNOWLEDGE_CONTENT")
	public String getKnowledgeContent() {
		return knowledgeContent;
	}
	public void setKnowledgeContent(String knowledgeContent) {
		this.knowledgeContent = knowledgeContent;
	}
	
	@Column(name = "PUBLISH_DATE")
	@Temporal(TemporalType.DATE)
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	@ManyToOne
    @JoinColumn(name="PUBLISH_STAFF")
	public User getPublishStaff() {
		return publishStaff;
	}
	public void setPublishStaff(User publishStaff) {
		this.publishStaff = publishStaff;
	}
	
	@Column(name = "DR")
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	
	@Column(name = "MODIFY_DATE")
	@Temporal(TemporalType.DATE)
	public Date getModifyDate(){
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate){
		this.modifyDate=modifyDate;
	}
	

}