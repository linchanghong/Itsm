package com.sccl.itsm.report.service;

import com.sccl.itsm.report.entity.YearReport;

public interface IYearReportService {

	public Long addYearReport(YearReport yearReport,String compId, String personId);
	public Long updateYearReport(String yearReportJson);
	public String deleteYearReport(String yearReportId);
	public String statusYearReport(String yearReportId,String status);
	public String findAllYearReportPage(String sqlWhere,int first,int size, boolean isCount);
	public String findYearReportById(String yearReportId);
	public String initYears();
}
