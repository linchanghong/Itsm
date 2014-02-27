/**
 * @Title: BugFlowTool.java
 * @Package com.sccl.serviceManager.bugManager.service
 * @Description: TODO-
 * @author Howie_Mark   
 * @date 2013-8-28 上午11:41:54
 * @version:V1.0 
 */
package com.sccl.serviceManager.bugManager.service;

import java.math.BigInteger;
import java.util.Date;

import com.sccl.flow.common.FlowTools;
import com.sccl.serviceManager.bugManager.entity.BugFlowInstence;

/**
 * @ProjectName:Itsm
 * @Package:com.sccl.serviceManager.bugManager.service.BugFlowTool.java
 * @ClassName: BugFlowTool
 * @Description: TODO-
 * @author:Howie_Mark
 * @UpdateUser:blue_sky
 * @UpdateDate:2013-8-28
 * @UpdateRemark:[The content of this revision]
 * @See:[Related classes and methods]
 * @Since:[Product template version]
 * @date 2013-8-28 上午11:41:54
 * @Version:v1.0
 */
public class BugFlowTool extends FlowTools {

    private static BugFlowTool tools;
    
    public static BugFlowTool getInstence() {
        if (tools == null) {
            tools= new BugFlowTool();
        }
        
        return tools;
    }
    

    /**
     * @Title: savaFlowInstence
     * @Description:TODO-
     * @param oprtTypeID
     * @param flowModelId
     * @param billID
     * @param personId
     * @param flowTitle 
     * @retunType:void      返回类型
     * @throws:
     */
    public void savaFlowInstence(Long oprtTypeID, Long flowModelId,
            BigInteger billID, String personId, String flowTitle) {
        BugFlowInstence instence = new BugFlowInstence();

        
//      instence.setFlowId(Long.valueOf(0));
        instence.setOprtTypeID(Long.valueOf(oprtTypeID));
        instence.setFlowModelId(flowModelId);
        instence.setBillID(billID);
        instence.setFlowState(42);  //未进入流程
        instence.setSenderId(Long.valueOf(personId));
        instence.setSendTime(new Date());
        instence.setFlowTitle(flowTitle);
//      instence.setOther1("");
//      instence.setOther2("");
//      instence.setCustomerName("");
//      instence.setSubOrBgnName("");
//      instence.setBusinessMoney(Double.valueOf(0));
//      instence.setFlowDeptId(Long.valueOf(0));
        
        this.getDataManager().add(instence);
    }
    
}
