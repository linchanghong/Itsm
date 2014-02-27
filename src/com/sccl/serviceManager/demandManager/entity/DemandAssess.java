/**
 * @Title: DemandAssess.java
 * @Package com.sccl.serviceManager.demandManager.entity
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-10-10 下午9:32:21
 * @version:V1.0 
 */
package com.sccl.serviceManager.demandManager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.TableEntity;
import com.sccl.serviceManager.bugManager.entity.User;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.demandManager.entity.DemandAssess.java
 * @ClassName: DemandAssess
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-10-10
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-10-10 下午9:32:21
 * @Version:v1.0
 */
@Entity
@XmlRootElement
@Table(name = "T_DEMANDS_ASSESS", schema = "ITSM")
public class DemandAssess extends TableEntity implements Serializable {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = 2282047122212144915L;

    /**
     * 评价表主键
     */
    private Long assessID;
    /**
     * 需求分解表主键
     */
    private Long subDemandID;
    /**
     * 被考评人
     */
    private User assessor;
    /**
     * 分析环节、开发环节、单元测试环节、功能测试环节
     */
    private Integer demandLink;
    /**
     * 打分人
     */
    private User examiner;
    /**
     * 各个环节的打分指标
     */
    private ConstDetail indicator;
    /**
     * 分数
     */
    private Float score;
    /**
     * 打分说明
     */
    private String direction;
    /**
     * 是否删除
     */
    private Short dr;
    
    public DemandAssess(){}
    
    /**
     * @return the assessID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_DEMAND_ASSESS")
    @SequenceGenerator(name = "GEN_DEMAND_ASSESS", sequenceName = "SEQ_DEMAND_ASSESS", allocationSize = 1)
    @Column(name = "DEMANDS_ASSESS_ID", nullable = false)
    public Long getAssessID() {
        return assessID;
    }
    /**
     * @param assessID the assessID to set
     */
    public void setAssessID(Long assessID) {
        this.assessID = assessID;
    }
    /**
     * @return the subDemandID
     */
    @Column(name = "USER_DEMAND_ID")
    public Long getSubDemandID() {
        return subDemandID;
    }
    /**
     * @param subDemandID the subDemandID to set
     */
    public void setSubDemandID(Long subDemandID) {
        this.subDemandID = subDemandID;
    }
    /**
     * @return the assessor
     */
    @ManyToOne
    @JoinColumn(name="ASSESSOR")
    public User getAssessor() {
        return assessor;
    }
    /**
     * @param assessor the assessor to set
     */
    public void setAssessor(User assessor) {
        this.assessor = assessor;
    }
    /**
     * @return the demandLink
     */
    @Column(name = "DEMANDS_LINK")
    public Integer getDemandLink() {
        return demandLink;
    }
    /**
     * @param demandLink the demandLink to set
     */
    public void setDemandLink(Integer demandLink) {
        this.demandLink = demandLink;
    }
    /**
     * @return the examiner
     */
    @ManyToOne
    @JoinColumn(name="EXAMINER")
    public User getExaminer() {
        return examiner;
    }
    /**
     * @param examiner the examiner to set
     */
    public void setExaminer(User examiner) {
        this.examiner = examiner;
    }
    /**
     * @return the indicator
     */
    @ManyToOne
    @JoinColumn(name="INDICATOR")
    public ConstDetail getIndicator() {
        return indicator;
    }
    /**
     * @param indicator the indicator to set
     */
    public void setIndicator(ConstDetail indicator) {
        this.indicator = indicator;
    }
    /**
     * @return the score
     */
    @Column(name = "SCORE")
    public Float getScore() {
        return score;
    }
    /**
     * @param score the score to set
     */
    public void setScore(Float score) {
        this.score = score;
    }
    /**
     * @return the direction
     */
    @Column(name = "DIRECTIONS")
    public String getDirection() {
        return direction;
    }
    /**
     * @param direction the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    /**
     * @return the dr
     */
    @Column(name = "DR")
    public Short getDr() {
        return dr;
    }
    /**
     * @param dr the dr to set
     */
    public void setDr(Short dr) {
        this.dr = dr;
    }
    
}
