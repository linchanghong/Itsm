/**
 * @Title: AnalyzesServiceImpl.java
 * @Package com.sccl.analyzes.service
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-9-15 下午3:37:49
 * @version:V1.0 
 */
package com.sccl.serviceManager.supportSystemManager.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sccl.analyzes.dto.ViewStatistic4problem;
import com.sccl.analyzes.dto.ViewStatistic4workload;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.entity.ConstDetail;
import com.sccl.serviceManager.bugManager.dto.SupportSystemDTO;
import com.sccl.serviceManager.bugManager.entity.HomePage;
import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.analyzes.service.AnalyzesServiceImpl.java
 * @ClassName: AnalyzesServiceImpl
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-9-15
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-9-15 下午3:37:49
 * @Version:v1.0
 */
@Component("supportSystemManagerService")
@SuppressWarnings("unchecked")
public class SupportSystemManagerServiceImpl implements ISupportSystemManagerService {
    
    /**
     * 成功
     */
    private static final String SUCCESS = "1";
    
    /**
     * 失败
     */
    private static final String ERROR = "0";

    private DataManager dataManager;

    public DataManager getDataManager() {
        return dataManager;
    }

    @Resource(name = "dataManager")
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /* 
     * <p>Title: initHomePage</p>
     * <p>Description: </p>
     * @param pageNum
     * @param lines
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService#initHomePage(int, int, boolean)
     */
    @Override
    public String initHomePage(int pageNum, int lines, boolean isCount) {
        try {
            List<Object> reList = new ArrayList<Object>();
            String sql = "select * " +
            		"from t_system_person t order by t.system_id, t.user_status ";
            
            List<SystemManagerEntity> systems = 
                    dataManager.createNativeQuery(
                            sql,SystemManagerEntity.class)
                                    .setFirstResult(pageNum).setMaxResults(lines)
                                    .getResultList();
            
            reList.add(systems);
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
     * <p>Title: initUserStatuses</p>
     * <p>Description: </p>
     * @return
     * @see com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService#initUserStatuses()
     */
    @Override
    public String initUserStatuses() {
        try {
            List<ConstDetail> userStatuses = new ArrayList<ConstDetail>();
            String hql = "from ConstDetail c where c.constType.id = " + 200 + " order by c.id";
            userStatuses = dataManager.createQuery(hql).getResultList();
            return new Gson().toJson(userStatuses);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: addSysManagerEntity</p>
     * <p>Description: </p>
     * @param sysEntity
     * @return
     * @see com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService#addSysManagerEntity(com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity)
     */
    @Override
    @Transactional
    public String addSysManagerEntity(SystemManagerEntity sysEntity) {
        try {
            dataManager.update(sysEntity);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: modSysManagerEntity</p>
     * <p>Description: </p>
     * @param sysEntity
     * @return
     * @see com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService#modSysManagerEntity(com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity)
     */
    @Override
    @Transactional
    public String modSysManagerEntity(SystemManagerEntity sysEntity) {
        try {
            dataManager.update(sysEntity);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: delSysManagerEntity</p>
     * <p>Description: </p>
     * @param sysEntity
     * @return
     * @see com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService#delSysManagerEntity(com.sccl.serviceManager.supportSystemManager.entity.SystemManagerEntity)
     */
    @Override
    @Transactional
    public String delSysManagerEntity(SystemManagerEntity sysEntity) {
        try {
            dataManager.deleteById(SystemManagerEntity.class, sysEntity.getEid());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    /* 
     * <p>Title: findSysManagerEntityByQueryWindow</p>
     * <p>Description: </p>
     * @param sysID
     * @param userID
     * @param statusID
     * @param pageNum
     * @param lines
     * @param isCount
     * @return
     * @see com.sccl.serviceManager.supportSystemManager.service.ISupportSystemManagerService#findSysManagerEntityByQueryWindow(int, int, int, int, int, boolean)
     */
    @Override
    public String findSysManagerEntityByQueryWindow(int sysID, int userID,
            int statusID, int pageNum, int lines, boolean isCount) {
        String sql = "select t.system_id, t.user_id, t.user_status " +
        		"from t_system_person t " +
        		"where 1=1 ";
      
        if(sysID > 0)
            sql += " and t.system_id = " + sysID;
        if(userID > 0)
            sql += " and t.user_id = " + userID;
        if(statusID > 0)
            sql += " and t.user_status = " + statusID;
        
      sql += " order by t.system_id, t.user_status";
      
      try {
          
          List<Object> reList = new ArrayList<Object>();
          
          List<SystemManagerEntity> sysEntities = 
                  dataManager.createNativeQuery(sql, SystemManagerEntity.class)
                                  .setFirstResult(pageNum).setMaxResults(lines)
                                  .getResultList();
          
          reList.add(sysEntities);
          
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

}
