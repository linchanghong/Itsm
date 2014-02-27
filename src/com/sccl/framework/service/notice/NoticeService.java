package com.sccl.framework.service.notice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.gson.GsonBuilder;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.entity.MsNotice;
import com.sccl.framework.utils.Tools;

@SuppressWarnings("all")
@Component
public class NoticeService implements INoticeService {
	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	/**
	 * 查询所有公告
	 * 
	 * @return
	 */
	@Override
	public String findAllNotice(int first, int size, boolean isCount) {
		List list = new ArrayList();
		List<MsNotice> dataList = dataManager.createNamedQuery(
				"MsNotice.findAllNotice", MsNotice.class).setParameter("dr",
				false).setFirstResult(first).setMaxResults(size)
				.getResultList();
		list.add(0, dataList);
		if (isCount) {
			Long count = dataManager.createNamedQuery(
					"MsNotice.findAllNoticeCount", Long.class).setParameter(
					"dr", false).getSingleResult();
			list.add(1, count);
		}
		String json = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create().toJson(list);
		return json;
	}

	/**
	 * 新增公告
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public String addNotice(String json) {
		boolean success = false;
		try {
			MsNotice notice = Tools.getGson().fromJson(json, MsNotice.class);
			notice.setInfoDate(new Date());
			notice.setDr(false);
			dataManager.add(notice);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"success\":" + success + "}";
	}

	/**
	 * 更新公告
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public String updateNotice(String json) {
		boolean success = false;
		try {
			MsNotice notice = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd HH:mm:ss").create().fromJson(json,
					MsNotice.class);
			dataManager.update(notice);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"success\":" + success + "}";
	}

	/**
	 * 删除公告
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public String deleteNotice(String json) {
		boolean success = false;
		try {
			MsNotice notice = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd HH:mm:ss").create().fromJson(json,
					MsNotice.class);
			notice.setDr(true);
			dataManager.update(notice);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{\"success\":" + success + "}";
	}

	/**
	 * 公告显示
	 * 
	 * @return
	 */
	@Override
	public String findAllNotice() {
		List<MsNotice> list = dataManager.createNamedQuery(
				"MsNotice.findOneMonthNotice", MsNotice.class).setParameter(
				"dr", false).setParameter("infoDate", getOneMonthToday())
				.getResultList();
		String json = Tools.getGson().toJson(list);
		return json;
	}
	
	/**
	 * 得到一个月前今天
	 * @return
	 */
	private Date getOneMonthToday(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    

		Calendar cal=Calendar.getInstance(); 

		cal.add(Calendar.DATE, -1);    //得到前一天 
		cal.add(Calendar.MONTH, -1);    //得到前一个月       

		long date = cal.getTimeInMillis();    

		return new Date(date);
	}
}
