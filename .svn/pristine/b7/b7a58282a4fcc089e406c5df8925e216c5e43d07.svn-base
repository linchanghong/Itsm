/**
 * @Title: IAnalyzesService.java
 * @Package com.sccl.analyzes.service
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-15 下午3:28:53
 * @version:V1.0 
 */
package com.sccl.serviceManager.supportSystemManager.service;

import com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.analyzes.service.IAnalyzesService.java
 * @ClassName: IAnalyzesService
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-15
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-15 下午3:28:53
 * @Version:v1.0
 */
public interface ISupportSystemManagerService {
    
    /**
     * 
     * @Title: initHomePage
     * @Description:表格初始化
     * @param pageNum 第几页
     * @param lines 每页显示多少行
     * @param isCount 是否分页
     * @return String        返回类型
     * @throws
     */
    public String initHomePage(int pageNum, int lines, boolean isCount);
    
    /**
     * 
     * @Title: initUserStatuses
     * @Description:加载数据库存储的员工职位列表
     * @return String        返回类型
     * @throws
     */
    public String initUserStatuses();

    /**
     * @Title: addSysManagerEntity
     * @Description:新增数据
     * @param sysEntity
     * @return String        返回类型
     * @throws 
     */
    public String addSysManagerEntity(SystemManagerEntity sysEntity);

    /**
     * @Title: modSysManagerEntity
     * @Description:修改数据
     * @param sysEntity
     * @return String        返回类型
     * @throws 
     */
    public String modSysManagerEntity(SystemManagerEntity sysEntity);

    /**
     * @Title: delSysManagerEntity
     * @Description:删除关联配置数据
     * @param sysEntity
     * @return String        返回类型
     * @throws 
     */
    public String delSysManagerEntity(SystemManagerEntity sysEntity);

    /**
     * 
     * @Title: findSysManagerEntityByQueryWindow
     * @Description:查询弹窗组件搜索信息
     * @param sysID 系统id
     * @param userID 人员id
     * @param statusID 职位id
     * @param pageNum 第几页
     * @param lines 每页显示多少行
     * @param isCount 是否分页
     * @return String        返回类型
     * @throws
     */
    public String findSysManagerEntityByQueryWindow(int sysID, int userID,
            int statusID, int pageNum, int lines, boolean isCount);
}
