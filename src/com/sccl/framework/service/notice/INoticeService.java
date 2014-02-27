package com.sccl.framework.service.notice;

public interface INoticeService {
	/**
	 * 查询所有公告
	 * @return
	 */
	public String findAllNotice(int first, int size, boolean isCount);
	
	/**
	 * 新增公告
	 * @param json
	 * @return
	 */
	public String addNotice(String json);
	
	/**
	 * 更新公告
	 * @param json
	 * @return
	 */
	public String updateNotice(String json);
	
	/**
	 * 删除公告
	 * @param json
	 * @return
	 */
	public String deleteNotice(String json);
	
	/**
	 * 公告显示
	 * @return
	 */
	public String findAllNotice();
}
