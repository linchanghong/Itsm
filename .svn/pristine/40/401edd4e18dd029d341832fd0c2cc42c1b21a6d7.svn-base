/**
 * @Title: SupportSystemDTO.java
 * @Package com.sccl.serviceManager.bugManager.dto
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-7-28 下午5:38:18
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.dto;

import java.io.Serializable;
import java.util.List;

import com.sccl.serviceManager.bugManager.entity.SupportSystem;
import com.sccl.serviceManager.bugManager.entity.SystemModule;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.dto.SupportSystemDTO.java
 * @ClassName: SupportSystemDTO
 * @Description: 系统数据传输对象，主要用于向客户端传输一个系统列表,
 *               里面包含一个其所有业务模块列表。
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-7-28
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-7-28 下午5:38:18
 * @Version:v1.0
 */
public class SupportSystemDTO extends SupportSystem implements Serializable {

    /**
     * @Description: TODO-
     */
    private static final long serialVersionUID = 8024926470308654594L;

    /**
     * 该系统的所有业务模块列表
     */
    private List<SystemModule> systemModules;
    
    /**
     * @Title:SupportSystemDTO
     * @Description:
     */
    public SupportSystemDTO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return the systemModules
     */
    public List<SystemModule> getSystemModules() {
        return systemModules;
    }

    /**
     * @param systemModules the systemModules to set
     */
    public void setSystemModules(List<SystemModule> systemModules) {
        this.systemModules = systemModules;
    }

    
    public void changeToSupportSystemDTO(
            SupportSystem system,
            List<SystemModule> modules){
        
        this.setDR(system.getDR());
        this.setOnlineDate(system.getOnlineDate());
        this.setRemark(system.getRemark());
        this.setSystemCode(system.getSystemCode());
        this.setSystemID(system.getSystemID());
        this.setSystemName(system.getSystemName());
        this.setSystemModules(modules);
        
    }
    
//    public static SupportSystem changeToSupportSystem(SupportSystemDTO dto){
//        SupportSystem system = new SupportSystem();
//        system = dto.getSystem();
//        return system;
//    }
}
