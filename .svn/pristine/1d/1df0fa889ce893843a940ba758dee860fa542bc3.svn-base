/**
 * @Title: IBugManagerService.java
 * @Package com.sccl.serviceManager.bugManager
 * @Description: bug处理业务层的服务接口文件
 * @author Howie_Mark   
 * @date 2013-7-23 上午11:07:57
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.service;


import com.sccl.serviceManager.bugManager.dto.SearchInfoDTO;
import com.sccl.serviceManager.bugManager.entity.ServiceOrder;
import com.sccl.serviceManager.bugManager.entity.UserApplyAdd;


/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.IBugManagerService.java
 * @ClassName: IBugManagerService
 * @Description: bug处理业务层的服务接口，主要对bug管理界面的用户操作进行后台回应
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-23
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-23 上午11:07:57
 * @Version:v1.0
 */
public interface IBugManagerService {
    
   /**
    * 
    * @Title: initHomePage
    * @Description:bugMnager主页的数据初始化，需要分页，默认显示10条
    * @param flag
    * @return 
    * @retunType:String      返回类型
    * @throws:
    */
    public String initHomePage(int flag);
    
    /**
     * 
     * @Title: initHomePage
     * @Description:查询出bugManager模块的首页表格数据
     * @param flag 2：bug管理，1：问题管理
     * @param lines 每页显示多少行
     * @param pageNum 第几页
     * @param isCount 是否返回总行数
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initHomePage(int flag, int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: initHomePageFast
     * @Description:快速查询出bugManager模块的首页表格数据(只是查询出主页表格所需数据)
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initHomePageFast(int flag, int lines, int pageNum, boolean isCount);
    
    
    /**
     * 
     * @Title: getPersonIdByApplyId
     * @Description:根据请求单id查询人员id
     * @param applyId
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String getPersonIdByApplyId(int applyId);
    
//    /**
//     * 
//     * @Title: initAttachments
//     * @Description:初始化查看、更改请求单页面的文档表格，分页
//     * @param userApplyID 请求单id
//     * @param lines 每页显示多少行
//     * @param pageNum 第几页
//     * @param isCount 是否返回总行数
//     * @return 
//     * @retunType:String      返回类型
//     * @throws:
//     */
//    public String initAttachments(String userApplyID, int lines, int pageNum, boolean isCount);
    
//    /**
//     * 
//     * @Title: queryAttachmentsByApplyIDAndPage
//     * @Description:根据请求单ID分页查询其文档集合
//     * @param applyID 请求单ID
//     * @param lines 每页显示多少行
//     * @param pageNum 第几页
//     * @return 
//     * @retunType:String      返回类型
//     * @throws:
//     */
//    public String queryAttachmentsByApplyIDAndPage(String applyID, int lines, int pageNum);
    
   /**
    * 
    * @Title: queryUserAppliesByPage
    * @Description:bugManager主页的分页插叙
    * @param flag 判断是查询bug还是question
     * @param lines 每页显示多少条数据
     * @param pageNum 需要第几页数据
    * @return 
    * @retunType:String      返回类型
    * @throws:
    */
    public String queryUserAppliesByPage(int flag, int lines, int pageNum);
    
