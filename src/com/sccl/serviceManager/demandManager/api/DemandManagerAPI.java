package com.sccl.serviceManager.demandManager.api;


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
import com.sccl.flow.vo.DataInteraction;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.service.log.Log;
import com.sccl.serviceManager.bugManager.dto.SearchInfoDTO;
import com.sccl.serviceManager.bugManager.entity.UserApplyAdd;
import com.sccl.serviceManager.demandManager.entity.Demand4Flow;
import com.sccl.serviceManager.demandManager.entity.SubDemand;
import com.sccl.serviceManager.demandManager.entity.SubDemand4Mod;
import com.sccl.serviceManager.demandManager.service.IDemandManagerService;
import com.sun.jersey.api.core.InjectParam;

/**
 * 
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.facade.BugManagerAPI.java
 * @ClassName: BugManagerAPI
 * @Description: 服务器端与界面的交互类
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-26
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-26 下午2:37:01
 * @Version:v1.0
 */
@RemotingDestination
@Component("demandManagerAPI")
@Path(value = "/frame/demand")
public class DemandManagerAPI {
    
    @InjectParam private IDemandManagerService demandManagerService;


    /**
     * 
     * @Title: initHomePageFast4SelectApply
     * @Description:加载请求单forselect
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/homePageFast/select/{flag}/{lines}/{pageNum}/{isCount}")
    @Produces(value = {"application/json"})
    public String initHomePageFast4SelectApply(@PathParam(value = "flag") int flag, 
            @PathParam(value = "lines") int lines, 
            @PathParam(value = "pageNum") int pageNum, 
            @PathParam(value = "isCount") boolean isCount){
        return demandManagerService.initHomePageFast4SelectApply(flag, lines, pageNum, isCount);
    }
    
    /**
     * 
     * @Title: initSubDemanHomePageFast
     * @Description:初始化自需求模块主界面表格
     * @param lines
     * @param pageNum
     * @param isCount
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/homePageFast/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
    public String initSubDemanHomePageFast(
            @PathParam(value = "pageNum") int pageNum, 
            @PathParam(value = "lines") int lines, 
            @PathParam(value = "isCount") boolean isCount){
        return demandManagerService.initSubDemanHomePageFast(lines, pageNum, isCount);
    }
    
    /**
     * 
     * @Title: addDemandUserApply
     * @Description:新增一条主需求单
     * @param userApplyJson
     * @return String        返回类型
     * @throws
     */
    @POST
    @Path(value = "/userApply/add/addDemand")
    @Transactional
    @Log
    public String addDemandUserApply(String userApplyJson){
        
        Gson gson = StaticMethods.getDateGson();
        
      Long type = (long) -2;
      
      UserApplyAdd apply = null;
      try {
          apply = gson.fromJson(userApplyJson, UserApplyAdd.class);
          String compId = apply.getApplyEntry().companyID.toString().trim();
          String personId = apply.getApplyEntry().personId.toString().trim();
          
          type = demandManagerService.addDemandUserApply(apply, compId, personId);
      } catch (IllegalArgumentException e) {
          e.printStackTrace();
      }
      
      DataInteraction di = new DataInteraction();
      di.setType(type.intValue());
      return gson.toJson(di);
      
    }
    
    /**
     * 
     * @Title: showDemand4FlowByID
     * @Description:根据id返回一个主需求单--主要用于流程页面，减轻程序负担
     * @param demandID
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/Demand/id/{demandID}")
    @Produces(value = {"application/json"})
    public String showDemand4FlowByID(
            @PathParam(value = "demandID") Long demandID){
        return demandManagerService.showDemand4FlowByID(demandID);
    }
    
    /**
     * 
     * @Title: judgeAllSubDemandsIsSubmit
     * @Description:该方法用于主需求流程项目经理添加子需求页面，
    * 用于判断主需求的的子需求是否所有子需求单已经“完成”，
    * 以限定是否可以审核通过。
     * @param demandID
     * @return short        返回类型
     * @throws
     */
    @GET
    @Path(value = "/Demand/id/judge/{demandID}")
    @Produces(value = {"application/json"})
    public short judgeAllSubDemandsIsSubmit(
            @PathParam(value = "demandID") Long demandID){
        return demandManagerService.judgeAllSubDemandsIsSubmit(demandID);
    }
    
