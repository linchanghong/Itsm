/**
 * @Title: BugManagerUtil.java
 * @Package com.sccl.serviceManager.util
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-27 下午1:41:18
 * @version:V1.0 
 */
package com.sccl.serviceManager.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.sccl.framework.DataManager;
import com.sccl.serviceManager.bugManager.entity.ServiceOrder;
import com.sccl.serviceManager.bugManager.entity.UserApply;
import com.sccl.serviceManager.demandManager.entity.SubDemand;
import com.sccl.serviceManager.demandManager.entity.SubDemandHomePage;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.util.BugManagerUtil.java
 * @ClassName: BugManagerUtil
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-27
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-27 下午1:41:18
 * @Version:v1.0
 */
public class BugManagerUtil {

    /**
     * 失败
     */
    private static final String ERROR = "0";
    
    private DataManager dataManager;
    
    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public BugManagerUtil(){}
    
    public BugManagerUtil(DataManager dataManager){
        this.dataManager = dataManager;
    }
    
    /**
     * 
     * @Title: createServiceOrder
     * @Description:根据传入的对象数据封装一个ServiceOrder对象。
     * @param obj bug、问题、子需求
     * @param flag 0:bug、问题；1：子需求
     * @return ServiceOrder        返回类型
     * @throws
     */
    public static ServiceOrder createServiceOrder(Object obj, int flag){
        ServiceOrder order = new ServiceOrder();
        if(flag == 0){
            UserApply apply = (UserApply)obj;
            order.setApplyId(apply.getApplyId());
            order.setDr(0);
            order.setPlanFinishDate(apply.getPlanFinishTime());
            order.setRealFinishDate(apply.getRealFinishTime());
            order.setReplyDate(apply.getReplyTime());
            order.setServiceStaff(apply.getReplyer());
            order.setServiceType(apply.getApplyType());
        }else{
            SubDemand demand = (SubDemand)obj;
            order.setApplyId(demand.getUserDemandId());
            order.setDr(0);
            order.setPlanFinishDate(demand.getPlanAnalysisDate());
            order.setRealFinishDate(demand.getRealAnalysisDate());
            order.setServiceStaff(demand.getAnalyst());
            order.setServiceType(3);
        }
        return order;
    }
    
    public static void loadingStatusDirection(List<SubDemandHomePage> subDemandHomePages){
        for (SubDemandHomePage subDemand : subDemandHomePages) {

            switch (subDemand.getDemandStatus()!=null?subDemand.getDemandStatus().intValue():0) {
            case 1:
                subDemand.status = "需求分析";
                break;
            case 2:
                subDemand.status = "开发经理指派人员";
                break;
            case 3:
                subDemand.status = "开发";
                break;
            case 4:
                subDemand.status = "单元测试";
                break;
            case 5:
                subDemand.status = "功能测试";
                break;
            case 6:
                subDemand.status = "项目经理审核";
                break;
            case 7:
                subDemand.status = "完成";
                break;
            default:
                subDemand.status = "未提交";
                break;
            }
        }
    }
    
    /**
     * 
     * @Title: createBugApplyCode
     * @Description:根据传入的请求单种类标志值，创建一个code
     * @param flag
     * @return String        返回类型
     * @throws
     */
  public String createBugApplyCode(int flag) {
  String flagStr = "BG";
  flagStr = judgeType(flag);
  String codes = findAllApplyCodes(flag);
  List<String> codesLstList = new Gson().fromJson(codes, ArrayList.class);
  List<Long> codeNums = new ArrayList<Long>();
  
  for (String str : codesLstList) {
      str = str.split(flagStr).length == 2 ? str.split(flagStr)[1] : "";
      if(!"".equals(str) && str.matches("\\d{12}"))
          codeNums.add(Long.parseLong(str));
  }
  
  Collections.sort(codeNums);
  
  Long maxVal = 0l;
  String applyCode = flagStr;
  Date date = new Date();
  String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
  String year = dateString.split("-")[0];
  String month = dateString.split("-")[1];
  String day = dateString.split("-")[2];
  
  Long dateNum = Long.parseLong(year+month+day);
  
  if(codeNums.size() >= 1)
      maxVal = codeNums.get(codeNums.size()-1);
      
      if(Math.floor(maxVal/10000) == dateNum){
          Long num =  maxVal % 10000 + 1;
          num = dateNum * 10000 + num;
          applyCode += num;
      }else
          applyCode += dateNum + "0001";
      
  
  return applyCode;
}
  
  /**
   * 
   * @Title: judgeType
   * @Description:通过传入的标志值返回code首两位
   * @param flag
   * @return String        返回类型
   * @throws
   */
  private String judgeType(int flag){
      String flagStr = "BG";
      if(flag == 1)
          flagStr = "WT";
      if(flag == 3)
          flagStr = "XQ";
      return flagStr;
  }
  
  /**
   * 
   * @Title: findAllApplyCodes
   * @Description:根据传入的请求单类型查询对应的请求单的所有code
   * @param flag
   * @return String        返回类型
   * @throws
   */
  public String findAllApplyCodes(int flag) {
      String flagStr = "BG";
      
      flagStr = judgeType(flag);
      
      Date date = new Date();
      String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
      String year = dateString.split("-")[0];
      String month = dateString.split("-")[1];
      String day = dateString.split("-")[2];
      
      String dateStr = year+month+day;
      
      try {
          String sql = "select u.userApplyCode from UserApply u where u.userApplyCode like '"+flagStr+dateStr+"%'";
          List<String> codes = dataManager.createQuery(sql, String.class).getResultList();
          return new Gson().toJson(codes);
      } catch (Exception e) {
          e.printStackTrace();
          return ERROR;
      }
  }
    
}
