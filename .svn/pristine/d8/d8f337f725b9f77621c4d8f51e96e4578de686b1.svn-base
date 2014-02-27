/**
 * @Title: BugManagerServiceImpl.java
 * @Package com.sccl.serviceManager.bugManager.service
 * @Description: bug管理业务层实现类
 * @author Howie_Mark   
 * @date 2013-7-24 下午2:45:43
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.bcel.generic.Select;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sccl.flow.common.FlowTools;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.entity.Attachment;
import com.sccl.framework.entity.ConstDetail;
import com.sccl.framework.entity.MsUser;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.service.log.Log;
import com.sccl.serviceManager.bugManager.dto.SearchInfoDTO;
import com.sccl.serviceManager.bugManager.dto.SupportSystemDTO;
import com.sccl.serviceManager.bugManager.dto.UserApplyDTO;
import com.sccl.serviceManager.bugManager.entity.HomePage;
import com.sccl.serviceManager.bugManager.entity.ReplyLevel;
import com.sccl.serviceManager.bugManager.entity.ServiceOrder;
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.SystemModule;
import com.sccl.serviceManager.bugManager.entity.User;
import com.sccl.serviceManager.bugManager.entity.UserApply;
import com.sccl.serviceManager.bugManager.entity.UserApplyAdd;
import com.sccl.serviceManager.demandManager.entity.SubDemand;
import com.sccl.serviceManager.util.BugManagerUtil;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.service.BugManagerServiceImpl.java
 * @ClassName: BugManagerServiceImpl
 * @Description: IBugManagerService的实现类，主要是针对bugManager页面的一系列操作的业务处理
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-24
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-24 下午2:45:43
 * @Version:v1.0
 */
@Component("bugManagerService")
@SuppressWarnings("unchecked")
public class BugManagerServiceImpl implements IBugManagerService {
    
	  /**
     * 登录用户
     */
    private MsUser msUser;
	
    /**
     * 每页显示多少行，默认为15行
     */
    private static final int PAGE_LINE = 15;
    
    /**
     * 是否被删除的标志查询语句
     */
    private static final String DR_QUERY_STRING = " DR = 0 ";
    
    /**
     * 成功
     */
    private static final String SUCCESS = "1";
    
    /**
     * 失败
     */
    private static final String ERROR = "0";
    
    private static final int ERROR_NUM = -1;
    
    private DataManager dataManager;
    
    public DataManager getDataManager() {
        return dataManager;
    }

    @Resource(name = "dataManager")
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    
    
    @Override
    public String initHomePage(int flag) {
        flag = flag != 1 ? 2 : 1;
        return queryUserAppliesByPage(flag, 0, PAGE_LINE);
    }
    