    /**
     * 
     * @Title: modifyDemand4DemandFlow
     * @Description:主需求流程页面修改：向数据库存入“开发经理”、“计划完成时间”、
     * “受理时间”、“需求经理”，修改“主需求单状态”
     * @param demandString
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/userApply/mod/modifyDemand4DemandFlow")
    public String modifyDemand4DemandFlow(String demandString){
        Demand4Flow demand4Flow = StaticMethods.getDateGson().fromJson(demandString, Demand4Flow.class);
        return demandManagerService.modifyDemand4DemandFlow(demand4Flow);
    }
    
    /**
     * 
     * @Title: addSubDemandUserApply
     * @Description:新增一条子需求单，并返回新增子需求单的id
     * @param subDemandString
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/userApply/add/addSubDemand")
    public String addSubDemandUserApply(String subDemandString){

        Gson gson = StaticMethods.getDateGson();
        
      Long type = (long) -2;
      
      SubDemand4Mod apply = null;
      try {
          apply = gson.fromJson(subDemandString, SubDemand4Mod.class);
          String compId = apply.getSponsor().companyID.toString().trim();
          String personId = apply.getSponsor().personId.toString().trim();
          
          type = demandManagerService.addSubDemandUserApply(apply, compId, personId);
      } catch (IllegalArgumentException e) {
          type = (long) -2;
      }
      
      DataInteraction di = new DataInteraction();
      di.setType(type.intValue());
      return gson.toJson(di);
      
    }
    
    /**
     * 
     * @Title: showSubDemandByID
     * @Description:跟据子需求单的id查询出一条子需求单
     * @param subDemandID
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/id/{subDemandID}")
    @Produces(value = {"application/json"})
    public String showSubDemandByID(
            @PathParam(value = "subDemandID") Long subDemandID){
        return demandManagerService.showSubDemandByID(subDemandID);
    }
    
    /**
     * 
     * @Title: updateSubDemandStatusNoDataBack
     * @Description:修改子需求单的状态
     * @param subDemandID
     * @param status
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/userApply/mod/modSubDemand/status/{subDemandID}/{status}")
    public String updateSubDemandStatusNoDataBack(
            @PathParam(value = "subDemandID") Long subDemandID, 
            @PathParam(value = "status") int status){
        return demandManagerService.updateSubDemandStatusNoDataBack(subDemandID, status);
    }
    
    /**
     * 
     * @Title: updateSubDemandUserApply
     * @Description:在子需求单未提交时，可以修改
     * @param subDemandString
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/userApply/mod/modSubDemand")
    public String updateSubDemandUserApply(String subDemandString){
        return demandManagerService.updateSubDemandUserApply(subDemandString);
    }
    
    /**
     * 
     * @Title: initDemandAssess4JobGrades
     * @Description:加载本环节的本业务需求的评价集合
     * @param jobGradeTypeID
     * @param demandsLink
     * @param userDemandID
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/jobGrade/id/{jobGradeTypeID}/{demandsLink}/{userDemandID}")
    @Produces(value = {"application/json"})
    public String initDemandAssess4JobGrades(
            @PathParam(value = "jobGradeTypeID") String jobGradeTypeID,
            @PathParam(value = "demandsLink") String demandsLink,
            @PathParam(value = "userDemandID") String userDemandID){
        return demandManagerService.initDemandAssess4JobGrades(jobGradeTypeID,demandsLink,userDemandID);
    }
    
    /**
     * 
     * @Title: initJobGradesByJobType
     * @Description:通过考评指标种类初始化考评指标
     * @param jobGradeTypeID
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/jobGrade/id/{jobGradeTypeID}")
    @Produces(value = {"application/json"})
    public String initJobGradesByJobType(
            @PathParam(value = "jobGradeTypeID") String jobGradeTypeID){
        return demandManagerService.initJobGradesByJobType(jobGradeTypeID);
    }
    
    /**
     * 
     * @Title: addAssess
     * @Description:新增评价信息
     * @param demandAssess
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/SubDemand/Assess/add/addAssess")
    public String addAssess(String demandAssess){
        return demandManagerService.addAssess(demandAssess);
    }
    
    /**
     * 
     * @Title: addAssesses
     * @Description:新增评价信息
     * @param demandAssesses
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/SubDemand/Assesses/add/addAssesses")
    public String addAssesses(String demandAssesses){
        return demandManagerService.addAssesses(demandAssesses);
    }
    
    /**
     * 
     * @Title: getPersonIdBySubDemandId
     * @Description:通过子需求单ID查询出其“需求发起人”
     * @param subDemandID
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/getPerson/{subDemandID}")
    @Produces(value = {"application/json"})
    public String getPersonIdBySubDemandId(
            @PathParam(value = "subDemandID") String subDemandID){
        return demandManagerService.getPersonIdBySubDemandId(subDemandID);
    }
    
    /**
     * 
     * @Title: delSubDemandById
     * @Description：根据子需求单的id删除该请求单--将DR设置为1
     * @param subDemandID
     * @return String        返回类型
     * @throws
     */
    @DELETE
    @Transactional
    @Path(value = "/SubDemand/id/del/{subDemandID}")
    public String delSubDemandById(
            @PathParam(value = "subDemandID") Long subDemandID){
        return demandManagerService.delSubDemandById(subDemandID);
    }
    