    /**
     * 
     * @Title: querUserAppliesByLike
     * @Description:bugManager页面的主页模糊查询
     * @param type bug还是问题
     * @param flag 查询标志值，这里传的是页面传过来的数值标志值：0-按编号，1-按所属系统，
     *             2-主题摘要，3-重要程度，4-影响范围，5-报告人，6-责任人，7-状态
     *             如果有这么多条件，请调用本类的私有方法
     * @param queryStr 用户所输入的查询内容
     * @param lines 每页显示多少行
     * @param pageNum 第几页
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String querUserAppliesByLike(
            int type, int flag, String queryStr, int lines, int pageNum);
    
    /**
     * 
     * @Title: querUserAppliesByLike
     * @Description:bug管理界面的模糊查询
     * @param type bug还是问题
     * @param flag 查询标志值
     * @param queryStr 查询内容
     * @param lines 每页显示多少行
     * @param pageNum 第几页
     * @param isCount 是否返回总行数
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String querUserAppliesByLike(
            int type, int flag, String queryStr, int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: showUserApllyByID
     * @Description:通过一个请求单ID查询一个请求单的相关信息（包括其对应的文档）
     * @param applyIDStr
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String showUserApllyByID(Long applyIDStr);
    
    /**
     * 
     * @Title: getSubDemandsByUserApplyID
     * @Description:通过主需求单查询所有子需求单
     * @param applyIDStr
     * @param lines
     * @param pageNum
     * @param isCount
     * @return String        返回类型
     * @throws
     */
    public String getSubDemandsByUserApplyID(Long applyIDStr, int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: getSubDemandsByApplyID
     * @Description:通过主需求单查询所有子需求单
     * @param applyIDStr
     * @param lines
     * @param pageNum
     * @param isCount
     * @return String        返回类型
     * @throws
     */
    public String getSubDemandsByApplyID(Long applyIDStr, int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: showUserApllyByCode
     * @Description:通过一个请求单编码查询一个请求单的相关信息（包括其对应的文档）
     * @param userApplyCode
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String showUserApllyByCode(String userApplyCode);
    
//    /**
//     * 
//     * @Title: findAllApplyCodes
//     * @Description:就是为了不再另外创建一张表的前提下构建code
//     * @param flag
//     * @return 
//     * @retunType:String      返回类型
//     * @throws:
//     */
//    public String findAllApplyCodes(int flag);
    
    /**
     * 
     * @Title: initBugSource
     * @Description:bugManager增、删、改页面的bug来源下拉单初始化
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initBugSource();
    
    /**
     * 
     * @Title: initBugScope
     * @Description:bugManager增、删、改页面的bug影响范围下拉单初始化
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initBugScope();
    
    /**
     * 
     * @Title: initLevels
     * @Description:bugManager增、删、改页面的bug重要程度下拉单初始化
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initLevels();
    
    /**
     * 
     * @Title: initSupportSystems
     * @Description:bugManager增、删、改页面的所属系统下拉单初始化
     * @return 
     * @retunType:List<SupportSystem>      返回类型
     * @throws:
     */
    public String initSupportSystems();
    
    /**
     * 
     * @Title: initSupportSystemsByPage
     * @Description:分页查询业务系统，主要运用与系统选择弹窗组件
     * @param pageNum 第几页
     * @param lines 每页显示多少行
     * @param isCount 是否分页
     * @return String        返回类型
     * @throws
     */
    public String initSupportSystemsByPage(int pageNum, int lines, boolean isCount);
    
    /**
     * 
     * @Title: initSupportSystemsByName
     * @Description:用户输入系统名称模糊查询
     * @param sysName 系统名称
     * @param pageNum 第几页
     * @param lines 每页显示多少行
     * @param isCount 是否分页
     * @return String        返回类型
     * @throws
     */
    public String initSupportSystemsByName(String sysName, int pageNum, int lines, boolean isCount);
    
    /**
     * 
     * @Title: initSystemModulesByPage
     * @Description:根据系统分页查询出对应的模块
     * @param sysID 系统id
     * @param pageNum 第几页
     * @param lines 每页显示多少行
     * @param isCount 是否分页
     * @return String        返回类型
     * @throws
     */
    public String initSystemModulesByPage(int sysID, int pageNum, int lines, boolean isCount);
    
    /**
     * 
     * @Title: initSystemModulesByName
     * @Description:根据系统id和模块名称模糊分页查询
     * @param moduleName 模块名称
     * @param sysID 系统id
     * @param pageNum 第几页
     * @param lines 每页显示多少行
     * @param isCount 是否分页
     * @return String        返回类型
     * @throws
     */
    public String initSystemModulesByName(String moduleName, int sysID, int pageNum, int lines, boolean isCount);
    
    /**
     * 
     * @Title: initSystemModules
     * @Description:bugManager增、删、改页面的所属业务下拉单初始化
     * @param systemId
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initSystemModules(int systemId);
    
    /**
     * 
     * @Title: addBugUserApply
     * @Description:新增请求单
     * @param apply
     * @param compId
     * @param personId
     * @return 
     * @retunType:Long     返回类型
     * @throws:
     */
    public Long addBugUserApply(UserApplyAdd apply, String compId, String personId);
    
//    /**
//     * 
//     * @Title: createBugApplyCode
//     * @Description:根据需求单类型和数据库code创建一个新的code
//     * @param flag
//     * @return String        返回类型
//     * @throws
//     */
//    public String createBugApplyCode(int flag);

    /**
     * 
     * @Title: addBugUserApply
     * @Description:新增请求单
     * @param apply
     * @param compId
     * @param personId
     * @return 
     * @retunType:Long     返回类型
     * @throws:
     */
    public Long addProblemUserApply(UserApplyAdd apply, String compId, String personId);
    
    /**
     * 
     * @Title: updateBugUerApplyAdd
     * @Description:再次呈报页面的数据存储
     * @param userApplyAddJson
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String updateBugUerApplyAdd(String userApplyAddJson);
    
    /**
     * 
     * @Title: updateBugUerApply
     * @Description:修改请求
     * @param userApplyJson
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String updateBugUerApply(String userApplyJson);
    
    /**
     * 
     * @Title: updateUerApplyStatus
     * @Description:修改请求单的状态
     * @param applyID
     * @param status
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String updateUerApplyStatus(Long applyID, int status);
    
    /**
     * 
     * @Title: updateUerApplyStatusNoDataBack
     * @Description:修改请求单的状态
     * @param applyID
     * @param status
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String updateUerApplyStatusNoDataBack(Long applyID, int status);
    
    /**
     * 
     * @Title: addOrUpdateUerApply
     * @Description:新增或是修改请求单
     * @param userApplyJson
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String addOrUpdateUerApply(String userApplyJson);
    
    
    
    /**
     * 
     * @Title: delUserApplyByID
     * @Description:根据请求单的id删除该请求单
     * @param applyID
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String delUserApplyByID(Long applyID);
    
    /**
     * 
     * @Title: delUserApply
     * @Description:根据传回的请求单对象删除
     * @param userApplyJson
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String delUserApply(String userApplyJson);
    
//    /**
//     * 
//     * @Title: delAttachmentById
//     * @Description:根据传入的文档ID删除相应的文档,更新表格信息
//     * @param attachmentId
//     * @param userApplyId
//     * @return 
//     * @retunType:String      返回类型
//     * @throws:
//     */
//    public String delAttachmentById(String attachmentId, Long userApplyId);

//    /**
//     * 
//     * @Title: delAttachment
//     * @Description:根据传回的文档对象删除
//     * @param attachmentJson
//     * @return 
//     * @retunType:String      返回类型
//     * @throws:
//     */
//    public String delAttachment(String attachmentJson);
    
//   /**
//    * 
//    * @Title: saveAttachment
//    * @Description:保存上传文档
//    * @param attachmentJson
//    * @return 
//    * @retunType:String      返回类型
//    * @throws:
//    */
//    public String saveAttachment(String attachmentJson);
    
    /**
     * 
     * @Title: initDropDownList4AddPage
     * @Description:从数据库传回数据初始化Add界面的相关值
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String initDropDownList4AddPage();
    
    /**
     * 
     * @Title: findUserAppliesByQueryWindow
     * @Description:用户自定义查询请求单
     * @param searchInfo 查询条件对象
     * @param lines 每页显示多少行
     * @param pageNum 第几页
     * @param isCount 是否返回总行数
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String findUserAppliesByQueryWindow(SearchInfoDTO searchInfo, int lines, int pageNum, boolean isCount);
      
    
    /**
     * 
     * @Title: agreeBugUserApplyFlow1st
     * @Description:第一次审批同意
     * @param billId
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String agreeBugUserApplyFlow1st(String billId);
    
    /**
     * 
     * @Title: setReplyer
     * @Description:初次审核页面,设置审核人
     * @param biilId
     * @param userId
     * @param planFinishDateStr
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String setReplyer(String biilId, int userId, String planFinishDateStr);
    
    /**
     * 
     * @Title: disagreeBugUserApplyFlow1st
     * @Description:第一次审批不同意
     * @param billId
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String disagreeBugUserApplyFlow1st(String billId);
    
    //==================================================================================
    //自己修改的后台方法
    /**
     * 
     * @Title: initMyHomePageFast
     * @Description:快速查询出bugManager模块的首页表格数据(只是查询出主页表格所需数据)
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return 
     * @retunType:String      返回类型
     * 
     * 
     * @throws:
     */
    public String initMyHomePageFast(int userId,int lines, int pageNum,boolean isCount);
    
    /**
     * 
     * @Title: findMyUserAppliesByQueryWindow
     * @Description:用户自定义查询请求单
     * @param searchInfo 查询条件对象
     * @param lines 每页显示多少行
     * @param pageNum 第几页
     * @param isCount 是否返回总行数
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String findMyUserAppliesByQueryWindow(SearchInfoDTO searchInfo, int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: addServiceOrder
     * @Description:新增请求单
     * @param serviceOrder
     * @return 
     * @retunType:Long     返回类型
     * @throws:
     */
    public Long addServiceOrder(ServiceOrder serviceOrder);    
    
    /**
     * 
     * @Title: uServiceHomePageFast
     * @Description:快速查询出bugManager模块的首页表格数据(只是查询出主页表格所需数据)
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String uServiceHomePageFast(int flag, int lines, int pageNum, boolean isCount);
    
    /**
     * 
     * @Title: addUProblemApply
     * @Description:新增请求单
     * @param apply
     * @param compId
     * @param personId
     * @return 
     * @retunType:Long     返回类型
     * @throws:
     */
    public Long addUProblemApply(UserApplyAdd apply, String compId, String personId);
    
    /**
     * 
     * @Title: addUBugApply
     * @Description:新增请求单
     * @param apply
     * @param compId
     * @param personId
     * @return 
     * @retunType:Long     返回类型
     * @throws:
     */
    public Long addUBugApply(UserApplyAdd apply, String compId, String personId);
    
    /**
     * 
     * @Title: addUDemandApply
     * @Description:新增请求单
     * @param apply
     * @param compId
     * @param personId
     * @return 
     * @retunType:Long     返回类型
     * @throws:
     */
    public Long addUDemandApply(UserApplyAdd apply, String compId, String personId);
}
