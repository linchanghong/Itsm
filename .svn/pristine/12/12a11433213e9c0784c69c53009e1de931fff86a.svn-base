package com.sccl.itsm.assessManager.service;

import com.sccl.itsm.assessManager.entity.AssessScheme;

public interface IAssessSchemeService {
	public Long addAssessScheme(AssessScheme assessScheme,String compId, String personId);
	public Long updateAssessScheme(AssessScheme assessScheme);
	public String deleteAssessScheme(String assessSchemeId);
	public String statusAssessScheme(String assessSchemeId,String status);
	public String findAllAssessSchemePage(String sqlWhere,int first,int size, boolean isCount);
	public String findAssessSchemeById(String assessSchemeId);
	public String findAllPeriodPage(String sqlWhere,int first,int size, boolean isCount);
	public String findAllPeriod();
	public int findSchemeByPeriodId(String periodId);
}