    /**
     * 
     * @Title: judgeIsPMByUserId
     * @Description:判断当前用户是否为“项目经理”
     * @param userId
     * @return String        返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/judge/isPM/{userId}")
    @Produces(value = {"application/json"})
    public String judgeIsPMByUserId(
            @PathParam(value = "userId") String userId){
        int id = Integer.parseInt(userId!=null?userId.trim():"-1");
        return demandManagerService.judgeIsPMByUserId(id);
    }
    
    @GET
    @Path(value = "/apply/getId/user/{personId}")
    @Produces(value = {"application/json"})
    public String getUserIdByPersonId(
            @PathParam(value = "personId") String personIdString){
        try {
            int personId = Integer.parseInt(personIdString != null ? personIdString.trim() : "-1");
            int userId = demandManagerService.getUserIdByPersonId(personId);
            return userId+"";
        } catch (Exception e) {
            return "0";
        }
    }
    
    /**
     * 
     * @Title: findSubDemandsByQueryWindow
     * @Description:用户自定义查询子需求单
     * @param searchInfo 查询条件对象json数组
     * @param lines 每页显示多少行
     * @param pageNum 第几页
     * @param isCount 是否返回总行数
     * @return 
     * @retunType:String      返回类型
     * @throws
     */
    @GET
    @Path(value = "/SubDemand/query/window/{searchInfo}/{pageNum}/{lines}/{isCount}")
    @Produces(value = {"application/json"})
    public String findSubDemandsByQueryWindow(
//          @ModelAttribute("searchInfo") @Valid SearchInfoDTO searchInfo, 
          @PathParam(value = "searchInfo") String searchInfoJson,
          @PathParam(value = "pageNum") int pageNum, 
          @PathParam(value = "lines") int lines,
          @PathParam(value = "isCount") boolean isCount){
        SearchInfoDTO searchInfo = 
                StaticMethods.getDateGson().fromJson(searchInfoJson, SearchInfoDTO.class);
            String applies = demandManagerService.findSubDemandsByQueryWindow(
                    searchInfo, lines, pageNum, isCount);
            return applies;
    }
    
    /**
     * 
     * @Title: updateDeveloperAndUnitTester
     * @Description:填写开发人员和单元测试人员
     * @param demand_id,developer_id,unitTester_id
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/SubDemand/update/DeveloperAndUnitTester/{demand_id}/{developer_id}/{unitTester_id}")
    public String updateDeveloperAndUnitTester(
            @PathParam(value = "demand_id") String demand_id,
            @PathParam(value = "developer_id") int developer_id, 
            @PathParam(value = "unitTester_id") int unitTester_id){
        return demandManagerService.updateDeveloperAndUnitTester(demand_id,developer_id, unitTester_id);
    }
    
    /**
     * 
     * @Title: updatePlanFinishDate
     * @Description:公共方法：填写实际需求完成时间、实际开发完成时间、实际单元测试完成时间、实际功能测试时间
     * @param demand_id,flag
     * @return String        返回类型
     * @throws
     */
    @Log
    @POST
    @Transactional
    @Path(value = "/SubDemand/update/RealFinish/{demand_id}/{flag}")
    public String updateRealFinishDate(
            @PathParam(value = "demand_id") String demand_id, 
            @PathParam(value = "flag") int flag){
        return demandManagerService.updateRealFinishDate(demand_id, flag);
    }
}