/**
 * @Title: UserApply.java
 * @Package com.sccl.serviceManager.bugManager.dto
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-7-28 下午4:57:32
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.sccl.framework.entity.Attachment;
import com.sccl.serviceManager.bugManager.entity.User;
import com.sccl.serviceManager.bugManager.entity.UserApply;
//import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sccl.serviceManager.demandManager.entity.SubDemand;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.dto.UserApply.java
 * @ClassName: UserApply
 * @Description: UserApply数据传输对象，用于服务器与客户端数据交互
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-28
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-28 下午4:57:32
 * @Version:v1.0
 */

public class UserApplyDTO extends UserApply implements Serializable {

    /**
     * 用户请求对象中的文档集合
     */
    public List<Attachment> attachments = new ArrayList<Attachment>();
    /**
     * 主需求单的子需求单集--3:需求单
     */
    public List<SubDemand> subDemands = new ArrayList<SubDemand>();
    /**
     * 请求单状态描述
     */
    public String applyStatusStr = "未提交";
//    /**
//     * 请求单的填写者，可以是用户，也可以是支撑人员
//     */
//    private String applyEntryName = "";
//    
//    /**
//     * 受理人
//     */
//    private String replyerName = "";
//    
//    /**
//     * 提出请求的具体用户
//     */
//    private String sponsorName = "";
    
    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = 1037939832679450840L;

    /**
     * @Title:UserApply
     * @Description:
     */
    public UserApplyDTO() {
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @return the attachments
//     */
//    public List<Attachment> getAttachments() {
//        return attachments;
//    }
//
//    /**
//     * @param list the attachments to set
//     */
//    public void setAttachments(List<Attachment> attachments) {
//        this.attachments = attachments;
//    }
//
//    /**
//     * @return the applyStatusStr
//     */
//    public String getApplyStatusStr() {
//        return applyStatusStr;
//    }
//
//    /**
//     * @param applyStatusStr the applyStatusStr to set
//     */
//    public void setApplyStatusStr(String applyStatusStr) {
//        this.applyStatusStr = applyStatusStr;
//    }
    
    public void changeToUserApplyDTO4Demand(UserApply userApply,
            List<Attachment> attachments, List<SubDemand> subDemands){
        this.setApplyEndDate(userApply.getApplyEndDate());
        this.setApplyEntry(userApply.getApplyEntry());
        this.setApplyOrigin(userApply.getApplyOrigin());
        this.setApplyReason(userApply.getApplyReason());
        this.setApplyStartDate(userApply.getApplyStartDate());
        this.setApplyEndDate(userApply.getApplyEndDate());
        this.setApplyStatus(userApply.getApplyStatus());
        this.setApplyTitle(userApply.getApplyTitle());
        this.setApplyType(userApply.getApplyType());
        this.setBelongSystem(userApply.getBelongSystem());
        this.setCompany(userApply.getCompany());
        this.setDepartment(userApply.getDepartment());
        this.setDirections(userApply.getDirections());
        this.setDR(userApply.getDR());
        this.setPlanFinishTime(userApply.getPlanFinishTime());
        this.setRange(userApply.getRange());
        this.setRealFinishTime(userApply.getRealFinishTime());
        this.setRealRange(userApply.getRealRange());
        this.setReason(userApply.getReason());
        this.setReplyer(userApply.getReplyer());
        this.setReplyTime(userApply.getReplyTime());
        this.setSolutions(userApply.getSolutions());
        this.setSponsor(userApply.getSponsor());
        this.setSysModule(userApply.getSysModule());
        this.setTelephone(userApply.getTelephone());
        this.setApplyId(userApply.getApplyId());
        this.setUrgent(userApply.getUrgent());
        this.setUserApplyCode(userApply.getUserApplyCode());
//        this.setAttachments(attachments 
//                == null || attachments.size() == 0?this.attachments:attachments);
        this.attachments = attachments 
                == null || attachments.size() == 0?this.attachments:attachments;
        this.subDemands = 
                subDemands == null || subDemands.size() == 0?this.subDemands:subDemands;
        /*由于在数据库中请求单状态只是存储的一个数字,所以要在首页的表格中显示汉字
                    需要在这里进行逻辑判断传值*/
        switch (this.getApplyStatus()!=null?this.getApplyStatus():0) {
        case 1:
            this.applyStatusStr = "未受理";
            break;
        case 2:
            this.applyStatusStr = "处理中";
            break;
        case 3:
            this.applyStatusStr = "完成";
            break;
            
        default:
            break;
        }
        
        
    }

    public void changeToUserApplyDTO(UserApply userApply,
            List<Attachment> attachments, 
            List<User> userLst){
        this.setApplyEndDate(userApply.getApplyEndDate());
        this.setApplyEntry(userApply.getApplyEntry());
        this.setApplyOrigin(userApply.getApplyOrigin());
        this.setApplyReason(userApply.getApplyReason());
        this.setApplyStartDate(userApply.getApplyStartDate());
        this.setApplyEndDate(userApply.getApplyEndDate());
        this.setApplyStatus(userApply.getApplyStatus());
        this.setApplyTitle(userApply.getApplyTitle());
        this.setApplyType(userApply.getApplyType());
        this.setBelongSystem(userApply.getBelongSystem());
        this.setCompany(userApply.getCompany());
        this.setDepartment(userApply.getDepartment());
        this.setDirections(userApply.getDirections());
        this.setDR(userApply.getDR());
        this.setPlanFinishTime(userApply.getPlanFinishTime());
        this.setRange(userApply.getRange());
        this.setRealFinishTime(userApply.getRealFinishTime());
        this.setRealRange(userApply.getRealRange());
        this.setReason(userApply.getReason());
        this.setReplyer(userApply.getReplyer());
        this.setReplyTime(userApply.getReplyTime());
        this.setSolutions(userApply.getSolutions());
        this.setSponsor(userApply.getSponsor());
        this.setSysModule(userApply.getSysModule());
        this.setTelephone(userApply.getTelephone());
        this.setApplyId(userApply.getApplyId());
        this.setUrgent(userApply.getUrgent());
        this.setUserApplyCode(userApply.getUserApplyCode());
//        this.setAttachments(attachments 
//                == null || attachments.size() == 0?this.attachments:attachments);
        this.attachments = attachments 
                == null || attachments.size() == 0?this.attachments:attachments;
        
        /*由于在数据库中请求单状态只是存储的一个数字,所以要在首页的表格中显示汉字
                    需要在这里进行逻辑判断传值*/
        switch (this.getApplyStatus()!=null?this.getApplyStatus():0) {
        case 1:
            this.applyStatusStr = "未受理";
            break;
        case 2:
            this.applyStatusStr = "处理中";
            break;
        case 3:
            this.applyStatusStr = "完成";
            break;
            
        default:
            break;
        }
        
        
    }
    
}
