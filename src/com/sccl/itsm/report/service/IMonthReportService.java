package com.sccl.itsm.report.service;

import com.sccl.itsm.report.entity.MonthReport;

public interface IMonthReportService {
	public Long addMonthReport(MonthReport monthReport,String compId, String personId);
	public Long updateMonthReport(String monthReportJson);
	public String deleteMonthReport(String monthReportId);
	public String statusMonthReport(String monthReportId,String status);
	public String findAllMonthReportPage(String sqlWhere,int first,int size, boolean isCount);
	public String findMonthReportById(String monthReportId);
}
