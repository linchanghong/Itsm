/**
 * @Title: AnalyzesAPI.java
 * @Package com.sccl.analyzes.api
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-15 下午3:38:33
 * @version:V1.0 
 */
package com.sccl.serviceManager.supportSystemManager.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.serviceManager.bugManager.dto.SearchInfoDTO;
import com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity;
import com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService;
import com.sun.jersey.api.core.InjectParam;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.analyzes.api.AnalyzesAPI.java
 * @ClassName: AnalyzesAPI
 * @Description: TOD
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-15
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-15 下午3:38:33
 * @Version:v1.0
 */
@RemotingDestination
@Component("supportSystemManagerAPI")
@Path(value = "/frame/SupportSystemManager")
public class SupportSystemManagerAPI {

    @InjectParam private ISupportSystemManagerService supportSystemManagerService;
    
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
    @GET
    @Path(value = "/homePage/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
    public String initHomePage(
            @PathParam(value = "pageNum") int pageNum,
            @PathParam(value = "lines") int lines, 
            @PathParam(value = "isCount") boolean isCount) {
        String homeString = supportSystemManagerService.initHomePage(pageNum, lines, isCount);
        return homeString;
     }
    
    /**
     * 
     * @Title: initUserStatuses
     * @Description:加载数据库存储的员工职位列表
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "userStatus/init")
    @Produces(value = {"application/json"})
    public String initUserStatuses() {
        return supportSystemManagerService.initUserStatuses();
    }
    
    /**
     * 
     * @Title: addSysManagerEntity
     * @Description:新增数据
     * @param sysManagerEntityStr
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/add")
    public String addSysManagerEntity(String sysManagerEntityStr){
        SystemManagerEntity sysEntity = 
                new Gson().fromJson(sysManagerEntityStr, SystemManagerEntity.class);
        return supportSystemManagerService.addSysManagerEntity(sysEntity);
    }
    
    /**
     * 
     * @Title: modSysManagerEntity
     * @Description:修改数据
     * @param sysManagerEntityStr
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/mod")
    public String modSysManagerEntity(String sysManagerEntityStr){
        SystemManagerEntity sysEntity = 
                new Gson().fromJson(sysManagerEntityStr, SystemManagerEntity.class);
        return supportSystemManagerService.modSysManagerEntity(sysEntity);
    }
    
    /**
     * 
     * @Title: delSysManagerEntity
     * @Description:删除关联配置数据
     * @param sysManagerEntityStr
     * @return String        返回类型
     * @throws
     */
    @Log
    @DELETE
    @Transactional
    @Path(value = "/del")
    public String delSysManagerEntity(String sysManagerEntityStr) {
        SystemManagerEntity sysEntity = 
                StaticMethods.getDateGson().fromJson(sysManagerEntityStr, SystemManagerEntity.class);
        return supportSystemManagerService.delSysManagerEntity(sysEntity);
    }
    
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
    @GET
    @Path(value = "/search/{sysID}/{userID}/{statusID}/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
    public String findSysManagerEntityByQueryWindow(
            @PathParam(value = "sysID") int sysID,
            @PathParam(value = "userID") int userID,
            @PathParam(value = "statusID") int statusID,
            @PathParam(value = "pageNum") int pageNum, 
            @PathParam(value = "lines") int lines,
            @PathParam(value = "isCount") boolean isCount){
        String applies = supportSystemManagerService.findSysManagerEntityByQueryWindow(sysID, userID, statusID, pageNum, lines, isCount);
        return applies;
    }
}
