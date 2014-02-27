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
import javax.xml.bind.annotation.XmlRootElement;

import com.sccl.framework.entity.TableEntity;
import com.sccl.serviceManager.bugManager.entity.User;

@XmlRootElement
@Entity
@Table(name = "T_ASSESS", schema = "ITSM")
@NamedQueries({
	@NamedQuery(name="findAssessByUserId", query="SELECT w FROM Assess w where w.assessObj.id=:userId"),
    @NamedQuery(name="findAssess", query="SELECT w FROM Assess w ")
    })
public class Assess extends TableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4287924916816263434L;
	
	private Long id;                       //主键
    private AssessScheme scheme;           //考核方案
    private User assessObj;         	   //考核对象
    private Integer dailyCount;            //日报完成数
    private Integer weeklyCount;           //周报完成数
    private Integer monthlyCount;          //月报完成数
    private Integer troubleCount;          //问题处理数
    private Integer bugCount;              //bug处理数
    private Integer demandCount;           //需求处理数
    private float score;                   //考核分数
    private User examiner;         	       //考评人
    private String directions;             //评分说明
   
    /**
     * @Title:WorkReport
     * @Description:无参构造
     */
    public Assess() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ASSESS")
	@SequenceGenerator(name = "GEN_ASSESS", sequenceName = "SEQ_ASSESS", allocationSize = 1)
    @Column(name = "ASSESS_ID", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
    @JoinColumn(name="SCHEME_ID")
	public AssessScheme getScheme() {
		return scheme;
	}

	public void setScheme(AssessScheme scheme) {
		this.scheme = scheme;
	}

	@ManyToOne
    @JoinColumn(name="ASSESS_OBJ")
	public User getAssessObj() {
		return assessObj;
	}

	public void setAssessObj(User assessObj) {
		this.assessObj = assessObj;
	}

	@Column(name = "DAILY_COUNT")
	public Integer getDailyCount() {
		return dailyCount;
	}

	public void setDailyCount(Integer dailyCount) {
		this.dailyCount = dailyCount;
	}

	@Column(name = "WEEKLY_COUNT")
	public Integer getWeeklyCount() {
		return weeklyCount;
	}

	public void setWeeklyCount(Integer weeklyCount) {
		this.weeklyCount = weeklyCount;
	}

	@Column(name = "MONTHLY_COUNT")
	public Integer getMonthlyCount() {
		return monthlyCount;
	}

	public void setMonthlyCount(Integer monthlyCount) {
		this.monthlyCount = monthlyCount;
	}

	@Column(name = "TROUBLE_COUNT")
	public Integer getTroubleCount() {
		return troubleCount;
	}

	public void setTroubleCount(Integer troubleCount) {
		this.troubleCount = troubleCount;
	}

	@Column(name = "BUG_COUNT")
	public Integer getBugCount() {
		return bugCount;
	}

	public void setBugCount(Integer bugCount) {
		this.bugCount = bugCount;
	}

	@Column(name = "DEMAND_COUNT")
	public Integer getDemandCount() {
		return demandCount;
	}

	public void setDemandCount(Integer demandCount) {
		this.demandCount = demandCount;
	}

	@Column(name = "ASSESS_SCORE")
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@ManyToOne
    @JoinColumn(name="EXAMINER")
	public User getExaminer() {
		return examiner;
	}

	public void setExaminer(User examiner) {
		this.examiner = examiner;
	}

	@Column(name = "DIRECTIONS")
	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}
    
    

}
