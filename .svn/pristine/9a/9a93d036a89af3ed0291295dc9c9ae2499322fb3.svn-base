package com.sccl.itsm.assessManager.service;

import com.sccl.itsm.assessManager.entity.WorkReport;

public interface IWorkReportService {
	
	public Long addWorkReport(WorkReport workReport,String compId, String personId);
	public Long updateWorkReport(String workReportJson);
	public String deleteWorkReport(String workReportId);
	public String statusWorkReport(String workReportId,String status);
	public String findAllWorkReportPage(String sqlWhere,int first,int size, boolean isCount);
	public String findWorkReportById(String workReportId);

}
