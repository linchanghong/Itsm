package com.sccl.itsm.assessManager.dto;

import com.sccl.framework.entity.QueryEntity;

public class AssessDTO extends QueryEntity {
	
	private Integer userId;          	 //支撑服务人员Id
	private Integer serviceStaff;       //支撑服务人员Id
	private Integer problemCount;     //问题处理数
	private Integer bugCount;         //bug处理数
	private Integer demandCount;      //需求处理数
	private Long reporter;            //报告人
	private Integer dailyCount;       //日报数
	private Integer weeklyCount;      //周报数
	private Integer examiner;            //考官ID
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getServiceStaff() {
		return serviceStaff;
	}
	public void setServiceStaff(Integer serviceStaff) {
		this.serviceStaff = serviceStaff;
	}
	public Integer getProblemCount() {
		return problemCount;
	}
	public void setProblemCount(Integer problemCount) {
		this.problemCount = problemCount;
	}
	public Integer getBugCount() {
		return bugCount;
	}
	public void setBugCount(Integer bugCount) {
		this.bugCount = bugCount;
	}
	public Integer getDemandCount() {
		return demandCount;
	}
	public void setDemandCount(Integer demandCount) {
		this.demandCount = demandCount;
	}
	public Long getReporter() {
		return reporter;
	}
	public void setReporter(Long reporter) {
		this.reporter = reporter;
	}
	public Integer getDailyCount() {
		return dailyCount;
	}
	public void setDailyCount(Integer dailyCount) {
		this.dailyCount = dailyCount;
	}
	public Integer getWeeklyCount() {
		return weeklyCount;
	}
	public void setWeeklyCount(Integer weeklyCount) {
		this.weeklyCount = weeklyCount;
	}
	public Integer getExaminer() {
		return examiner;
	}
	public void setExaminer(Integer examiner) {
		this.examiner = examiner;
	}
	
	
	
}
