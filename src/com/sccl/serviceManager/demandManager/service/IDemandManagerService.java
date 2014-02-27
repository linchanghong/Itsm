package com.sccl.serviceManager.demandManager.service;

import com.sccl.serviceManager.bugManager.dto.SearchInfoDTO;
import com.sccl.serviceManager.bugManager.entity.UserApplyAdd;
import com.sccl.serviceManager.demandManager.entity.Demand4Flow;
import com.sccl.serviceManager.demandManager.entity.SubDemand;
import com.sccl.serviceManager.demandManager.entity.SubDemand4Mod;



/**
 * 
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.demandManager.service.IDemandManagerService.java
 * @ClassName: IDemandManagerService
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-27
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-27 上午11:41:08
 * @Version:v1.0
 */
public interface IDemandManagerService {
    
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
    public String initHomePageFast4SelectApply(int flag, int lines, int pageNum, boolean isCount);
    
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
    public String initSubDemanHomePageFast(int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: addDemandUserApply
     * @Description:新增需求单
     * @param apply
     * @param compId
     * @param personId
     * @return Long        返回类型
     * @throws
     */
   public Long addDemandUserApply(UserApplyAdd apply, String compId, String personId);
    
   /**
    * 
    * @Title: showDemand4FlowByID
    * @Description:根据id返回一个主需求单--主要用于流程页面，减轻程序负担
    * @param demandID
    * @return String        返回类型
    * @throws
    */
   public String showDemand4FlowByID(Long demandID);
   
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
   public short judgeAllSubDemandsIsSubmit(Long demandID);
   
   /**
    * 
    * @Title: modifyDemand4DemandFlow
    * @Description:主需求流程页面修改：向数据库存入“开发经理”、“计划完成时间”、
    * “受理时间”、“需求经理”，修改“主需求单状态”
    * @param demand4Flow
    * @return String        返回类型
    * @throws
    */
   public String modifyDemand4DemandFlow(Demand4Flow demand4Flow);
   
   /**
    * 
    * @Title: addSubDemandUserApply
    * @Description:新增一条子需求单，并返回新增子需求单的id
    * @param apply
    * @param compId
    * @param personId
    * @return Long        返回类型
    * @throws
    */
   public Long addSubDemandUserApply(SubDemand4Mod apply, String compId, String personId);
   
   /**
    * 
    * @Title: showSubDemandByID
    * @Description:跟据子需求单的id查询出一条子需求单
    * @param subDemandID
    * @return String        返回类型
    * @throws
    */
   public String showSubDemandByID(Long subDemandID);
   
   /**
    * 
    * @Title: updateSubDemandStatusNoDataBack
    * @Description:修改子需求单的状态
    * @param subDemandID
    * @param status
    * @return String        返回类型
    * @throws
    */
   public String updateSubDemandStatusNoDataBack(Long subDemandID, int status);
   
   /**
    * 
    * @Title: updateSubDemandUserApply
    * @Description:在子需求单未提交时，可以修改
    * @param subDemandString
    * @return String        返回类型
    * @throws
    */
   public String updateSubDemandUserApply(String subDemandString);
   
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
   public String initDemandAssess4JobGrades(String jobGradeTypeID, String demandsLink, String userDemandID);
   
   /**
    * 
    * @Title: initJobGradesByJobType
    * @Description:通过考评指标种类初始化考评指标
    * @param jobGradeTypeID
    * @return String        返回类型
    * @throws
    */
   public String initJobGradesByJobType(String jobGradeTypeID);
   
   /**
    * 
    * @Title: addAssess
    * @Description:添加评价
    * @param addAssess
    * @return String        返回类型
    * @throws
    */
   public String addAssess(String addAssess);
   
   /**
    * 
    * @Title: addAssesses
    * @Description:添加评价
    * @param addAssesses
    * @return String        返回类型
    * @throws
    */
   public String addAssesses(String addAssesses);
   
   /**
    * 
    * @Title: getPersonIdBySubDemandId
    * @Description:根据子需求id返回子需求单的子需求发起人id
    * @param subDemandID
    * @return String        返回类型
    * @throws
    */
   public String getPersonIdBySubDemandId(String subDemandID);
   
   /**
    * 
    * @Title: delSubDemandById
    * @Description:根据子需求单的id删除该请求单--将DR设置为1
    * @param subDemandID
    * @return String        返回类型
    * @throws
    */
   public String delSubDemandById(Long subDemandID);
   
   /**
    * 
    * @Title: judgeIsPMByUserId
    * @Description:判断当前用户是否为“项目经理”
    * @param personId
    * @return String        返回类型
    * @throws
    */
   public String judgeIsPMByUserId(int personId);
   
   /**
    * 
    * @Title: getUserIdByPersonId
    * @Description:通过人员id获取对应的用户id，因为请求单里面存储的是用户id。
    * @param personId
    * @return String        返回类型
    * @throws
    */
   public int getUserIdByPersonId(int personId);
   
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
   public String findSubDemandsByQueryWindow(
           SearchInfoDTO searchInfo, int lines, int pageNum, boolean isCount);
   
   /**
    * 
    * @Title: updateDeveloperAndUnitTester
    * @Description:填写开发人员和单元测试人员
    * @param demand_id,developer_id,unitTester_id
    * @return String        返回类型
    * @throws
    */
   public String updateDeveloperAndUnitTester(String demand_id, int developer_id, int unitTester_id);
   
   /**
    * 
    * @Title: updatePlanFinishDate
    * @Description:公共方法：填写实际需求完成时间、实际开发完成时间、实际单元测试完成时间、实际功能测试完成时间
    * @param developer_id,unitTester_id
    * @return String        返回类型
    * @throws
    */
   public String updateRealFinishDate(String demand_id, int flag);
}