    /* 
     * <p>Title: initHomePage</p>
     * <p>Description: </p>
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initHomePage(int, int, int, boolean)
     */
    @Override
    public String initHomePage(int flag, int lines, int pageNum, boolean isCount) {
        try {
            if(isCount)
                return queryUserAppliesByPage(flag, lines, pageNum);
            else
//                return new Gson().toJson(queryUserAppliesByPageExactly(flag, lines, pageNum));
                return StaticMethods.getDateGson().toJson(queryUserAppliesByPageExactly(flag, lines, pageNum));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /* 
     * <p>Title: initHomePageFast</p>
     * <p>Description: </p>
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initHomePageFast(int, int, int, boolean)
     */
    @Override
    public String initHomePageFast(int flag, int lines, int pageNum,
            boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            
            reList.add(queryUserAppliesByPageFastExactly(flag, lines, pageNum));
            if(isCount)
               reList.add(getPageCount(flag));
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /**
     * 
     * @Title: getPageCount
     * @Description:返回某种请求单的总数
     * @param flag
     * @return 
     * @retunType:Long      返回类型
     * @throws:
     */
    private Long getPageCount(int flag){
        try {
            return dataManager
                    .createQuery("select count(p) from UserApply p " +
                    		"where p.DR = 0 and p.applyType = " 
                            + flag, Long.class)
                            .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.parseLong(ERROR);
        }
    }
    
    /**
     * 
     * @Title: getPageCountByUserId
     * @Description:返回某种请求单的总数
     * @param flag
     * @return 
     * @retunType:Long      返回类型
     * @throws:
     */
    private Long getPageCountByUserId(int flag){
        try {
            return dataManager
                    .createQuery("select count(p) from UserApply p " +
                    		"where p.DR = 0 and p.sponsor.id = " 
                            + flag, Long.class)
                            .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.parseLong(ERROR);
        }
    }

    /**
     * @Title: queryUserAppliesByPageExactly
     * @Description:分页查询一个用户请求单集合
     * @param flag
     * @param lines
     * @param pageNum
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    private List<Object> queryUserAppliesByPageExactly(
            int flag, int pageNum, int lines) {
        List<Object> reList = new ArrayList<Object>();
        List<UserApply> applies = 
                dataManager.createQuery(
                        "from UserApply p where  p.applyType = " + flag + " " +
                        		"and p.DR = 0"+" order by p.userApplyCode",UserApply.class)
                        		.setFirstResult(pageNum).setMaxResults(lines)
                        		.getResultList();
        List<UserApplyDTO> dtos = getUserApplyDTOLst(applies);
        reList.add(dtos);
        return reList;
    }
    
    /**
     * 
     * @Title: queryUserAppliesByPageFastExactly
     * @Description:分页查询一个用户请求单集合(数据只限于主页表格)
     * @param flag
     * @param pageNum
     * @param lines
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    private List<HomePage> queryUserAppliesByPageFastExactly(
            int flag, int pageNum, int lines) {
        List<HomePage> applies = 
                dataManager.createQuery(
                        "from HomePage p where  p.applyType = " + flag + " " +
                                "and p.DR = 0"+" order by p.applyStatus ",HomePage.class)
                                .setFirstResult(pageNum).setMaxResults(lines)
                                .getResultList();
        for (HomePage homePage : applies) {

            switch (homePage.getApplyStatus()!=null?homePage.getApplyStatus():0) {
            case 1:
                homePage.applyStatusStr = "未受理";
                break;
            case 2:
                homePage.applyStatusStr = "处理中";
                break;
            case 3:
                homePage.applyStatusStr = "完成";
                break;
            default:
                homePage.applyStatusStr = "未提交";
                break;
            }
            
//            User applyEntry = homePage.getApplyEntry();
//            String personId = dataManager
//                    .createNativeQuery(
//                            "select mu.person_id " +
//                            "from ms_user mu " +
//                            "where mu.user_id = "+applyEntry.getId(), Integer.class)
//                            .toString().trim();
//            applyEntry.setPersonId(personId);
        }
        return applies;
    }
    
    /**
     * 
     * @Title: getUserApplyDTOLst
     * @Description:传入一个请求单集合以及文档对象，返回一个请求单数据传输对象集合
     * @param applies
     * @param attachments
     * @return 
     * @retunType:List<UserApplyDTO>      返回类型
     * @throws:
     */
    private List<UserApplyDTO> getUserApplyDTOLst(List<UserApply> applies) {
        List<UserApplyDTO> dtos = new ArrayList<UserApplyDTO>();
        for (UserApply userApply : applies) {
            List<User> userLst = new ArrayList<User>();
//            userLst =  getUserNames4UserApplyDTO(userApply);
            UserApplyDTO dto = new UserApplyDTO();//new UserApplyDTO();
            List<Attachment> attachments =
                    getAttachmentsByUserApplyID(userApply.getApplyId()!=null?userApply.getApplyId():0);
            dto.changeToUserApplyDTO(userApply,attachments, userLst);
           dtos.add(dto);
        }
        return dtos;
    }
    
//    /**
//     * 
//     * @Title: getUserNames4UserApplyDTO
//     * @Description:传入一个UserApply对象，返回一个List<MsUser>
//     * @param uApply
//     * @return 
//     * @retunType:List<MsUser>      返回类型
//     * @throws:
//     */
//    private List<User> getUserNames4UserApplyDTO(UserApply uApply){
//        List<User> msUsers = new ArrayList<User>();
//        try {
//            msUsers.add(getMsUserByID(uApply.getApplyEntry() != null?uApply.getApplyEntry().getId():0));
//            msUsers.add(getMsUserByID(uApply.getReplyer() != null?uApply.getReplyer().getId():0));
//            msUsers.add(getMsUserByID(uApply.getSponsor() != null?uApply.getSponsor().getId():0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return msUsers;
//    }
    
    
//    /**
//     * 
//     * @Title: getMsUserByID
//     * @Description:根据用户ID查询出用户信息
//     * @param id
//     * @return 
//     * @retunType:MsUser      返回类型
//     * @throws:
//     */
//    private User getMsUserByID(int id) {
//        try {
//            return (User)dataManager.findById(User.class, id);
//        } catch (Exception e) {
//            return null;
//        }
//    }
    
    /* 
     * <p>Title: querUserAppliesByLike</p>
     * <p>Description: </p>
     * @param type
     * @param flag
     * @param queryStr
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#querUserAppliesByLike(int, java.lang.String)
     */
    @Override
    public String querUserAppliesByLike(
            int type, int flag, String queryStr, int lines, int pageNum) {
        try {
            return queryUserAppliesByApplyTitleLike(type, flag, queryStr, lines, pageNum);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /* 
     * <p>Title: querUserAppliesByLike</p>
     * <p>Description: </p>
     * @param type
     * @param flag
     * @param queryStr
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#querUserAppliesByLike(int, int, java.lang.String, int, int, boolean)
     */
    @Override
    public String querUserAppliesByLike(int type, int flag, String queryStr,
            int lines, int pageNum, boolean isCount) {
        try {
            if(!isCount)
                return new Gson().toJson(
                        queryUserAppliesByApplyTitleLike4Like(type, flag, queryStr, lines, pageNum));
            else
                return querUserAppliesByLike(type, flag, queryStr, lines, pageNum);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 
     * @Title: queryUserAppliesByApplyTitleLike
     * @Description:通过主题摘要模糊查询
     * @param type
     * @param flag
     * @param queryString
     * @param lines
     * @param pageNum
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    private String queryUserAppliesByApplyTitleLike(
            int type, int flag, String queryStr, int lines, int pageNum){
        try {
            List<Object> reList = queryUserAppliesByApplyTitleLike4Like(type,
                    flag, queryStr, lines, pageNum);
            Long count = reList != null?((List<UserApplyDTO>)(reList.get(0))).size():0l;
                reList.add(1, count);
                return StaticMethods.getDateGson().toJson(reList);
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR;
            }
    }

    /**
     * @Title: queryUserAppliesByApplyTitleLike4Like
     * @Description:根据请求主题模糊查询
     * @param type
     * @param flag
     * @param queryStr
     * @param lines
     * @param pageNum
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    private List<Object> queryUserAppliesByApplyTitleLike4Like(
            int type, int flag, String queryStr, int pageNum, int lines) {
        List<Object> reList = new ArrayList<Object>();
      List<UserApply> applies =  new ArrayList<UserApply>();
      if(flag == 0)
          applies = dataManager.createQuery("select p from UserApply p " +
                  "where p.applyType = " + type  + 
                         " and (p.applyTitle LIKE '%"+ queryStr +"'" +
                          " OR p.applyTitle LIKE '" + queryStr + "%'" +
                          " OR p.applyTitle LIKE '%" + queryStr + "%')" +
                          " and"+ " p.DR = 0" +
                          " order by p.applyTd",
          UserApply.class).setFirstResult(pageNum).setMaxResults(lines).getResultList();
      List<UserApplyDTO> dtos = getUserApplyDTOLst(applies);
      reList.add(dtos);
      return reList;
    }

    /* 
     * <p>Title: showUserApllyByID</p>
     * <p>Description: </p>
     * @param applyIDStr
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#showUserApllyByID(java.lang.String)
     */
    @Override
    public String showUserApllyByID(Long applyIDStr) {
        try {
            
//            List<UserApply> applies =  new ArrayList<UserApply>();
//            applies = dataManager.createQuery("select p from UserApply p " +
//                        "where p.user_apply_id = " + applyIDStr + " and p.DR = 0" +
//                                " order by p.user_apply_code",
//                UserApply.class).getResultList();
            
            UserApply apply = null;
            List<Attachment> attachments = null;
//            List<SubDemand> subDemands = null;
            UserApplyDTO dto = null;
            
            apply = dataManager.findById(UserApply.class, applyIDStr);
            
            if(apply != null){
                dto  = new UserApplyDTO();
                attachments =
                        getAttachmentsByUserApplyID(apply!=null && apply.getApplyId()!=null?apply.getApplyId():0);
//                if(apply.getApplyType() == 3){
//                    subDemands = 
//                            getSubDemandsByUserApplyID(apply!=null && apply.getApplyId()!=null?apply.getApplyId():0);
//                    dto.changeToUserApplyDTO4Demand(apply, attachments, subDemands);
//                }else 
                    dto.changeToUserApplyDTO(apply,attachments, null);
            }
            
            
        String string = StaticMethods.getDateGson().toJson(dto);
          return string;
      } catch (Exception e) {
          e.printStackTrace();
          return ERROR;
      }
    }
    
    /* 
     * <p>Title: showUserApllyByCode</p>
     * <p>Description: </p>
     * @param userApplyCode
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#showUserApllyByCode(java.lang.String)
     */
    @Override
    public String showUserApllyByCode(String userApplyCode) {
        try {
            List<UserApply> applies = new ArrayList<UserApply>();
            applies = dataManager.createQuery(
                    "select p from UserApply p "
                            + "where p.userApplyCode = '" + userApplyCode
                            + "'" + " and p.DR = 0"
                            + " order by p.userApplyCode", UserApply.class)
                    .getResultList();

            UserApply apply = null;

            if (applies.size() > 0)
                apply = applies.get(0);

            UserApplyDTO dto = new UserApplyDTO();

            List<Attachment> attachments = getAttachmentsByUserApplyID(apply != null
                    && apply.getApplyId() != null ? apply.getApplyId() : 0);

            dto.changeToUserApplyDTO(apply, attachments, null);

            String string = StaticMethods.getDateGson().toJson(dto);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /**
     * 
     * @Title: getAttachmentsByUserApplyID
     * @Description:通过请求单ID查询其所有文档对象数据传输对象
     * @param applyIDStr
     * @return 
     * @retunType:List<Attachment>      返回类型
     * @throws:
     */
//    private List<AttachmentsDTO> getAttachmentsDTOByUserApplyID(String applyIDStr){
//        String sql = "select apply_id,attachment_id,attachment_type," +
//                "directions,dr,file_name,file_size,file_type,file_url," +
//                "upload_date,uploader  from t_attachment " +
//                "where apply_id = '"+ applyIDStr +"' " +
//                "and dr = 0 " +
//                "order by upload_date";
//        List<Attachments> attachments = 
//                dataManager.createNativeQuery(sql, Attachments.class).getResultList();
//                        
//        List<AttachmentsDTO> dtos = changeToAttachmentsDTOLst(attachments);
//        return dtos;
//    }
    
    
    /**
     * 
     * @Title: getAttachmentsByUserApplyID
     * @Description:通过请求单ID查询其所有文档对象数据传输对象
     * @param applyIDStr
     * @return 
     * @retunType:List<Attachments>      返回类型
     * @throws:
     */
    private List<Attachment> getAttachmentsByUserApplyID(Long applyIDStr){
        
        List<Attachment> attachments =  
                dataManager.createQuery(
                        "from Attachment a where a.busDataId = '" + applyIDStr + 
                        "' order by a.uploadDate", 
                        Attachment.class).getResultList();
        return attachments;
    }
    
//    /**
//     * 
//     * @Title: changeToAttachmentsDTOLst
//     * @Description:传入一个文档对象集合，返回一个文档对象数据传输对象集合
//     * @param attachments
//     * @return 
//     * @retunType:List<AttachmentsDTO>      返回类型
//     * @throws:
//     */
//    private List<AttachmentsDTO> changeToAttachmentsDTOLst(List<Attachments> attachments){
//        List<AttachmentsDTO> dtos = new ArrayList<AttachmentsDTO>();
//        if(attachments != null){
//            if(attachments.size() > 0){
//                for (Attachments attachment : attachments) {
//                    AttachmentsDTO dto = new AttachmentsDTO();
//                    if(attachment != null){
//                        User user =  null;
//                        AttachmentType type = null;
//                        user = getMsUserByID(
//                                attachment.getUploaderID() != null
//                                ?attachment.getUploaderID():0);
//                        type = getAttachmentTypeByID(
//                                attachment.getFileType() != null
//                                ?attachment.getFileType():0);
//                        dto.changeToAttachmentsDTO(attachment, type, user);
//                    }
//                    dtos.add(dto);
//                }
//            }
//        }
//        return dtos;
//    }
    
//    /**
//     * 
//     * @Title: getAttachmentTypeByID
//     * @Description:根据文档类型ID查询文档类型对象，最主要是查询到文档类型对象名称
//     * @param id
//     * @return 
//     * @retunType:AttachmentType      返回类型
//     * @throws:
//     */
//    private AttachmentType getAttachmentTypeByID(int id){
//        return null;
//    }
    
    /* 
     * <p>Title: initBugSource</p>
     * <p>Description: </p>1:请求来源
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initBugSource()
     */
    @Override
    public String initBugSource() {
        try {
        List<ConstDetail> bugSources = new ArrayList<ConstDetail>();
        String hql = "from ConstDetail c where c.constType.id = " + 1 + " order by c.id";
        bugSources = dataManager.createQuery(hql).getResultList();
        return StaticMethods.getDateGson().toJson(bugSources);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /**
     * 
     * @Title: initBugSourceReturnList
     * @Description:内部调用：获得“bug来源列表”
     * @return 
     * @retunType:List<ConstDetail>      返回类型
     * @throws:
     */
    private List<ConstDetail> initBugSourceReturnList() {
        try {
        List<ConstDetail> bugSources = new ArrayList<ConstDetail>();
        String hql = "from ConstDetail c where c.constType.id = " + 1 + " order by c.id";
        bugSources = dataManager.createQuery(hql).getResultList();
        return bugSources;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /* 
     * <p>Title: initBugScope</p>
     * <p>Description: </p>2：影响范围
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initBugScope()
     */
    @Override
    public String initBugScope() {
        try {
        List<ConstDetail> bugScopes = new ArrayList<ConstDetail>();
        String hql = "from ConstDetail c where c.constType.id = " + 2 + " order by c.id";
        bugScopes = dataManager.createQuery(hql).getResultList();
        return StaticMethods.getDateGson().toJson(bugScopes);
    } catch (Exception e) {
        e.printStackTrace();
        return ERROR;
    }
    }
    
    /**
     * 
     * @Title: initBugScopeReturnList
     * @Description:内部调用：获得“作用范围列表”
     * @return 
     * @retunType:List<ConstDetail>      返回类型
     * @throws:
     */
    private List<ConstDetail> initBugScopeReturnList() {
        try {
        List<ConstDetail> bugScopes = new ArrayList<ConstDetail>();
        String hql = "from ConstDetail c where c.constType.id = " + 2 + " order by c.id";
        bugScopes = dataManager.createQuery(hql).getResultList();
        return bugScopes;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }
    
    /* 
     * <p>Title: initLevels</p>
     * <p>Description: </p>
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initLevels()
     */
    @Override
    public String initLevels() {
        try {
        List<ReplyLevel> replyLevels = new ArrayList<ReplyLevel>();
        String hql = "from ReplyLevel r where " + DR_QUERY_STRING + "order by r.replyLevelId";
        replyLevels = 
                dataManager.createQuery(hql).getResultList();
        return StaticMethods.getDateGson().toJson(replyLevels);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /**
     * 
     * @Title: initLevelsReturnList
     * @Description:内部调用：获得“重要程度列表”
     * @return 
     * @retunType:List<ReplyLevel>      返回类型
     * @throws:
     */
    private List<ReplyLevel> initLevelsReturnList() {
        try {
        List<ReplyLevel> replyLevels = new ArrayList<ReplyLevel>();
        String hql = "from ReplyLevel r where " + DR_QUERY_STRING + "order by r.replyLevelId";
        replyLevels = 
                dataManager.createQuery(hql).getResultList();
        return replyLevels;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    /* 
     * <p>Title: initSupportSystems</p>
     * <p>Description: </p>
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initSupportSystems()
     */
    @Override
    public String initSupportSystems() {
        try {
        List<SupportSystem> supportSystems = new ArrayList<SupportSystem>();
        supportSystems = dataManager.createQuery(SupportSystem.class)
                .queryWhere(DR_QUERY_STRING).list();
        List<SupportSystemDTO> dtos = new ArrayList<SupportSystemDTO>();
        for (int i = 0; i < supportSystems.size(); i++) {
            SupportSystemDTO dto = new SupportSystemDTO();
            dto.changeToSupportSystemDTO(supportSystems.get(i), null);
            if(i == 0)
                dto.setSystemModules(initModulesBySystemID(dto.getSystemID()));
            dtos.add(dto);
        }
        return StaticMethods.getDateGson().toJson(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 
     * @Title: initSupportSystemsReturnList
     * @Description:内部调用：获得“系统列表（外加第一个系统的所有业务列表）”
     * @return 
     * @retunType:List<SupportSystemDTO>      返回类型
     * @throws:
     */
    private List<SupportSystemDTO> initSupportSystemsReturnList() {
        try {
        List<SupportSystem> supportSystems = new ArrayList<SupportSystem>();
        supportSystems = dataManager.createQuery(SupportSystem.class)
                .queryWhere(DR_QUERY_STRING).list();
        List<SupportSystemDTO> dtos = new ArrayList<SupportSystemDTO>();
        for (int i = 0; i < supportSystems.size(); i++) {
            SupportSystemDTO dto = new SupportSystemDTO();
            dto.changeToSupportSystemDTO(supportSystems.get(i), null);
            if(i == 0)
                dto.setSystemModules(initModulesBySystemID(dto.getSystemID()));
            dtos.add(dto);
        }
        return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * 
     * @Title: initModulesBySystemID
     * @Description:传入系统ID，查询出其所有的模块
     * @param systemId
     * @return 
     * @retunType:List<SystemModule>      返回类型
     * @throws:
     */
    private List<SystemModule> initModulesBySystemID(int systemId) {
        List<SystemModule> systemModules = null;
        try {
            systemModules = new ArrayList<SystemModule>();
//        String hql = " systemID = " + systemId + " and " + DR_QUERY_STRING;
//        systemModules = 
//                dataManager.createQuery(SystemModule.class)
//                .queryWhere(hql)
//                .list();
            //由于配置的关系，这里用sql语句
            String sql = " select sm.dr, sm.module_code, sm.module_id, sm.module_name, sm.remark, sm.system_id " +
            		"from t_system_module sm " +
            		"where sm.system_id = " + systemId + " " +
            		"and sm.dr = 0 ";
            systemModules = 
                    dataManager.createNativeQuery(sql, SystemModule.class).getResultList();
    } catch (Exception e) {
        e.printStackTrace();
    }
        return systemModules;
    }

    /* 
     * <p>Title: initSystemModules</p>
     * <p>Description: </p>
     * @param systemId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initSystemModules(int)
     */
    @Override
    public String initSystemModules(int systemId) {
        try {
            return StaticMethods.getDateGson().toJson(initModulesBySystemID(systemId));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: addOrUpdateUerApply</p>
     * <p>Description: </p>
     * @param userApplyJson
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#saveOrUpdateUerApply(java.lang.String)
     */
    @Override
    public String addOrUpdateUerApply(String userApplyJson) {
        try {
            UserApply apply = StaticMethods.getDateGson().fromJson(userApplyJson, UserApply.class);
            if(apply.getApplyId() != null && !"".equals(apply.getApplyId()))
               dataManager.update(apply);
            else{
//                apply.setApplyId(Uid.getUidUtil().createUID());
                dataManager.add(apply);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        
    }

    /* 
     * <p>Title: delUserApplyByID</p>
     * <p>Description: </p>
     * @param applyID
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#delUserApplyByID(java.lang.String)
     */
    @Override
    @Transactional
    public String delUserApplyByID(Long applyID) {
        try {
            if(applyID != null && applyID != 0){
                UserApply apply = dataManager.findById(UserApply.class, applyID);
                apply.setDR(1);
                dataManager.update(apply);
//                dataManager.deleteById(UserApply.class, applyID);
            }
                
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: delUserApply</p>
     * <p>Description: </p>
     * @param userApplyJson
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#delUserApply(java.lang.String)
     */
    @Override
    public String delUserApply(String userApplyJson) {
        try {
            UserApply userApply = new Gson().fromJson(userApplyJson, UserApply.class);
            userApply.setDR(1);
            dataManager.update(userApply);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

//    /* 
//     * <p>Title: delAttachmentById</p>
//     * <p>Description: </p>
//     * @param attachmentId
//     * @param userApplyId
//     * @return
//     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#delAttachmentById(java.lang.String, java.lang.String)
//     */
//    @Override
//    public String delAttachmentById(String attachmentId, Long userApplyId) {
//        try {
//            dataManager.deleteById(Attachment.class, attachmentId);
//            return initAttachmentsByApplyID(userApplyId);
//        } catch (Exception e) {
//            return ERROR;
//        }
//    }
    
    /* 
     * <p>Title: delAttachment</p>
     * <p>Description: </p>
     * @param attachmentJson
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#delAttachment(java.lang.String)
     */
//    @Override
//    public String delAttachment(String attachmentJson) {
//        try {
//            Attachments attachment = 
//                    new Gson().fromJson(attachmentJson, Attachments.class);
//            Long userApplyId = attachment.getApply().getApplyId();
//            return initAttachmentsByApplyID(userApplyId);
//        } catch (Exception e) {
//            return ERROR;
//        }
//    }

    /* 
     * <p>Title: saveAttachment</p>
     * <p>Description: </p>
     * @param attachmentJson
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#saveAttachment(java.lang.String)
     */
//    @Override
//    public String saveAttachment(String attachmentJson) {
//        try {
//            Attachments attachment = new Gson().fromJson(attachmentJson, Attachments.class);
//            dataManager.add(attachment);
//            return initAttachmentsByApplyID(attachment.getApply().getApplyId());
//        } catch (Exception e) {
//            return ERROR;
//        }
//    }

//    /**
//     * 
//     * @Title: initAttachmentsByApplyID
//     * @Description:根据请求单id初始化文档表格
//     * @param applyId
//     * @return 
//     * @retunType:String      返回类型
//     * @throws:
//     */
//    private String initAttachmentsByApplyID(Long applyId) {
//        try {
//            List<Attachments> attachments = new ArrayList<Attachments>();
//            String hql = " applyID = '" + applyId + "'";
//            attachments = dataManager.createQuery(Attachments.class)
//                    .queryWhere(hql)
//                    .queryWhere(DR_QUERY_STRING)
//                    .list();
//            return StaticMethods.getDateGson().toJson(changeToAttachmentsDTOLst(attachments));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ERROR;
//        }
//    }

//    /* 
//     * <p>Title: initAttachments</p>
//     * <p>Description: </p>
//     * @param userApplyID
//     * @param lines
//     * @param pageNum
//     * @param isCount
//     * @return
//     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initAttachments(java.lang.String, int, int, boolean)
//     */
//    @Override
//    public String initAttachments(
//            String userApplyID, int lines, int pageNum, boolean isCount) {
//        String string = "";
//        try {
//            if(isCount)
//                string =  queryAttachmentsByApplyIDAndPage(userApplyID, pageNum, lines);
//            else
//                string =  StaticMethods.getDateGson().toJson(queryAttachmentsByApplyIDAndPageExactly(userApplyID, pageNum, lines));
//            return string;
//        } catch (Exception e) {
//            return ERROR;
//        }
//    }

//    /**
//     * 
//     * @Title: queryAttachmentsByApplyIDAndPageExactly
//     * @Description:通过请求单分页查询出其对应的文档对象集合，
//     *              不返回总行数。
//     * @param applyID 请求单ID
//     * @param pageNum 第几页
//     * @param lines 每页显示多少行
//     * @return 
//     * @retunType:List<Object>      返回类型
//     * @throws:
//     */
//    private List<Object> queryAttachmentsByApplyIDAndPageExactly(
//            String applyID, int pageNum, int lines) {
//        
//        String sql = "select apply_id,attachment_id,attachment_type," +
//        		"directions,dr,file_name,file_size,file_type,file_url," +
//        		"upload_date,uploader  from t_attachment " +
//        		"where apply_id = '"+ applyID +"' " +
//        		"and dr = 0 " +
//        		"order by upload_date";
//        
//        List<Object> reList = new ArrayList<Object>();
//        
//        List<Attachments> attachments = 
//                dataManager.createNativeQuery(sql, Attachments.class)
//                .setFirstResult(pageNum).setMaxResults(lines)
//                .getResultList();
//        
//        List<AttachmentsDTO> dtos = changeToAttachmentsDTOLst(attachments);
//        reList.add(dtos);
//        return reList;
//    }

//    /* 
//     * <p>Title: queryAttachmentsByApplyIDAndPage</p>
//     * <p>Description: </p>
//     * @param applyID
//     * @param lines
//     * @param pageNum
//     * @return
//     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#queryAttachmentsByApplyIDAndPage(java.lang.String, int, int)
//     */
//    @Override
//    public String queryAttachmentsByApplyIDAndPage(
//            String applyID, int pageNum, int lines) {
//        
//        String sql = "select count(attachment_id) as num from t_attachment " +
//        		"where apply_id = '" + applyID + "' and dr = 0";
//        
//            try {
//                List<Object> reList = queryAttachmentsByApplyIDAndPageExactly(
//                        applyID, pageNum, lines);
//                
//                Long count = dataManager.createQuery(sql, Long.class).getSingleResult();
//                reList.add(count);
//                String string = new Gson().toJson(reList);
//                return string;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ERROR;
//            }
//    }

    /* 
     * <p>Title: initDropDownList4AddPage</p>
     * <p>Description: </p>
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initDropDownList4AddPage()
     */
    @Override
    public String initDropDownList4AddPage() {
        List<Object> menusList = new ArrayList<Object>();
        //影响范围0
        menusList.add(initBugScopeReturnList());
        //来源1
        menusList.add(initBugSourceReturnList());
        //重要程度2
        menusList.add(initLevelsReturnList());
        //系统,列表中第一个系统的业务模块3
        menusList.add(initSupportSystemsReturnList());
        String string =  StaticMethods.getDateGson().toJson(menusList);
        
        return string;
    }

    /* 
     * <p>Title: findUserAppliesByQueryWindow</p>
     * <p>Description: </p>
     * @param searchInfo
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#findUserAppliesByQueryWindow(java.lang.String, int, int, boolean)
     */
    @Override
    public String findUserAppliesByQueryWindow(SearchInfoDTO searchInfo, 
            int pageNum, int lines, boolean isCount) {
        String sql = 
//                "select ua.user_apply_id, ua.user_apply_code, " +
//                "ua.apply_end_date, ua.apply_entry, ua.apply_origin, " +
//                "ua.apply_reason, ua.apply_start_date, ua.apply_status, " +
//                "ua.apply_title, ua.apply_type, ua.belongs_business, " +
//                "ua.belongs_system, ua.company, ua.department, " +
//                "ua.directions, ua.dr, ua.msuser_apply_code, " +
//                "ua.plan_finish_time, ua.range, ua.real_finish_time, " +
//                "ua.real_finish_time, ua.real_range, ua.reason, " +
//                "ua.reply_time, ua.replyer, ua.replylevel, " +
//                "ua.solutions, ua.sponsor, ua.telephone, ua.urgent " +
                
                "select * " +
                
                "from t_user_apply ua " +
                "where ua.dr = 0 ";
        
        if(searchInfo != null){
            
            if(searchInfo.getFlag() > 0)
                sql += "and ua.apply_type = " + searchInfo.getFlag() + " ";
            if(searchInfo.getCode() != null && !"".equals(searchInfo.getCode().trim())){
                sql += "and ua.user_apply_code like '" + searchInfo.getCode() + "%' ";
            }
            if(searchInfo.getDegree() > ERROR_NUM){
                sql += "and ua.URGENT = " + searchInfo.getDegree() + " ";
            }
            if(searchInfo.getRange() > ERROR_NUM){
                 sql += "and ua.range = " + searchInfo.getRange() + " ";
            }
            if(searchInfo.getStatus() > ERROR_NUM){
                 sql += "and ua.apply_status = " + searchInfo.getStatus() + " ";
            }
            if(searchInfo.getSystem() > ERROR_NUM){
                 sql += "and ua.belongs_system = " + searchInfo.getSystem() + " ";
            }
            if(searchInfo.getTheme() != null && !"".equals(searchInfo.getTheme().trim())){
                 sql += "and ua.apply_title like '%" + searchInfo.getTheme() + "%' ";
            }
            if(searchInfo.getSponsor() != null && !"".equals(searchInfo.getSponsor().trim())){
                 sql += "and ua.sponsor in " +
                		"(select mu.user_id from ms_user mu " +
                		"where mu.user_name like '%" + searchInfo.getSponsor() + "%') ";
            }
            if(searchInfo.getUserID() > 0){
                sql += "and ua.sponsor = " + searchInfo.getUserID() + " ";
            }
            
        }
        
        sql += " order by ua.user_apply_code desc";
        
        try {
            
            List<Object> reList = new ArrayList<Object>();
            
            List<HomePage> applies = 
                    dataManager.createNativeQuery(sql, HomePage.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            for (HomePage homePage : applies) {

                switch (homePage.getApplyStatus()!=null?homePage.getApplyStatus():0) {
                case 1:
                    homePage.applyStatusStr = "未受理";
                    break;
                case 2:
                    homePage.applyStatusStr = "处理中";
                    break;
                case 3:
                    homePage.applyStatusStr = "完成";
                    break;
                default:
                    homePage.applyStatusStr = "未提交";
                    break;
                }
            }
            
            reList.add(applies);
            
            if(isCount){
                Long count = new Long(dataManager.getCountBySql(sql));
                reList.add(count);
            }
           
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
            
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        
    }

//    /* 
//     * <p>Title: findAllApplyCodes</p>
//     * <p>Description: </p>
//     * @param flag
//     * @return
//     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#findAllApplyCodes(int)
//     */
//    @Override
//    public String findAllApplyCodes(int flag) {
//        String flagStr = "BG";
//        if(flag == 1)
//            flagStr = "WT";
//        Date date = new Date();
//        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
//        String year = dateString.split("-")[0];
//        String month = dateString.split("-")[1];
//        String day = dateString.split("-")[2];
//        
//        String dateStr = year+month+day;
//        
//        try {
//            String sql = "select u.userApplyCode from UserApply u where u.userApplyCode like '"+flagStr+dateStr+"%'";
//            List<String> codes = dataManager.createQuery(sql, String.class).getResultList();
//            return new Gson().toJson(codes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ERROR;
//        }
//    }
    
    /* 
     * <p>Title: addBugUserApply</p>
     * <p>Description: </p>
     * @param apply
     * @param compId
     * @param personId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#addBugUserApply(com.sccl.serviceManager.bugManager.entity.UserApply, java.lang.String, java.lang.String)
     */
    @Override
    public Long addBugUserApply(UserApplyAdd apply, String compId, String personId) {
        try {
//            BugFlowTool tools = BugFlowTool.getInstence();
            FlowTools tools = FlowTools.getInstence();
            tools.setDataManager(dataManager);
            Long oprtTypeID = Long.valueOf("2");
            Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
            if(flowModelId==-1){
                return (long) -1;
            }else if(flowModelId==0){
                return (long) 0;
            }else{
//                apply.setApplyId(Uid.getUidUtil().createUID());
                apply.setUserApplyCode(new BugManagerUtil(dataManager).createBugApplyCode(2));
                if(apply.getApplyId() !=null && apply.getApplyId() <= 0)
                    apply.setApplyId(null);
                dataManager.add(apply);
//                BigInteger billID = new BigInteger(apply.getApplyId().trim());
                Long billID = apply.getApplyId();
                String flowTitle = "BUG流程："+apply.getApplyTitle();
                tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
            }
            return apply.getApplyId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -2;
    }
    
    

    
    /* 
     * <p>Title: updateBugUerApply</p>
     * <p>Description: </p>
     * @param userApplyJson
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#updateBugUerApply(java.lang.String)
     */
    @Override
    public String updateBugUerApply(String userApplyJson) {
        try {
            UserApply apply = StaticMethods.getDateGson().fromJson(userApplyJson, UserApply.class);
            if(apply.getApplyId() != null && apply.getApplyId() != 0){
               dataManager.update(apply);
               return SUCCESS;
            }
            return ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: updateUerApplyStatus</p>
     * <p>Description: </p>
     * @param applyID
     * @param status
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#updateUerApplyStatus(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public String updateUerApplyStatus(Long applyID, int status) {
        try {
            String flagString = updateUerApplyStatusNoDataBack(applyID, status);
            if(SUCCESS.equals(flagString)){
                UserApply apply = dataManager.findById(UserApply.class, applyID);
              String applyStr = StaticMethods.getDateGson().toJson(apply);
              return applyStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return ERROR;
    }
    
    /* 
     * <p>Title: agreeBugUserApplyFlow1st</p>
     * <p>Description: </p>
     * @param billId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#agreeBugUserApplyFlow(java.lang.String)
     */
    @Log
    @Override
    @Transactional
    public String agreeBugUserApplyFlow1st(String billId) {
        try {
            String sql = "update t_user_apply u set u.apply_status = 2 " +
            		" where u.user_apply_id = " + Long.parseLong(billId);
            dataManager.createNativeQuery(sql).executeUpdate();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: disagreeBugUserApplyFlow1st</p>
     * <p>Description: </p>
     * @param billId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#disagreeBugUserApplyFlow(java.lang.String)
     */
    @Log
    @Override
    @Transactional
    public String disagreeBugUserApplyFlow1st(String billId) {
        try {
            String sql = "update t_user_apply u set u.apply_status = 0 where u.user_apply_id = " + Long.parseLong(billId);
            dataManager.createNativeQuery(sql).executeUpdate();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * 
     * @Title: getUserByPersonId
     * @Description:根据人员id获取用户id
     * @param personId
     * @return 
     * @retunType:int      返回类型
     * @throws:
     */
    private int getUserByPersonId(int personId){
        try {
            String sql = "select mu.user_id from ms_user mu where mu.person_id = " + personId;
            List<BigDecimal> users =  dataManager.createNativeQuery(sql).getResultList();
            if(users != null && users.size() > 0){
                return users.get(0).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    /* 
     * <p>Title: setReplyer</p>
     * <p>Description: </p>
     * @param billId
     * @param userId
     * @param planFinishDateStr
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#setReplyer(java.lang.String, int)
     */
    @Log
    @Override
    @Transactional
    public String setReplyer(String billId, int userId, String planFinishDateStr) {
        try {
//            int userID = getUserByPersonId(userId);
//            if(userID > 0 ){
//                userId = userID;
//            }
            String sql = "update t_user_apply u set u.REPLYER = " + userId + ", " +
            		"u.REPLY_TIME = (select current_date from dual), " +
                    "u.PLAN_FINISH_TIME = to_date('"+ planFinishDateStr + "','YYYY-MM-DD') " +
            		"where u.USER_APPLY_ID = " + Long.parseLong(billId);
            int flag = dataManager.createNativeQuery(sql).executeUpdate();
            return flag > 0 ? SUCCESS : ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: updateUerApplyStatusNoDataBack</p>
     * <p>Description: </p>
     * @param applyID
     * @param status
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#updateUerApplyStatusNoDataBack(java.lang.Long, int)
     */
    @Log
    @Override
    @Transactional
    public String updateUerApplyStatusNoDataBack(Long applyID, int status) {
        try {
            if(applyID != null && !"".equals(applyID)){
            String sql = "update t_user_apply t set t.apply_status = " + status + " ";
            if(status == 3){
                sql += ",  t.real_finish_time = (select sysdate from dual) ";
            }
            sql += "where t.user_apply_id = " + applyID;
            dataManager.createNativeQuery(sql).executeUpdate();
            if(status == 3){
                UserApply apply = dataManager.findById(UserApply.class, applyID);
                ServiceOrder order = null;
                if(apply != null){
                    //0:bug、问题；1：子需求
                    if(apply.getApplyType() != 3){
                        order = BugManagerUtil.createServiceOrder(apply, 0);
                        dataManager.add(order);
                    }
                }
            }
            return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return ERROR;
    }

    /* 
     * <p>Title: updateBugUerApplyAdd</p>
     * <p>Description: </p>
     * @param userApplyAddJson
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#updateBugUerApplyAdd(java.lang.String)
     */
    @Log
    @Override
    @Transactional
    public String updateBugUerApplyAdd(String userApplyAddJson) {
        try {
            UserApplyAdd apply = StaticMethods.getDateGson().fromJson(userApplyAddJson, UserApplyAdd.class);
            if(apply.getApplyId() != null && 
                    apply.getApplyId() != 0 &&
                    apply.getUserApplyCode() != null){
               dataManager.update(apply);
               return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return ERROR;
    }

    /* 
     * <p>Title: getPersonIdByApplyId</p>
     * <p>Description: </p>
     * @param applyId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#getPersonIdByUserId(int)
     */
    @Override
    public String getPersonIdByApplyId(int applyId) {
        try {
            
            @SuppressWarnings("rawtypes")
            List lst = dataManager
                    .createNativeQuery(
                            "select mu1.person_id as sponsor, " +
                            "mu.person_id as applyEntry " +
                            "from ms_user mu, ms_user mu1, t_user_apply up " +
                            "where mu1.user_id = up.sponsor " +
                            "and mu.user_id = up.apply_entry " +
                            "and up.user_apply_id = " + applyId)
                            .getResultList();
            
//            Integer personId = new Integer(
//                    ((java.math.BigDecimal)(dataManager
//                  .createNativeQuery(
//                          "select mu.person_id " +
//                          "from ms_user mu, t_user_apply up " +
//                          "where mu.user_id = up.apply_entry " +
//                          "and up.user_apply_id = " + applyId)
//                          .getResultList().get(0))).intValue());
////            return new Gson().toJson(personId);
//            if(personId > 0)
//                return personId.toString().trim();
            if(lst != null && lst.size() > 0)
                return new Gson().toJson(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return ERROR;
    }
    
    
    /* 查询用户请求单的所有数据包括（problem模块和bug模块的全部数据）
     * <p>Title: queryUserAppliesByPage</p>
     * <p>Description: </p>
     *  @param flag
     * @param lines
     * @param pageNum
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#queryUserAppliesByPage(int, int)
     */
    @Override
    public String queryUserAppliesByPage(int flag, int lines, int pageNum) {
            try {
                List<Object> reList = queryUserAppliesByPageExactly(flag,
                        lines, pageNum);
                
                Long count = dataManager.createQuery("select count(p) from UserApply p where p.DR = 0 and p.applyType = " + flag, Long.class).getSingleResult();
                reList.add(count);
                String string = StaticMethods.getDateGson().toJson(reList);
                return string;
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR;
            }
    }
    
    
    
    
    //==================================================================================================
    /* 
     * <p>Title: findMyUserAppliesByQueryWindow</p>
     * <p>Description: </p>
     * @param searchInfo
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#findUserAppliesByQueryWindow(java.lang.String, int, int, boolean)
     */
    @Override
    public String findMyUserAppliesByQueryWindow(SearchInfoDTO searchInfo, 
            int pageNum, int lines, boolean isCount) {
        String sql =     
                "select * " +
                "from t_user_apply ua " +
                "where ua.dr = 0 ";
        
        if(searchInfo != null){
            
            if(searchInfo.getDegree() > ERROR_NUM){
                sql += "and ua.apply_type = " + searchInfo.getFlag() + " ";
            }
            if(searchInfo.getCode() != null && !"".equals(searchInfo.getCode().trim())){
                sql += "and ua.user_apply_code like '" + searchInfo.getCode() + "%' ";
            }
            if(searchInfo.getDegree() > ERROR_NUM){
                sql += "and ua.URGENT = " + searchInfo.getDegree() + " ";
            }
            if(searchInfo.getRange() > ERROR_NUM){
                 sql += "and ua.range = " + searchInfo.getRange() + " ";
            }
            if(searchInfo.getStatus() > ERROR_NUM){
                 sql += "and ua.apply_status = " + searchInfo.getStatus() + " ";
            }
            if(searchInfo.getSystem() > ERROR_NUM){
                 sql += "and ua.belongs_system = " + searchInfo.getSystem() + " ";
            }
            if(searchInfo.getTheme() != null && !"".equals(searchInfo.getTheme().trim())){
                 sql += "and ua.apply_title like '%" + searchInfo.getTheme() + "%' ";
            }
            if(searchInfo.getSponsor() != null && !"".equals(searchInfo.getSponsor().trim())){
                 sql += "and ua.sponsor in " +
                		"(select mu.user_id from ms_user mu " +
                		"where mu.user_name like '%" + searchInfo.getSponsor() + "%')";
            }
            
        }
        
        sql += " order by ua.user_apply_code desc";
        
        try {
            
            List<Object> reList = new ArrayList<Object>();
            
            List<HomePage> applies = 
                    dataManager.createNativeQuery(sql, HomePage.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            for (HomePage homePage : applies) {

                switch (homePage.getApplyStatus()!=null?homePage.getApplyStatus():0) {
                case 1:
                    homePage.applyStatusStr = "未受理";
                    break;
                case 2:
                    homePage.applyStatusStr = "处理中";
                    break;
                case 3:
                    homePage.applyStatusStr = "完成";
                    break;
                default:
                    homePage.applyStatusStr = "未提交";
                    break;
                }
            }
            
            reList.add(applies);
            
            if(isCount){
                Long count = new Long(dataManager.getCountBySql(sql));
                reList.add(count);
            }
           
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
            
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        
    }
    

   
    
    /* 
     * <p>Title: initMyHomePageFast</p>初始化首页的所有包括problem和bug的所有数据
     * <p>Description: </p>
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initHomePageFast(int, int, int, boolean)
     */
    @Override
    public String initMyHomePageFast(int userId,int lines, int pageNum,boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            
            reList.add(queryMyUserAppliesByPageFastExactly(lines, pageNum,userId));
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            return ERROR;
        }
    }
    
    /**
     * 
     * @Title: queryMyUserAppliesByPageFastExactly
     * @Description:分页查询一个用户请求单集合(数据只限于主页表格)
     * @param flag
     * @param pageNum
     * @param lines
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    private List<HomePage> queryMyUserAppliesByPageFastExactly(int pageNum, int lines,int userId) {
    	
        List<HomePage> applies = 
                dataManager.createQuery(
                        "from HomePage p where p.DR=0 " + " and p.applyEntry =   " + userId +  " order by p.userApplyCode desc",HomePage.class)
                                .setFirstResult(pageNum).setMaxResults(lines)
                                .getResultList();
        for (HomePage homePage : applies) {

            switch (homePage.getApplyStatus()!=null?homePage.getApplyStatus():0) {
            case 1:
                homePage.applyStatusStr = "未受理";
                break;
            case 2:
                homePage.applyStatusStr = "处理中";
                break;
            case 3:
                homePage.applyStatusStr = "完成";
                break;
            default:
                homePage.applyStatusStr = "未提交";
                break;
            }
            
        }
//      User applyEntry = homePage.getApplyEntry();
//      String personId = dataManager
//              .createNativeQuery(
//                      "select mu.person_id " +
//                      "from ms_user mu " +
//                      "where mu.user_id = "+applyEntry.getId(), Integer.class)
//                      .toString().trim();
//      applyEntry.setPersonId(personId);
        return applies;
    }


    
    public MsUser getMsUser() {
		return msUser;
	}

	public void setMsUser(MsUser msUser) {
		this.msUser = msUser;
	}

	/* 
     * <p>Title: addProblemUserApply</p>创建problem模块的WT编号
     * <p>Description: </p>
     * @param apply
     * @param compId
     * @param personId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#addBugUserApply(com.sccl.serviceManager.bugManager.entity.UserApply, java.lang.String, java.lang.String)
     */
    @Override
    public Long addProblemUserApply(UserApplyAdd apply, String compId, String personId) {
        try {
            FlowTools tools = FlowTools.getInstence();
            tools.setDataManager(dataManager);
            Long oprtTypeID = Long.valueOf("1");
            Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
            if(flowModelId==-1){
                return (long) -1;
            }else if(flowModelId==0){
                return (long) 0;
            }else{
                apply.setUserApplyCode(new BugManagerUtil(dataManager).createBugApplyCode(1));
                if(apply.getApplyId() !=null && apply.getApplyId() <= 0)
                    apply.setApplyId(null);
                dataManager.add(apply);
                Long billID = apply.getApplyId();
                String flowTitle = "Problem流程："+apply.getApplyTitle();
                tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
            }
            return apply.getApplyId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -2;
    }
    //===============================================================================

    /* 
     * <p>Title: getSubDemandsByUserApplyID</p>
     * <p>Description: </p>
     * @param applyIDStr
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#getSubDemandsByUserApplyID(java.lang.Long, int, int, boolean)
     */
    @Override
    public String getSubDemandsByUserApplyID(Long applyIDStr,
            int lines, int pageNum, boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            
            reList.add(getSubDemandsByUserApplyIDExactly(applyIDStr, lines, pageNum));
            if(isCount)
                reList.add(getPageCount4SubDemands(applyIDStr));
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /* 
     * <p>Title: getSubDemandsByApplyID</p>
     * <p>Description: </p>
     * @param applyIDStr
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#getSubDemandsByApplyID(java.lang.Long, int, int, boolean)
     */
    @Override
    public String getSubDemandsByApplyID(Long applyIDStr,
            int lines, int pageNum, boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            
            reList.add(getSubDemandsByApplyIDExactly(applyIDStr, lines, pageNum));
            if(isCount)
                reList.add(getPageCount4SubDemands(applyIDStr));
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: getSubDemandsByApplyIDExactly
     * @Description:根据客户需求单id查询出其所有子需求单
     * @param applyIDStr
     * @param pageNum
     * @param lines
     * @return List<SubDemand>        返回类型
     * @throws
     */
    private List<SubDemand> getSubDemandsByApplyIDExactly(
            Long applyIDStr, int pageNum, int lines) {
        List<SubDemand> subDemands =  
                dataManager.createQuery(
                        "from SubDemand a where a.userApplyId = " + applyIDStr + 
                        " and a.dr = 0 and a.demandStatus > 0 order by a.demandStatus ", 
                        SubDemand.class)
                                .setFirstResult(pageNum).setMaxResults(lines)
                                .getResultList();
        for (SubDemand subDemand : subDemands) {
            //需求状态，0：未提交，1：需求分析，2：开发经理指派人员，3：开发，4：单元测试，5：功能测试，6：项目经理审核，7：完成
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
        return subDemands;
    }
    
    /**
     * 
     * @Title: getPageCount4SubDemands
     * @Description:返回需求单的所有子需求单数量（dr==0）
     * @param applyID
     * @return Long        返回类型
     * @throws
     */
    private Long getPageCount4SubDemands(Long applyID){
        try {
            return dataManager
                    .createQuery("select count(p) from SubDemand p " +
                            "where p.dr = 0 and p.userApplyId = " 
                            + applyID, Long.class)
                            .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.parseLong(ERROR);
        }
    }

    /**
     * 
     * @Title: getSubDemandsByUserApplyIDExactly
     * @Description:根据客户需求单id查询出其所有子需求单
     * @param applyIDStr
     * @param pageNum
     * @param lines
     * @return List<SubDemand>        返回类型
     * @throws
     */
    private List<SubDemand> getSubDemandsByUserApplyIDExactly(
            Long applyIDStr, int pageNum, int lines) {
        List<SubDemand> subDemands =  
                dataManager.createQuery(
                        "from SubDemand a where a.userApplyId = " + applyIDStr + 
                        " and a.dr = 0 order by a.demandStatus ", 
                        SubDemand.class)
                                .setFirstResult(pageNum).setMaxResults(lines)
                                .getResultList();
        for (SubDemand subDemand : subDemands) {
            //需求状态，0：未提交，1：需求分析，2：开发经理指派人员，3：开发，4：单元测试，5：功能测试，6：项目经理审核，7：完成
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
        return subDemands;
    }

    /* 
     * <p>Title: addServiceOrder</p>
     * <p>Description: </p>
     * @param serviceOrder
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#addServiceOrder(com.sccl.serviceManager.bugManager.entity.ServiceOrder)
     */
    @Override
    public Long addServiceOrder(ServiceOrder serviceOrder) {
        try {
            if(serviceOrder.getServiceOrderId() !=null && serviceOrder.getServiceOrderId() <= 0)
                serviceOrder.setServiceOrderId(null);
            dataManager.add(serviceOrder);
            return serviceOrder.getServiceOrderId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        }
    }

    /* 
     * <p>Title: initSupportSystemsByPage</p>
     * <p>Description: </p>
     * @param pageNum
     * @param lines
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initSupportSystemsByPage(int, int, boolean)
     */
    @Override
    public String initSupportSystemsByPage(int pageNum, int lines,
            boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            String sql = "select t.system_id , t.system_name , " +
            		"t.system_code , t.online_date , " +
            		"t.remark , t.dr " +
            		"from t_support_system t " +
                    "where t.dr = 0 " +
                    "order by t.online_date desc";
            
            List<SupportSystem> systems = 
                    dataManager.createNativeQuery(
                            sql,SupportSystem.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            
            reList.add(systems);
            if(isCount){
                Long count = new Long(dataManager.getCountBySql(sql));
                reList.add(count);
            }
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: initSystemModulesByPage</p>
     * <p>Description: </p>
     * @param sysID
     * @param pageNum
     * @param lines
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initSystemModulesByPage(int, int, int, boolean)
     */
    @Override
    public String initSystemModulesByPage(int sysID, int pageNum, int lines,
            boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            String sql = "select * from t_system_module t " +
            		"where t.dr = 0  " +
            				"and t.system_id = " + sysID +
            		" order by t.module_name ";
            
            List<SystemModule> modules = 
                    dataManager.createNativeQuery(
                            sql,SystemModule.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            
            reList.add(modules);
            if(isCount){
                Long count = new Long(dataManager.getCountBySql(sql));
                reList.add(count);
            }
            
            String string = new Gson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: initSupportSystemsByName</p>
     * <p>Description: </p>
     * @param sysName
     * @param pageNum
     * @param lines
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initSupportSystemsByName(java.lang.String, int, int, boolean)
     */
    @Override
    public String initSupportSystemsByName(String sysName, int pageNum,
            int lines, boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            String sql = "select t.system_id , t.system_name, " +
                    "t.system_code , t.online_date , " +
                    "t.remark , t.dr " +
                    "from t_support_system t " +
            		"where t.dr = 0 and t.system_name like '%" + sysName + "%' " +
            		"order by t.online_date desc";
            
            List<SupportSystem> systems = 
                    dataManager.createNativeQuery(
                            sql,SupportSystem.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            
            reList.add(systems);
            if(isCount){
                Long count = new Long(dataManager.getCountBySql(sql));
                reList.add(count);
            }
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: initSystemModulesByName</p>
     * <p>Description: </p>
     * @param moduleName
     * @param sysID
     * @param pageNum
     * @param lines
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initSystemModulesByName(java.lang.String, int, int, int, boolean)
     */
    @Override
    public String initSystemModulesByName(String moduleName, int sysID,
            int pageNum, int lines, boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            String sql = "select * from t_system_module t " +
                    "where t.dr = 0 and t.module_name like '%"+moduleName+"%' " +
                            "and t.system_id = " + sysID +
                    " order by t.module_name ";
            
            List<SystemModule> modules = 
                    dataManager.createNativeQuery(
                            sql,SystemModule.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            
            reList.add(modules);
            if(isCount){
                Long count = new Long(dataManager.getCountBySql(sql));
                reList.add(count);
            }
            
            String string = new Gson().toJson(reList);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }
    
    /**
     * 
     * @Title: queryUserAppliesByPageFastExactly
     * @Description:分页查询一个用户请求单集合(数据只限于主页表格)
     * @param flag
     * @param pageNum
     * @param lines
     * @return 
     * @retunType:List<Object>      返回类型
     * @throws:
     */
    private List<HomePage> uServiceAppliesByPageFastExactly(
            int flag, int pageNum, int lines) {
        List<HomePage> applies = 
                dataManager.createQuery(
                        "from HomePage p where  p.sponsor.id = " + flag + " " +
                                "and p.DR = 0"+" order by p.applyStatus asc",HomePage.class)
                                .setFirstResult(pageNum).setMaxResults(lines)
                                .getResultList();
        for (HomePage homePage : applies) {

            switch (homePage.getApplyStatus()!=null?homePage.getApplyStatus():0) {
            case 1:
                homePage.applyStatusStr = "未受理";
                break;
            case 2:
                homePage.applyStatusStr = "处理中";
                break;
            case 3:
                homePage.applyStatusStr = "完成";
                break;
            default:
                homePage.applyStatusStr = "未提交";
                break;
            }
        }
        return applies;
    }
    
    
    /* 
     * <p>Title: uServiceHomePageFast</p>
     * <p>Description: </p>
     * @param flag
     * @param lines
     * @param pageNum
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#initHomePageFast(int, int, int, boolean)
     */
    @Override
    public String uServiceHomePageFast(int flag, int lines, int pageNum,
            boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            
            reList.add(uServiceAppliesByPageFastExactly(flag, lines, pageNum));
            if(isCount)
               reList.add(getPageCountByUserId(flag));
            
            String string = StaticMethods.getDateGson().toJson(reList);
            return string;
        } catch (Exception e) {
            return ERROR;
        }
    }

    /* 
     * <p>Title: addUProblemApply</p>创建problem模块的WT编号
     * <p>Description: </p>
     * @param apply
     * @param compId
     * @param personId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#addUProblemApply(com.sccl.serviceManager.bugManager.entity.UserApply, java.lang.String, java.lang.String)
     */
    @Override
    public Long addUProblemApply(UserApplyAdd apply, String compId, String personId) {
        try {
            FlowTools tools = FlowTools.getInstence();
            tools.setDataManager(dataManager);
            Long oprtTypeID = Long.valueOf("1");
            Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
            if(flowModelId==-1){
                return (long) -1;
            }else if(flowModelId==0){
                return (long) 0;
            }else{
                apply.setUserApplyCode(new BugManagerUtil(dataManager).createBugApplyCode(1));
                if(apply.getApplyId() !=null && apply.getApplyId() <= 0)
                    apply.setApplyId(null);
                apply.setCompany(searCompanyName(apply.getApplyEntry().getId()));
                dataManager.add(apply);
                Long billID = apply.getApplyId();
                String flowTitle = "Problem流程："+apply.getApplyTitle();
                tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
            }
            return apply.getApplyId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -2;
    }
    
    /* 
     * <p>Title: addUBugApply</p>创建problem模块的WT编号
     * <p>Description: </p>
     * @param apply
     * @param compId
     * @param personId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#addUBugApply(com.sccl.serviceManager.bugManager.entity.UserApply, java.lang.String, java.lang.String)
     */
    @Override
    public Long addUBugApply(UserApplyAdd apply, String compId, String personId) {
        try {
            FlowTools tools = FlowTools.getInstence();
            tools.setDataManager(dataManager);
            Long oprtTypeID = Long.valueOf("2");
            Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
            if(flowModelId==-1){
                return (long) -1;
            }else if(flowModelId==0){
                return (long) 0;
            }else{
                apply.setUserApplyCode(new BugManagerUtil(dataManager).createBugApplyCode(2));
                if(apply.getApplyId() !=null && apply.getApplyId() <= 0)
                    apply.setApplyId(null);
                apply.setCompany(searCompanyName(apply.getApplyEntry().getId()));
                dataManager.add(apply);
                Long billID = apply.getApplyId();
                String flowTitle = "Bug流程："+apply.getApplyTitle();
                tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
            }
            return apply.getApplyId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -2;
    }
    
    /* 
     * <p>Title: addUDemandApply</p>创建problem模块的WT编号
     * <p>Description: </p>
     * @param apply
     * @param compId
     * @param personId
     * @return
     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#addUDemandApply(com.sccl.serviceManager.bugManager.entity.UserApply, java.lang.String, java.lang.String)
     */
    @Override
    public Long addUDemandApply(UserApplyAdd apply, String compId, String personId) {
        try {
            FlowTools tools = FlowTools.getInstence();
            tools.setDataManager(dataManager);
            Long oprtTypeID = Long.valueOf("3");
            Long flowModelId=tools.getFlowModelId(oprtTypeID, Long.valueOf(compId));
            if(flowModelId==-1){
                return (long) -1;
            }else if(flowModelId==0){
                return (long) 0;
            }else{
                apply.setUserApplyCode(new BugManagerUtil(dataManager).createBugApplyCode(3));
                if(apply.getApplyId() !=null && apply.getApplyId() <= 0)
                    apply.setApplyId(null);
                System.out.println(searCompanyName(apply.getApplyEntry().getId()));
                apply.setCompany(searCompanyName(apply.getApplyEntry().getId()));
                dataManager.add(apply);
                Long billID = apply.getApplyId();
                String flowTitle = "需求流程："+apply.getApplyTitle();
                tools.savaFlowInstence(oprtTypeID, flowModelId, billID, personId, flowTitle);
            }
            return apply.getApplyId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) -2;
    }
    
    
    /**
     * 
     * @Title: searCompanyName
     * @Description:根据用户id查询对应公司的名称
     * @param id
     * @return 
     * @retunType:String      返回类型
     * @throws:
     */
    public String searCompanyName(Integer id){
    	String sql = "select * from organization org where org.org_id = "+
    				 " (select o.company_id from organization o where o.org_id = "+
    				 " (select mp.org_id from ms_person mp where mp.person_id = "+
    				 " (select mu.person_id from ms_user mu where mu.user_id = "+id+")))";

//    	System.out.println(dataManager.createNativeQuery(sql,Organization.class).getResultList().get(0));
    	Organization or = (Organization) dataManager.createNativeQuery(sql,Organization.class).getResultList().get(0);
    	return or.getOrgName();
    	
    }
    
//    /* 
//     * <p>Title: createBugApplyCode</p>
//     * <p>Description: </p>
//     * @param flag
//     * @return
//     * @see com.sccl.serviceManager.bugManager.service.IBugManagerService#createBugApplyCode(int)
//     */
//    @Override
//    public String createBugApplyCode(int flag) {
//            String flagStr = "BG";
//            if(flag == 1)
//                flagStr = "WT";
//            if(flag == 3)
//                flagStr = "XQ";
//            String codes = findAllApplyCodes(flag);
//            List<String> codesLstList = new Gson().fromJson(codes, ArrayList.class);
//            List<Long> codeNums = new ArrayList<Long>();
//            
//            for (String str : codesLstList) {
//                str = str.split(flagStr).length == 2 ? str.split(flagStr)[1] : "";
//                if(!"".equals(str) && str.matches("\\d{12}"))
//                    codeNums.add(Long.parseLong(str));
//            }
//            
//            Collections.sort(codeNums);
//            
//            Long maxVal = 0l;
//            String applyCode = flagStr;
//            Date date = new Date();
//            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
//            String year = dateString.split("-")[0];
//            String month = dateString.split("-")[1];
//            String day = dateString.split("-")[2];
//            
//            Long dateNum = Long.parseLong(year+month+day);
//            
//            if(codeNums.size() >= 1)
//                maxVal = codeNums.get(codeNums.size()-1);
//                
//                if(Math.floor(maxVal/10000) == dateNum){
//                    Long num =  maxVal % 10000 + 1;
//                    num = dateNum * 10000 + num;
//                    applyCode += num;
//                }else
//                    applyCode += dateNum + "0001";
//                
//            
//            return applyCode;
//        }

}
