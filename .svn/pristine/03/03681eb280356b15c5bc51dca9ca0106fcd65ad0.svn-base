package com.sccl.flow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.activemq.broker.UserIDBroker;
import org.springframework.stereotype.Component;

import com.sccl.flow.common.FlowException;
import com.sccl.flow.common.FlowTools;
import com.sccl.flow.entity.FlowInstence;
import com.sccl.flow.entity.FlowModel;
import com.sccl.flow.entity.FlowNodeInstence;
import com.sccl.flow.entity.FlowNodeInstenceNew;
import com.sccl.flow.entity.FlowNodeModel;
import com.sccl.flow.entity.FlowOprtType;
import com.sccl.flow.entity.FlowRoleUsers;
import com.sccl.flow.vo.FlowNodeModelFull;
import com.sccl.flow.vo.RoleAndNodeModel;
import com.sccl.flow.vo.ViewFlowApproval;
import com.sccl.flow.vo.ViewFlowNodeInstence;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.SystemStatic;
import com.sccl.framework.entity.MsPerson;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.query.DataQuery;
import com.sccl.framework.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

//流程状态
//42    41  未进入流程
//136	41	流程中
//137	41	流程结束
//138	41	流程退回

//流程节点状态
//141	44	流程节点未到
//142	44	流程节点到了
//145	44	流程节点通过
//146	44	流程节点否决
//147	44	流程节点等待
@SuppressWarnings("all")
@Component("flowGlobalService")
public class FlowGlobalService implements IFlowGlobalService {

    private DataManager dataManager;
    private int nodeOrderId = 0;

    public DataManager getDataManager() {
        return dataManager;
    }

    @Resource(name = "dataManager")
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * 呈报
     * 
     * @param user
     *            操作人
     * @param sendId
     *            发起人ID
     * @param flowTypeId
     *            流程业务ID
     * @param billid
     *            单据ID
     * @return
     */
    public List<FlowNodeInstence> startFlowInstence(MsPerson user, Long sendId,
            Long flowTypeId, long billid) throws FlowException { // ==改1

        List<FlowNodeModelFull> resultlist = new ArrayList<FlowNodeModelFull>();

        // 获得流程实例
        FlowInstence flowInstence = (FlowInstence) dataManager
                .createQuery(FlowInstence.class)
                .queryWhere(
                        "billID=" + billid + " and oprtTypeID=" + flowTypeId)
                .single();

        if (flowInstence == null)
            throw new FlowException("没有找到流程模型为本次呈报生成的流程实例，可能是流程未设置或生成单据之前没有流程。");

        // 已经处理过的，抛出已经处理的异常
        int flowState = flowInstence.getFlowState();
        if (flowState == 136 || flowState == 137)
            throw new FlowException("--此流程已经呈报过了，不能再呈报");

        // 得到流程发起人 按流程发起人走流程
        String condition = null;
        MsPerson sender = (MsPerson) dataManager.findById(MsPerson.class,
                Integer.valueOf(sendId.toString()));
        if (sender == null)
            throw new FlowException("--未找到流程发起人，人员是否已经被删除");
        else
            condition = "department:" + sender.getOrganization().getId();

        boolean isReSubmit = false;
        int peNodeCount = dataManager.createQuery(FlowNodeInstence.class)
                .queryWhere("flowID='" + flowInstence.getFlowId() + "'")
                .count();
        if (peNodeCount > 0)
            isReSubmit = true;

        // 再次呈报执行部分
        if (isReSubmit) {
            // 删除再次呈报退回的后面节点
            dataManager.createQuery(
                    "delete FlowNodeInstence where MakeRubbish=0 and FlowID='"
                            + flowInstence.getFlowId() + "'").executeUpdate();
        }

        // 流程模拟执行初始化数据
        // 获得该业务的流程模型ID
        DataQuery query = dataManager.createQuery(FlowModel.class).queryWhere(
                "ISActive = '1' and OprtTypeID =" + flowTypeId
                        + " and CompId ="
                        + sender.getOrganization().getCompanyId());

        if (query == null)
            throw new FlowException("--未设置流程模型，请联系公司管理员");

        FlowModel flowmodel = (FlowModel) query.single();

        long flowModelId = flowmodel.getFlowID();

        String flowNodeModelstrSQL = "select a.*, case when a.NodeMark = '-10' then a.NodeMark else TO_CHAR(b.FlowRoleID) end as FlowRoleID,"
                + " case when a.NodeMark = '-10' then '项目经理(项目所属)' else b.FlowRoleName end as FlowRoleName from FlowNodeModel a"
                + " Left Outer Join FlowRole b ON (case when regexp_like(a.NodeMark,'^\\d+$') then a.NodeMark else '' end) = b.FlowRoleID where a.FlowID = '"
                + flowModelId + "'";

        // 获得所有节点
        List<RoleAndNodeModel> rnList = (List<RoleAndNodeModel>) dataManager
                .nativeQuery(flowNodeModelstrSQL, RoleAndNodeModel.class);

        if (rnList == null || rnList.size() == 0)
            throw new FlowException("--未设置流程模型，请联系公司管理员");

        List<FlowNodeModelFull> thisFlowAllNodes = transformObject(rnList);
        // 新添加
        // 遍历所有节点 得到正确路径
        List<FlowNodeModelFull> correctPath = findNodePath(thisFlowAllNodes,
                billid, sender);

        long tfOpertTypeID = 0;
        // 对应省公司审批流程ID 非0则需要省公司审批
        FlowOprtType flowOprtType = (FlowOprtType) dataManager.findById(
                FlowOprtType.class, flowTypeId);
        if (flowOprtType != null)
            tfOpertTypeID = flowOprtType.getTFOprtTypeID();

        // 如果是预算或预算变更 若项目为亏损项目 必须由老总审批
        boolean isAddLeader = false;
        if (flowTypeId == 5 || flowTypeId == 17) {
            // 暂时不翻译这段代码
            // 判断项目是否为亏损项目
            // isAddLeader = new
            // BudSubBudgetDAL().ChekedLoss(Convert.ToInt32(billid));
        }

        // if (true) {// (allFenzhiContain)

        // 为真表示 流程模拟执行成功，生成了完整的路径。
        resultlist = correctPath;
        if (resultlist.size() < 2) {
            throw new FlowException("无适合分支，也无else分支。请检查模型");
        }

        FlowNodeInstence fni;
        // 如有没设置角色的节点，返回错误
        for (FlowNodeModelFull flownmf : resultlist) {
            if (flownmf.getNodeAttr() == 0 && flownmf.getNodeMark() != null
                    && flownmf.getNodeMark().trim().equals("")) {
                throw new FlowException(flownmf.getNodeName()
                        + flownmf.getNodeNumber() + " 没有设置角色，");
            }
        }

        // 将节点加入实例表
        List<FlowNodeInstence> nodeArrList = new ArrayList<FlowNodeInstence>();
        String endModleCode = "";
        boolean isAddBefore = false;
        // 最后一个为并联节点。且要加省公司审核
        boolean isAddandNode = false;
        FlowNodeInstence endandnode = null;

        for (FlowNodeModelFull fnmf : resultlist) {
            if (fnmf.getNodeAttr() == -1) {
                FlowNodeInstence beginFNI = new FlowNodeInstence();
                if (peNodeCount > 0)
                    beginFNI.setNodeAttr(-3);
                else
                    beginFNI.setNodeAttr(-1);

                beginFNI.setCheckState(145);
                beginFNI.setFlowId(flowInstence.getFlowId());
                beginFNI.setHandleDate(new Date());
                beginFNI.setFlowModelId(fnmf.getFlowID());
                beginFNI.setMakeRubbish(false);
                beginFNI.setModuleCode(fnmf.getModuleCode());
                beginFNI.setNextids(fnmf.getNextID());
                beginFNI.setNodeId(fnmf.getNodeID());
                beginFNI.setNodeNumber(fnmf.getNodeNumber());
                beginFNI.setHandlerId(Long.valueOf(user.getId()));
                beginFNI.setHandlerName(user.getPersonName());
                beginFNI.setFlowRoleName("");
                nodeArrList.add(beginFNI);
                if (flowTypeId == 3) {// 明细项目
                    // 暂且不翻译这段代码
                    // String err = "";
                    // nodeArrList = addFlowNodeForSJ(nodeArrList,
                    // beginFNI, billid, sender.getCompId(), err,
                    // isAddBefore);
                    // if (nodeArrList == null)
                    // throw new FlowException(err);
                }
                // **************
                if (fnmf.getNextID() != null
                        && fnmf.getNextID().contains("EndNode")) {
                    if (tfOpertTypeID != 0) {// 需要省公司审批
                        // 暂时不翻译此段代码
                        nodeArrList = addLeaderNode(nodeArrList, beginFNI,
                                tfOpertTypeID, billid, sender, flowmodel,
                                endModleCode); // 添加省公司结点r
                    }
                    if (isAddLeader) { // 亏损项目需要老总审批
                        // 暂时不翻译此段代码
                        // String err = "";
                        // nodeArrList = addOwnCompLeaderNode(resultlist,
                        // nodeArrList, beginFNI, sender
                        // .getCompId(), flowmodel, err);
                        // if (nodeArrList == null)
                        // throw new FlowException(err);
                    }
                }
                continue;
            }

            if (fnmf.getNodeAttr() == -2) {
                FlowNodeInstence endFNI = new FlowNodeInstence();
                endFNI.setCheckState(141);
                endFNI.setFlowId(flowInstence.getFlowId());
                endFNI.setHandleDate(new Date());
                endFNI.setFlowModelId(fnmf.getFlowID());
                endFNI.setMakeRubbish(false);
                endFNI.setModuleCode(fnmf.getModuleCode());
                endFNI.setNextids(fnmf.getNextID());
                endFNI.setNodeAttr(fnmf.getNodeAttr());
                endFNI.setNodeId(fnmf.getNodeID());
                endFNI.setNodeNumber(fnmf.getNodeNumber());
                endFNI.setHandlerId(Long.valueOf(user.getId()));
                endFNI.setHandlerName(user.getPersonName());
                endFNI.setFlowRoleName("");
                nodeArrList.add(endFNI);
                continue;
            }

            fni = new FlowNodeInstence();
            if (flowmodel.getIsSubmitModify())
                fni.setCheckState(141);
            else
                fni.setCheckState(isAddBefore ? 141 : liveFirstNode(fnmf,
                        resultlist));

            if (fni.getCheckState() == 142)
                fni.setComeDate(new Date());

            fni.setFlowId(flowInstence.getFlowId());
            fni.setHandleDate(new Date());
            fni.setFlowModelId(fnmf.getFlowID());
            fni.setMakeRubbish(false);
            fni.setModuleCode(fnmf.getModuleCode());
            fni.setNextids(fnmf.getNextID());
            fni.setNodeAttr(fnmf.getNodeAttr());
            fni.setNodeId(fnmf.getNodeID());
            fni.setNodeNumber(fnmf.getNodeNumber());
            fni.setOther1(fnmf.getAndNodeMark());
            fni.setDealWithDay(fnmf.getDealWithDay());
            fni.setIsNodeOneself(fnmf.getIsNodeOneself());
            fni.setIsNeedCheckDate(fnmf.getIsNeedCheckDate());
            String flowUserName = sender.getPersonName();
            fni = makeSureDoer(sender, fnmf, fni, billid, flowTypeId);

            if (fni == null) {
                throw new FlowException("--" + fnmf.getFlowRoleName()
                        + " 的管辖范围中未找到：" + flowUserName);
            }

            nodeArrList.add(fni);
            fni.setFlowRoleName(fnmf.getNodeName());

            if (fnmf.getNextID().contains("EndNode")) {
                if (isAddandNode) {
                    fni.setNextids(endandnode.getNextids());
                    continue;
                }
                isAddandNode = true;
                endandnode = fni;
                if (isAddLeader) { // 亏损项目需要老总审批

                    // String err = "";
                    // nodeArrList = addOwnCompLeaderNode(resultlist,
                    // nodeArrList, fni, sender.getCompId(),flowmodel, err);
                    // if (nodeArrList == null)
                    // throw new FlowException(err);
                }
                if (tfOpertTypeID != 0) { // 需要省公司审批

                    nodeArrList = addLeaderNode(nodeArrList, fni,
                            tfOpertTypeID, billid, sender, flowmodel,
                            endModleCode); // 添加省公司结点r
                }
            }
        }

        nodeOrderby(nodeArrList, peNodeCount);

        if (nodeArrList.size() == 0)
            throw new FlowException("流程生成失败，请重新呈报");
        if (flowmodel.getIsSubmitModify()) {
            for (FlowNodeInstence nodeinstence : nodeArrList) {
                // nodeinstence.setIsSubmitModify(flowmodel.getIsSubmitModify());
                // nodeinstence.setOprtTypeID(flowTypeId);
            }
            // return nodeArrList;
        }
        if (nodeArrList.size() == 2) { // 开始-->结束
            // new
            // FlowInstenceDAL().finishFlowInstence(flowInstence.getFlowId());

            for (FlowNodeInstence nodeinstence : nodeArrList) {
                if (nodeinstence.getNodeAttr() == -2)
                    nodeinstence.setCheckState(142);
                // control.AddMainItemsEntity(nodeinstence);
            }
            // 回调函数
            try {
                // sendEndExe(flowInstence.OprtTypeID, flowInstence.BillID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        try {
            int resultSave = 0;
            int resultInt = 0;
            String sTemp = "";
            for (int i = 0; i < nodeArrList.size(); i++) {
                // nodeArrList.get(i).setIsSubmitModify(flowmodel.getIsSubmitModify());
                // resultInt =
                // control.addMainItemsEntity(nodeArrList.get(i));
                try {
                    dataManager.add(nodeArrList.get(i));
                    resultInt = 1;
                } catch (Exception e) {
                    throw new FlowException("流程生成失败，请重新呈报");
                }
                sTemp = sTemp + resultInt + ",";
                if (resultInt == 0) {
                    resultSave = 1;
                    break;
                }
            }
            if ((resultSave == 0) && (resultInt != 0)) {
                // 开始正式跑流程，修改流程状态为“流程中”
                // control.UpdateEntityValue_Transaction("update FlowInstence set FlowState=136, SendTime= getdate(),SenderId="
                // + operUserId + ",Other2='" + sTemp +
                // "' from FlowInstence where Flowid = " +
                // flowInstence.FlowId, "Model.FlowInstence");
                FlowInstence fi = (FlowInstence) dataManager
                        .createQuery(FlowInstence.class)
                        .queryWhere("Flowid='" + flowInstence.getFlowId() + "'")
                        .single();
                fi.setFlowState(136);
                fi.setSenderId(Long.valueOf(user.getId()));
                fi.setSendTime(new Date());
                fi.setOther2(sTemp);
                dataManager.update(fi);
            } else {
                throw new FlowException("流程生成失败，请重新呈报");
            }
        } catch (Exception e) {
            throw new FlowException("流程生成失败，请重新呈报");
        }
        // **************

        // 复制到实例备份表中。
        createFreshFlowNodeInstence(flowInstence.getFlowId());
        return nodeArrList;
    }

    private void createFreshFlowNodeInstence(long flowId) {

        dataManager.createQuery(
                "delete FlowNodeInstenceNew t where t.flowId='" + flowId + "'")
                .executeUpdate();

        String where = "FlowID = '"
                + flowId
                + "' and InstID >= (select Max(InstID)"
                + " from FlowNodeInstence where NodeNumber='BeginNode' and FlowID='"
                + flowId + "')";
        List<FlowNodeInstence> list = dataManager
                .createQuery(FlowNodeInstence.class).queryWhere(where).list();
        for (FlowNodeInstence flowNodeInstence : list) {
            FlowNodeInstenceNew flowNodeInstenceNew = transformObject(flowNodeInstence);
            flowNodeInstenceNew.setInstId(null);
            dataManager.add(flowNodeInstenceNew);
        }
    }

    /**
     * 转换vo
     * 
     * @param list
     * @return
     */
    private FlowNodeInstenceNew transformObject(
            FlowNodeInstence flowNodeInstence) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        String json = gson.toJson(flowNodeInstence);
        FlowNodeInstenceNew flowNodeInstenceNew = gson.fromJson(json,
                FlowNodeInstenceNew.class);
        return flowNodeInstenceNew;
    }

    /**
     * 转换vo
     * 
     * @param list
     * @return
     */
    private List<FlowNodeModelFull> transformObject(List<RoleAndNodeModel> list) {

        Gson gson = Tools.getGson();
        JsonArray ja = new JsonArray();
        JsonObject jo = null;
        String json = "[]";
        for (RoleAndNodeModel item : list) {
            jo = new JsonObject();
            json = gson.toJson(item);
            Set<Entry<String, JsonElement>> set = new JsonParser().parse(json)
                    .getAsJsonObject().entrySet();
            Iterator<Entry<String, JsonElement>> it = set.iterator();
            String key = "";
            while (it.hasNext()) {
                Entry<String, JsonElement> entry = it.next();
                key = entry.getKey();
                jo.add(key.substring(0, 1).toLowerCase() + key.substring(1),
                        entry.getValue());
            }
            ja.add(jo);
        }

        json = gson.toJson(ja);
        List<FlowNodeModelFull> nodeModelList = gson.fromJson(ja,
                new TypeToken<List<FlowNodeModelFull>>() {
                }.getType());

        return nodeModelList;
    }

    /**
     * 查找开始节点
     * 
     * @param nodeList
     * @return
     */
    private FlowNodeModelFull getBeginNode(List<FlowNodeModelFull> nodeList) {

        for (int i = 0; i < nodeList.size(); i++) {
            FlowNodeModelFull node = nodeList.get(i);
            if (node.getNodeAttr() == -1)
                return node;
        }

        return null;
    }

    /**
     * 根据Number查询节点
     * 
     * @param nodeList
     * @param nodeNumber
     * @return
     */
    private FlowNodeModelFull getNodeByNodeNumber(
            List<FlowNodeModelFull> nodeList, String nodeNumber) {
        for (FlowNodeModelFull node : nodeList) {
            if (node.getNodeNumber().equals(nodeNumber))
                return node;
        }
        return null;
    }

    /**
     * 设置后续节点
     * 
     * @param currentNodeList
     * @param nextIDs
     */
    private void setNextNodeId(List<FlowNodeModelFull> currentNodeList,
            String nextIDs) {
        int len = currentNodeList.size() - 1;

        FlowNodeModelFull fnm = currentNodeList.get(len);
        String tempid = fnm.getApposeNodeIDs();

        if (tempid != null && "1".equals(tempid)) {
            String andNodemark = fnm.getAndNodeMark();
            if (andNodemark == null)
                andNodemark = "";
            for (int i = len; i >= 0; i--) {
                FlowNodeModelFull full = currentNodeList.get(i);
                if (!"1".equals(full.getApposeNodeIDs())
                        || !andNodemark.equals(full.getAndNodeMark()))
                    break;
                full.setNextID(nextIDs);
            }
        } else
            currentNodeList.get(len).setNextID(nextIDs);
    }

    /**
     * 判断是否是上级部门
     * 
     * @param flowDeptId
     * @param sendDeptId
     * @return
     */
    private boolean checkPrantDept(String flowDeptId, long sendDeptId) {
        // String strSQL = " select * from CusDepartment where DeptId = "
        // + sendDeptId
        // +
        // " and LevelCode like ( select LevelCode+'%' from CusDepartment  where  DeptId = "
        // + flowDeptId + " )";
        //
        // int count = dataManager.getCountBySql(strSQL);
        // if (count > 0)
        // return true;
        // else
        // return false;

        List<Organization> list = dataManager
                .createQuery(Organization.class)
                .queryWhere(
                        "org_id='" + sendDeptId + "' or parent_id='"
                                + sendDeptId + "'").list();

        for (Organization org : list) {
            String orgId = String.valueOf(org.getId());
            if (orgId.equals(flowDeptId)) {
                return true;
            } else {
                checkPrantDept(orgId, sendDeptId);
            }
        }

        return false;
    }

    /**
     * 根据是否属于这个角色管辖判断，如果是属于这个角色管辖返回true反之则false
     * 
     * @param roleId
     * @param sendUser
     * @return
     */
    private boolean checkFlowRole(String roleId, MsPerson sendUser) {
        // String strSQL =
        // " select ManageType,ManageDepts from FlowRoleUsers where  FlowRoleID= "
        // + roleId;

        List<FlowRoleUsers> ds = dataManager.createQuery(FlowRoleUsers.class)
                .queryWhere("FlowRoleID='" + roleId + "'").list();

        if (ds != null && ds.size() > 0) {
            int count = ds.size();
            int manageType;
            String manageDepts;
            for (int i = 0; i < count; i++) {
                FlowRoleUsers roleUsers = ds.get(i);
                manageType = roleUsers.getManageType();
                manageDepts = roleUsers.getManageDepts();
                if (manageType == 0) { // 管辖人员
                    String[] users = manageDepts.split(",");
                    for (String user : users) {
                        if (user == String.valueOf(sendUser.getId()))
                            return true;
                    }
                } else if (manageType == 1) { // 管辖部门

                    String[] depts = manageDepts.split(",");
                    for (String dpet : depts) {
                        if (dpet == null || dpet.equals(""))
                            continue;
                        if (dpet == String.valueOf(sendUser.getOrganization()
                                .getId()))
                            return true;
                        else { // 判断上级部门
                            boolean result = checkPrantDept(dpet, sendUser
                                    .getOrganization().getId());
                            if (result)
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 寻找路径
     * 
     * @param nodeList
     * @param billid
     * @param sender
     * @return
     * @throws FlowException
     */
    private List<FlowNodeModelFull> findNodePath(
            List<FlowNodeModelFull> nodeList, long billid, MsPerson sender)
            throws FlowException {
        List<FlowNodeModelFull> currentNodeList = new ArrayList<FlowNodeModelFull>();
        // 查找开始节点
        FlowNodeModelFull beginNode = getBeginNode(nodeList);
        if (beginNode == null)
            throw new FlowException("--未找到开始节点");
        currentNodeList.add(beginNode);
        String roleName = beginNode.getNodeName();
        String nextIDs = beginNode.getNextID();
        int nextNodeAttr = 0;
        // 总节点数
        int nodesLens = nodeList.size();
        // 循环次数
        int nodei = 0;
        // 根据后续节点判断,直到结束节点
        while (nextNodeAttr != -2) {
            // 如果将执行的所有节点次数后，还未结束，说明流程设置有问题。
            if (nodei > nodesLens) {
                throw new FlowException("--流程设置有误，请联系管理员检查流程模型设置");
            }
            nodei++;
            boolean hasNextNode = false;
            String[] nodes = nextIDs.split(",");
            FlowNodeModelFull midNextNode = null;
            for (int i = 0; i < nodes.length; i++) {
                String nextNodeName = nodes[i];
                if (nextNodeName == null || nextNodeName.equals(""))
                    continue;
                FlowNodeModelFull nextNode = getNodeByNodeNumber(nodeList,
                        nextNodeName);
                if (nextNode == null)
                    continue;

                if (midNextNode == null)
                    midNextNode = nextNode;
                else if (midNextNode.getNodeNumber() != nextNode
                        .getNodeNumber())
                    throw new FlowException("--" + roleName + " 的前置节点有多个后续分支节点");
                else
                    continue;

                switch (nextNode.getNodeAttr()) {
                case 0:
                case -2:
                    setNextNodeId(currentNodeList,
                            "," + nextNode.getNodeNumber());
                    currentNodeList.add(nextNode);
                    nextIDs = nextNode.getNextID();
                    nextNodeAttr = nextNode.getNodeAttr();
                    roleName = nextNode.getNodeName();
                    hasNextNode = true;
                    break;
                case 2: // 部门节点
                    String nodeMark = nextNode.getNodeMark();
                    String[] deptIfs = nodeMark.split(",");
                    List<FlowNodeModelFull> resultNodeList = new ArrayList<FlowNodeModelFull>();
                    for (String deptIf : deptIfs) {
                        if (deptIf == null || deptIf.equals(""))
                            continue;
                        String deptId = "0";
                        String currNodeName = deptIf.split("-")[1];
                        String[] ifnodes = deptIf.split("-")[0].split("&");
                        for (String ifnode : ifnodes) {
                            if (ifnode == null || ifnode.equals(""))
                                continue;
                            deptId = ifnode.split(":").length > 1 ? ifnode
                                    .split(":")[1] : "0";
                            if (ifnode.indexOf("department") >= 0) {
                                if (deptId.equals(String.valueOf(sender
                                        .getOrganization().getId()))) {
                                    resultNodeList.add(getNodeByNodeNumber(
                                            nodeList, currNodeName));
                                    break;
                                } else {// 判断是否是上级部门
                                    if (checkPrantDept(deptId, sender
                                            .getOrganization().getId())) {
                                        resultNodeList.add(getNodeByNodeNumber(
                                                nodeList, currNodeName));
                                        break;
                                    }
                                }
                            } else if (deptIf.indexOf("flowRole") >= 0) {
                                if (checkFlowRole(deptId, sender)) { // 符合角色权限
                                    resultNodeList.add(getNodeByNodeNumber(
                                            nodeList, currNodeName));
                                    break;
                                }
                            }
                        }
                    }
                    if (resultNodeList.size() == 0) {
                        for (String deptIf : deptIfs) {
                            if (deptIf == null || deptIf.equals(""))
                                continue;
                            String currNodeName = deptIf.split("-")[1];
                            if (deptIf.indexOf(">>>") >= 0) {
                                if (resultNodeList.size() == 0)
                                    resultNodeList.add(getNodeByNodeNumber(
                                            nodeList, currNodeName));
                            }
                        }
                    }
                    if (resultNodeList.size() == 0)
                        throw new FlowException("--" + nextNode.getNodeName()
                                + "无符合条件的分支");
                    else if (resultNodeList.size() > 1)
                        throw new FlowException("--" + nextNode.getNodeName()
                                + "有多个符合条件的分支");
                    else {
                        FlowNodeModelFull dNode = resultNodeList.get(0);
                        if (dNode.getNodeAttr() == 0
                                || dNode.getNodeAttr() == -2) {
                            setNextNodeId(currentNodeList,
                                    "," + dNode.getNodeNumber()); // (currentNodeList[currentNodeList.Count
                            // - 1] as
                            // FlowNodeModelFull).NextID
                            // = "," +
                            // dNode.NodeNumber;
                            currentNodeList.add(dNode);
                            nextIDs = dNode.getNextID();
                        } else
                            nextIDs = dNode.getNodeNumber();
                        nextNodeAttr = dNode.getNodeAttr();
                        roleName = dNode.getNodeName();
                        hasNextNode = true;
                    }
                    break;
                case 3: // 并
                    String[] andNodes = nextNode.getNextID().split(",");
                    String andNodeMark = nextNode.getNodeMark();
                    String andNextIds = "";
                    List<FlowNodeModelFull> andNodeList = new ArrayList<FlowNodeModelFull>();
                    String nextNextNumber = "";
                    for (String andN : andNodes) {
                        if (andN == null || andN.equals(""))
                            continue;
                        FlowNodeModelFull andNode = getNodeByNodeNumber(
                                nodeList, andN);
                        if (andNode.getNodeAttr() != 0)
                            throw new FlowException("--并节点后续节点不能是条件节点");
                        andNode.setAndNodeMark(andNodeMark);
                        andNode.setApposeNodeIDs("1");
                        andNextIds += "," + andNode.getNodeNumber();
                        andNodeList.add(andNode);
                        String[] nextNumbers = andNode.getNextID().split(",");
                        for (String numbers : nextNumbers) {
                            if (numbers == null || numbers.equals(""))
                                continue;
                            if (nextNextNumber.equals(""))
                                nextNextNumber = numbers;
                            else if (nextNextNumber.equals(numbers))
                                throw new FlowException("--并节点上的所有节点的后续节点应该一致");
                        }
                        nextIDs = andNode.getNextID();
                        nextNodeAttr = andNode.getNodeAttr();
                        roleName = andNode.getNodeName();
                        hasNextNode = true;
                    }
                    setNextNodeId(currentNodeList, nextNode.getNextID());
                    for (FlowNodeModelFull nodeFull : andNodeList) {
                        currentNodeList.add(nodeFull);
                    }
                    break;
                case 4: // 自定义条件
                    String NodeMark = nextNode.getNodeMark();
                    String sql = NodeMark.substring(0, NodeMark.indexOf("||"));
                    if (sql.contains("%d")) {
                        sql = sql.replace("%d", String.valueOf(billid));
                    } else {
                        throw new FlowException("流程模型中的"
                                + nextNode.getNodeName() + " 不含有%d 标识符 ");
                    }

                    List customDs = dataManager.nativeQuery(sql);

                    int fromnum = NodeMark.indexOf("||");
                    String conditionStr = NodeMark.substring(fromnum + 2);// ,
                                                                          // (NodeMark.length()
                                                                          // -
                                                                          // fromnum
                                                                          // -
                                                                          // 2));
                    String[] conditionArr = conditionStr.split(",");

                    if (customDs != null) {
                        if (customDs.size() >= 2) {
                            throw new FlowException(nextNode.getNodeName()
                                    + "拥有多个符合条件的分支，");
                        } else if (customDs.size() == 0) {
                            throw new FlowException(nextNode.getNodeName()
                                    + "条件设置有误");
                        } else if (customDs.size() == 1) {
                            Map<String, Object> map = (Map<String, Object>) customDs
                                    .get(0);
                            Set<String> key = map.keySet();
                            if (key.size() != 1) {
                                throw new FlowException(nextNode.getNodeName()
                                        + "条件设置有误");
                            }

                            String selectResult = String.valueOf(map.get(key
                                    .iterator().next()));
                            int meetCount = 0;
                            for (String condition : conditionArr) {
                                if (condition.length() <= 1) {
                                    continue;
                                }

                                int index = condition.indexOf("-");
                                String conditionvalue = condition.substring(0,
                                        index);
                                String aimNode = condition.substring(index + 1,
                                        condition.length());
                                if (conditionvalue.equals(selectResult)) {
                                    hasNextNode = true;
                                    meetCount++;
                                    FlowNodeModelFull tnextNode = getNodeByNodeNumber(
                                            nodeList, aimNode);
                                    if (tnextNode.getNodeAttr() == 0
                                            || tnextNode.getNodeAttr() == -2) {
                                        setNextNodeId(currentNodeList, ","
                                                + tnextNode.getNodeNumber());// (currentNodeList[currentNodeList.Count
                                        // -
                                        // 1]
                                        // as
                                        // FlowNodeModelFull).NextID
                                        // =
                                        // ","
                                        // +
                                        // tnextNode.NodeNumber;
                                        currentNodeList.add(tnextNode);
                                        nextIDs = tnextNode.getNextID();
                                    } else
                                        nextIDs = tnextNode.getNodeNumber();
                                    nextNodeAttr = tnextNode.getNodeAttr();
                                    roleName = tnextNode.getNodeName();
                                }

                            }
                            // 判断是否有多个到达条件
                            // 如：200-TaskNode19,100-TaskNode21,100-CustomIfNode38,100-IfNode39,
                            if (meetCount == 0)
                                throw new FlowException("--" + roleName
                                        + "节点--未找到后续节点");
                            else if (meetCount > 1)
                                throw new FlowException("--" + roleName
                                        + "节点--找到多个后续节点");
                        }
                    }
                    break;
                case 5: // 自定义条件 ==改3

                    String andparam = nextNode.getApposeNodeIDs();
                    String[] param = andparam.split("&");

                    List csDs = null;
                    
                    if(param.length > 2 && "true".equals(param[2])){
                        
                        csDs = new ArrayList<Object>();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("USERNAME", "待定");
                        map.put("USERID", "-1");
                        csDs.add(map);
                        
                    }else{
                        
                        String cssql = nextNode.getNodeMark();
                        if (cssql.contains("%d")) {
                            cssql = cssql.replace("%d", String.valueOf(billid));
                        } else {
                            throw new FlowException("流程模型中的"
                                    + nextNode.getNodeName() + " 不含有%d 标识符 ");
                        }

                        csDs = dataManager.nativeQuery(cssql);
                        
                    }

                    // DataSet csDs = control.ExecuteQueryWheres(cssql);
                    if (csDs != null) {
                        if (csDs.size() == 0) {
                            throw new FlowException(nextNode.getNodeName()
                                    + "人员查询设置有误");
                        }
                        
                        String andAutidFlag = "true";
                        if (param.length > 0)
                            andAutidFlag = (param[0].equals("true")) ? "false"
                                    : "true";
                        
                        String customNextIds = "";
                        List<FlowNodeModelFull> customNodeList = new ArrayList<FlowNodeModelFull>();

                        String customrAndMark = "";
                        // DataRowCollection dc = csDs.Tables[0].Rows;
                        for (int j = 0; j < csDs.size(); j++) {
                            Map map = (Map) csDs.get(j);
                            if (map.size() == 2) {
                                Object valueId = map
                                        .get("userId".toUpperCase());
                                Object valueName = map.get("userName"
                                        .toUpperCase());
                                String userId = String.valueOf(valueId);
                                String userName = String.valueOf(valueName);
                                
//                                // 判断是否为待定节点
//                                if (param.length > 2 && "true".equals(param[2])) {// 待定节点
//                                    userName = "待定";
//                                    userId = "-1";
//                                } 
                                
                                
                                // //创建并联审批节点
                                FlowNodeModelFull custNode = new FlowNodeModelFull();

                                custNode.setNodeID(nextNode.getNodeID());
                                custNode.setNodeName(nextNode.getNodeName());
                                custNode.setNodeAttr(nextNode.getNodeAttr());
                                custNode.setNodeNumber(nextNode.getNodeNumber()
                                        + "cin" + j);
                                custNode.setFlowID(nextNode.getFlowID());
                                custNode.setModuleCode(nextNode.getModuleCode());
                                custNode.setNodeMark("");
                                custNode.setPointX(nextNode.getPointX());
                                custNode.setPointY(nextNode.getPointY()
                                        + (j * 10));
                                custNode.setPreviewID(nextNode.getPreviewID());
                                custNode.setFlowRoleID(Long.valueOf(userId));
                                custNode.setFlowRoleName(userName);
                                custNode.setDealWithDay(nextNode
                                        .getDealWithDay());
                                custNode.setNextID(nextNode.getNextID());
                                custNode.setIsNodeOneself(nextNode
                                        .getIsNodeOneself());
                                custNode.setIsNeedCheckDate(nextNode
                                        .getIsNeedCheckDate());

                                custNode.setApposeNodeIDs((csDs.size() > 1 ? "1"
                                        : "0"));
                                customNextIds += "," + custNode.getNodeNumber();
                                customrAndMark += custNode.getNodeNumber()
                                        + "|" + andAutidFlag + "&";
                                customNodeList.add(custNode);
                            } else
                                throw new FlowException(nextNode.getNodeName()
                                        + "人员查询设置有误");

                        }
                        if (param.length > 1) {
                            String p2 = ("true".equals(param[1])) ? "true"
                                    : "false";
                            customrAndMark += "$" + p2;
                        }
                        nextIDs = nextNode.getNextID();
                        nextNodeAttr = nextNode.getNodeAttr();
                        roleName = nextNode.getNodeName();
                        setNextNodeId(currentNodeList, customNextIds);
                        hasNextNode = true;
                        for (FlowNodeModelFull nodeFull : customNodeList) {
                            if (customNodeList.size() > 1)
                                nodeFull.setAndNodeMark(customrAndMark);
                            currentNodeList.add(nodeFull);
                        }
                        // if (param.length > 2) {
                        // String p3 = ("true".equals(param[2])) ? "true" :
                        // "false";
                        // customrAndMark += "$" + p3;
                        // }
                    } else
                        throw new FlowException(nextNode.getNodeName()
                                + "人员查询设置有误");
                    break;
                }
            }
            if (!hasNextNode)
                throw new FlowException("--" + roleName + "节点--未找到后续节点");
        }

        return currentNodeList;
    }

    /**
     * 针对设计增加部门经理-项目主管-项目经理流程
     * 
     * @param NodeArrList
     * @param preNode
     * @param billid
     * @param compId
     * @param string
     * @return
     */
    private List addFlowNodeForSJ(List nodeArrList, FlowNodeInstence preNode,
            long billid, long compId, String errStr, boolean isAddBefore) {
        errStr = "";
        isAddBefore = false;

        return null;
    }

    /**
     * 增加省公司节点 preNode 前节点
     * 
     * @param NodeArrList
     * @param preNode
     * @param flowTypeId
     * @param billid
     * @param sender
     * @param flowmodel
     * @param string
     * @return
     */
    private List addLeaderNode(List<FlowNodeInstence> nodeArrList,
            FlowNodeInstence preNode, long flowTypeId, long billid,
            MsPerson sender, FlowModel flowmodel, String endModleCode)
            throws FlowException {// ==改4

        List oldNodeArrList = nodeArrList;

        endModleCode = "";
        // 得到省公司ID
        int compId = Integer.valueOf(SystemStatic.COMPANY_ID);

        // if
        // (String.valueOf(compId).equals(sender.getOrganization().getCompanyId()))
        // return nodeArrList;

        List<FlowNodeModelFull> thisFlowAllNodes = new ArrayList<FlowNodeModelFull>();
        List conditions = new ArrayList();
        // 获得该业务的流程模型ID
        FlowModel flowModel = null;
        long flowModelId = -1;
        try {
            flowModel = (FlowModel) dataManager
                    .createQuery(FlowModel.class)
                    .queryWhere(
                            "ISActive = '1' and OprtTypeID ='" + flowTypeId
                                    + "' and CompId ='" + compId + "'")
                    .single();

            if (flowModel == null)
                throw new FlowException("--省公司流程段流程模型未设置");
            else
                flowModelId = flowModel.getFlowID();

        } catch (Exception e) {
            throw new FlowException("--查找省公司流程段流程模型出错");
        }

        // 获得所有节点
        thisFlowAllNodes = getAllNodeByModelId(flowModelId);
        for (FlowNodeModelFull flowNodeModel : thisFlowAllNodes) // 遍历该模型中的所有节点
        {
            // 开始，结束，角色，自定义，并，自定义条件
            // if (flowNodeModel.NodeAttr == 2)
            // {
            // throw new PopUpException("--省公司流程设置有误，不能含有if条件(部门)");
            // }
            if (flowNodeModel.getNodeAttr() == 0
                    && flowNodeModel.getNodeMark().trim().equals("")) {
                throw new FlowException("--省公司流程设置没有设置角色，");
            }

        }
        // fj 2012-6-12
        // 得到正确路径
        List<FlowNodeModelFull> nodeList = findNodePath(thisFlowAllNodes,
                billid, sender); // 根据条件得到路径节点
        // 为避免重复修改nodeNumber;
        for (FlowNodeModelFull fnmf : nodeList) {
            if (fnmf.getNodeAttr() != -1 && fnmf.getNodeAttr() != -2)
                fnmf.setNodeNumber("P-" + fnmf.getNodeNumber());
            if (fnmf.getNextID() != null) {
                String[] nextids = fnmf.getNextID().split(",");
                String nextid = "";
                for (String s : nextids) {
                    if (s == null || "".equals(s))
                        continue;
                    String nodenmber = s;
                    if (!s.equals("BeginNode") && !s.equals("EndNode"))
                        nodenmber = "P-" + s;
                    nextid += (nextid == null ? "" : ",") + nodenmber;
                }
                fnmf.setNextID(nextid);
            }
            if (fnmf.getAndNodeMark() != null
                    && !fnmf.getAndNodeMark().equals("")) {
                String[] andnodemark = fnmf.getAndNodeMark().split("&");
                String andmark = "";
                for (String s : andnodemark) {
                    if (s == null || "".equals(s))
                        continue;
                    String nodenmber = s;
                    if (!s.equals("BeginNode") && !s.equals("EndNode")
                            && s.indexOf("$") != 0)
                        nodenmber = "P-" + s;
                    andmark += ((andmark == null || andmark.equals("")) ? ""
                            : "&") + nodenmber;
                }
                fnmf.setAndNodeMark(andmark);
            }
        }

        int flag = 0;
        // 遍历所有节点
        for (int i = 0; i < nodeList.size(); i++) // 遍历该模型中的所有节点
        {
            FlowNodeModelFull flowNodeModel = nodeList.get(i);
            if (flowNodeModel.getNodeAttr() == -1) {
                preNode.setNextids(flowNodeModel.getNextID());
                continue;
            }
            if (flowNodeModel.getNodeAttr() == -2)
                continue;

            FlowNodeInstence pfni = new FlowNodeInstence();
            pfni.setNodeNumber(flowNodeModel.getNodeNumber());
            pfni.setFlowId(preNode.getFlowId());
            pfni.setHandleDate(new Date());
            pfni.setFlowModelId(flowModelId);
            pfni.setMakeRubbish(false);
            pfni.setModuleCode(flowNodeModel.getModuleCode());
            pfni.setNextids(flowNodeModel.getNextID());
            pfni.setNodeAttr(0);
            pfni.setNodeId(preNode.getNodeId());
            pfni.setSenderId(preNode.getSenderId());
            pfni.setSenderName(preNode.getSenderName());
            pfni.setSenderDeptId(preNode.getSenderDeptId());
            pfni.setNodeOrder(preNode.getNodeOrder());
            pfni.setFlowRoleName("省公司--" + flowNodeModel.getFlowRoleName());
            String StrErr = "";
            String flowUserName = sender.getPersonName();
            pfni = makeSureDoer(sender, flowNodeModel, pfni, billid, flowTypeId);
            if (pfni == null) {
                if (StrErr.equals(""))
                    StrErr = "--省公司-" + flowNodeModel.getFlowRoleName()
                            + " 的管辖范围中未找到：" + flowUserName; // sender.UserName;
                throw new FlowException(StrErr);
            }
            pfni.setCheckState(141);
            if (flag == 0) {
                if (preNode.getNodeNumber().equals("BeginNode")
                        && flowmodel.getIsSubmitModify() == false)
                    pfni.setCheckState(142); // 待审批
                else
                    pfni.setCheckState(141); // 未到
            }
            flag = 1;
            nodeArrList.add(pfni);
        }

        return nodeArrList;
    }

    /**
     * 通过流程模型ID获得
     * 
     * @param FlowModelID
     * @return
     */
    public List<FlowNodeModelFull> getAllNodeByModelId(long flowModelID) {
        List<FlowNodeModelFull> resultlist = new ArrayList<FlowNodeModelFull>();
        // DataSet FlowRoleDS = new DataSet();

        String flowNodeModelstrSQL = "select a.*, case when a.NodeMark = '-10' then a.NodeMark else TO_CHAR(b.FlowRoleID) end as FlowRoleID,"
                + " case when a.NodeMark = '-10' then '项目经理(项目所属)' else b.FlowRoleName end as FlowRoleName from FlowNodeModel a"
                + " Left Outer Join FlowRole b ON (case when regexp_like(a.NodeMark,'^\\d+$') then a.NodeMark else '' end) = b.FlowRoleID where a.FlowID = '"
                + flowModelID + "'";

        List<RoleAndNodeModel> flowNodeModelDS = (List<RoleAndNodeModel>) dataManager
                .nativeQuery(flowNodeModelstrSQL, RoleAndNodeModel.class);

        for (RoleAndNodeModel flowNodeModelDataRow : flowNodeModelDS) {
            FlowNodeModelFull fnmf = new FlowNodeModelFull();
            fnmf.setApposeNodeIDs(flowNodeModelDataRow.getApposeNodeIDs());
            fnmf.setFlowID(flowNodeModelDataRow.getFlowID());
            fnmf.setModuleCode(flowNodeModelDataRow.getModuleCode());
            fnmf.setNextID(flowNodeModelDataRow.getNextID());
            fnmf.setNodeAttr(flowNodeModelDataRow.getNodeAttr());
            fnmf.setNodeID(flowNodeModelDataRow.getNodeID());
            fnmf.setNodeMark(flowNodeModelDataRow.getNodeMark());
            fnmf.setNodeName(flowNodeModelDataRow.getNodeName());
            fnmf.setNodeNumber(flowNodeModelDataRow.getNodeNumber());
            fnmf.setPointX(flowNodeModelDataRow.getPointX());
            fnmf.setPointY(flowNodeModelDataRow.getPointY());
            // fnmf.PreviewID = FlowNodeModelDataRow["PreviewID"].ToString();
            // fj 处理天数据
            fnmf.setDealWithDay(flowNodeModelDataRow.getDealWithDay() == null ? 0
                    : flowNodeModelDataRow.getDealWithDay());
            // 节点独立
            fnmf.setIsNodeOneself(flowNodeModelDataRow.getIsNodeOneself() == null ? false
                    : flowNodeModelDataRow.getIsNodeOneself());
            fnmf.setIsNeedCheckDate(flowNodeModelDataRow.getIsNeedCheckDate() == null ? false
                    : flowNodeModelDataRow.getIsNeedCheckDate());
            if (flowNodeModelDataRow.getNodeAttr() == 0) {
                // wmd 2009-09-29
                // foreach (DataRow FlowRoleRow in FlowRoleDS.Tables[0].Rows)
                // {
                // if (FlowRoleRow["FlowRoleID"].ToString() ==
                // FlowNodeModelDataRow["NodeMark"].ToString())
                // {
                // fnmf.FlowRoleID =
                // int.Parse(FlowRoleRow["FlowRoleID"].ToString());
                // fnmf.FlowRoleName = FlowRoleRow["FlowRoleName"].ToString();
                // }
                // }
                if (!"".equals(flowNodeModelDataRow.getFlowRoleID()))
                    fnmf.setFlowRoleID(Long.valueOf(flowNodeModelDataRow
                            .getFlowRoleID()));
                fnmf.setFlowRoleName(flowNodeModelDataRow.getFlowRoleName());
            }
            resultlist.add(fnmf);
        }

        return resultlist;
    }

    /**
     * 添加老总节点
     * 
     * @param resultList
     * @param nodeArrList
     * @param preNode
     * @param compId
     * @param flowmodel
     * @param errStr
     * @return
     */
    private List addOwnCompLeaderNode(List resultList, List nodeArrList,
            FlowNodeInstence preNode, long compId, FlowModel flowmodel,
            String errStr) {

        return null;
    }

    /**
     * 将beginnode 后的第一个节点激活
     * 
     * @param currentNode
     * @param flownodelist
     * @return
     */
    private int liveFirstNode(FlowNodeModelFull currentNode,
            List<FlowNodeModelFull> flownodelist) {
        // 非 beginnnode后的节点 设置其状态为 141， 是第一个节点的就设置为142
        int resultCheckState = 141;
        for (FlowNodeModelFull fnmf : flownodelist) {
            if (fnmf.getNextID() != null && fnmf.getNodeAttr() == -1) {
                for (String beginnextNodeNumber : fnmf.getNextID().split(",")) {
                    if (!beginnextNodeNumber.equals("")
                            && beginnextNodeNumber.equals(currentNode
                                    .getNodeNumber())) {
                        resultCheckState = 142;
                    }
                }
            }

        }
        return resultCheckState;
    }

    /**
     * 确定审核者
     * 
     * @param sender
     * @param fnmf
     * @param fni
     * @param billid
     * @param flowTypeId
     * @return
     */
    private FlowNodeInstence makeSureDoer(MsPerson sender,
            FlowNodeModelFull fnmf, FlowNodeInstence fni, long billid,
            long flowTypeId) throws FlowException {
        // 节点属性,0:普通节点（选择）,1特殊高级节点（自定义）,2:if .3:and, -1:beging. -2:end

        String flowUserName = sender.getPersonName();
        // 如果是项目经理（系统自带）
        if (fnmf.getNodeMark().equals("-10")) {
            String strSQLType = "OprtTypeID=" + flowTypeId;

            FlowOprtType flowOprtType = (FlowOprtType) dataManager
                    .createQuery(FlowOprtType.class).queryWhere(strSQLType)
                    .single();
            if (flowOprtType != null) {
                strSQLType = flowOprtType.getProjectManagerSQL();
                if (strSQLType != null && !strSQLType.equals("")) {
                    if (strSQLType.contains("%d")) {
                        strSQLType = strSQLType.replace("%d",
                                String.valueOf(billid));
                        List<MsPerson> ds = (List<MsPerson>) dataManager
                                .nativeQuery(strSQLType, MsPerson.class);
                        if (ds != null && ds.size() > 0) {
                            fni.setHandlerId(Long.valueOf(ds.get(0).getId()));
                            fni.setHandlerName(ds.get(0).getPersonName());
                            return fni;
                        } else {
                            throw new FlowException("--未找到项目的项目经理");
                        }
                    } else {
                        throw new FlowException("--项目经理（自带）条件设置有误");
                    }
                }
            } else {
                throw new FlowException("--未找流程标识");
            }
        }
        int userCount = 0;
        switch (fnmf.getNodeAttr()) {
        case -2:
            break;
        case -1:
            break;
        case 0:
            List<FlowRoleUsers> flowRoleUsersDs = new ArrayList<FlowRoleUsers>();

            String handerid = "0";
            String handername = "";
            long senderDeptId = sender.getOrganization().getId();
            long senderUserId = sender.getId();
            long senderCompId = Long.valueOf(sender.getOrganization()
                    .getCompanyId());

            if (!fnmf.getNodeMark().trim().equals("") && senderDeptId != 0) {

                String sqlstr = " select  * from FlowRoleUsers t where t.FlowRoleID ='"
                        + fnmf.getNodeMark() + "'";

                flowRoleUsersDs = dataManager.createQuery(FlowRoleUsers.class)
                        .queryWhere("FlowRoleID ='" + fnmf.getNodeMark() + "'")
                        .list();
                String managetype = "";
                String[] manageDepts;// 这种角色管理的是部门
                String[] manageUsers;// 这种角色管理的是人员
                String[] manageComps; // 管辖公司

                for (FlowRoleUsers flowRoleUsers : flowRoleUsersDs) {

                    managetype = String.valueOf(flowRoleUsers.getManageType());
                    if (managetype.equals("1")) { // 管辖部门

                        manageDepts = flowRoleUsers.getManageDepts().split(",");
                        for (String managedepart : manageDepts) {
                            if (!"".equals(managedepart)
                                    && senderDeptId == Long
                                            .valueOf(managedepart)) {
                                handerid = flowRoleUsers.getUserID();
                                try {
                                    MsPerson cusUsers = (MsPerson) dataManager
                                            .findById(MsPerson.class,
                                                    Integer.valueOf(handerid));
                                    handername = cusUsers.getPersonName();
                                    userCount++;
                                } catch (Exception e) {
                                    handername = "编号" + handerid;

                                    throw new FlowException("--未找到的流程角色："
                                            + (fnmf == null ? ""
                                                    : fnmf.getFlowRoleName())
                                            + "定义包含的审批人员，编号为：" + handerid);
                                }
                                break;
                            }
                        }
                    } else if (managetype.equals("0")) { // 管辖人员

                        manageUsers = flowRoleUsers.getManageDepts().split(",");
                        for (String manageUserId : manageUsers) {
                            if (String.valueOf(senderUserId).equals(
                                    manageUserId)) {
                                handerid = flowRoleUsers.getUserID();
                                try {
                                    handername = ((MsPerson) dataManager
                                            .findById(MsPerson.class,
                                                    Integer.valueOf(handerid)))
                                            .getPersonName();
                                    userCount++;
                                } catch (Exception e) {
                                    handername = "编号" + handerid;
                                    // wmd----2009-12-07
                                    throw new FlowException("--未找到的流程角色："
                                            + (fnmf == null ? ""
                                                    : fnmf.getFlowRoleName())
                                            + "定义包含的审批人员，编号为：" + handerid);
                                }
                                break;
                            }
                        }
                    } else if (managetype.equals("2")) { // 管辖公司 fj 2012-6-12

                        manageComps = flowRoleUsers.getManageDepts().split(",");
                        for (String managecomp : manageComps) {
                            if (!"".equals(managecomp)
                                    && String.valueOf(senderCompId).equals(
                                            managecomp)) {
                                handerid = flowRoleUsers.getUserID();
                                try {
                                    handername = ((MsPerson) dataManager
                                            .findById(MsPerson.class,
                                                    Integer.valueOf(handerid)))
                                            .getPersonName();
                                    userCount++;
                                } catch (Exception e) {
                                    handername = "编号" + handerid;
                                    throw new FlowException("--未找到的流程角色："
                                            + (fnmf == null ? ""
                                                    : fnmf.getFlowRoleName())
                                            + "定义包含的审批人员，编号为：" + handerid);
                                }

                                break;
                            }
                        }
                    }
                }
            }

            if (handerid.equals("0") || handername.equals(""))
                throw new FlowException("未找到模型对应的角色！");
            fni.setHandlerId(Long.valueOf(handerid));
            fni.setHandlerName(handername);

            if (userCount > 1) {
                throw new FlowException("--查询到"
                        + (fnmf == null ? "" : fnmf.getFlowRoleName())
                        + "同一个流程角色下，审批人员管辖范围重叠：");
            }

            break;
        case 1:
            break;
        case 2:
            fni = null;
            break;
        case 3:
            break;
        case 5:
            fni.setHandlerId(fnmf.getFlowRoleID());
            fni.setHandlerName(fnmf.getFlowRoleName());
            break;

        }
        return fni;
    }

    /**
     * 流程实例增加排序顺序号
     * 
     * @param NodesArr
     * @param reNodeCount
     * @throws FlowException
     */
    private void nodeOrderby(List<FlowNodeInstence> nodesArr, int reNodeCount)
            throws FlowException {
        nodeOrderId = reNodeCount;
        setNodeOrder(nodesArr, findNode(nodesArr, "BeginNode"));
    }

    /**
     * 查找节点顺序号
     * 
     * @param alNode
     *            实例数据集
     * @param nodeName
     *            节点名
     * @return
     * @throws FlowException
     */
    private int findNode(List<FlowNodeInstence> alNode, String nodeName)
            throws FlowException {
        int result = -1;
        String nodeStr = "";
        for (int i = 0; i < alNode.size(); i++) {
            nodeStr = alNode.get(i).getNodeNumber();
            if (nodeStr.toLowerCase().equals(nodeName.toLowerCase())) {
                result = i;
                break;
            }
        }

        if (result == -1)
            throw new FlowException("生成流程失败！");
        return result;
    }

    /**
     * 递归设置顺序号
     * 
     * @param ALNodes
     *            实例数据集
     * @param NumId
     *            记录行号
     * @throws FlowException
     */
    private void setNodeOrder(List<FlowNodeInstence> alNodes, int numId)
            throws FlowException {
        nodeOrderId++;
        alNodes.get(numId).setNodeOrder(nodeOrderId);
        if (alNodes.get(numId).getNextids() == null
                || alNodes.get(numId).getNextids().equals("")) {
            return;
        } else {
            String nextStr = alNodes.get(numId).getNextids();
            String[] nextArr = nextStr.split(",");
            for (int i = 0; i < nextArr.length; i++) {
                if (nextArr[i].equals(""))
                    continue;
                setNodeOrder(alNodes, findNode(alNodes, nextArr[i]));
            }
        }
    }

    @Override
    public int getAllCount(String wherestr) {
        String sql = "select * from View_FlowNodeInstence";
        if (wherestr != null && !wherestr.equals("")) {
            sql = sql + " where " + wherestr;
        }

        return dataManager.getCountBySql(sql);
    }

    /**
     * 根据条件查询不同的数据
     * 
     * @param pageIndex
     *            页号
     * @param pageSize
     *            每页记录数
     * @param wherestr
     *            查询条件
     * @param orderBy
     *            排序<
     * @return
     */
    public List<ViewFlowNodeInstence> getPageData(int pageIndex, int pageSize,
            String wherestr, String orderBy) {
        String sql = "select * from View_FlowNodeInstence";
        if (wherestr != null && !wherestr.equals("")) {
            sql = sql + " where " + wherestr;
        }
        if (orderBy != null && !orderBy.equals("")) {
            sql = sql + " order by " + orderBy;
        }

        List<ViewFlowNodeInstence> list = (List<ViewFlowNodeInstence>) dataManager
                .nativeQuery(sql, ViewFlowNodeInstence.class);
        int count = list.size();
        int end = pageIndex + pageSize;
        if (list != null && count != 0) {

            if (count >= end) {

                list.subList(pageIndex, end);
            } else {
                list.subList(pageIndex, count);
            }
        } else {
            return null;
        }

        return list;
    }

    /**
     * 该流程的所有历史审批记录
     * 
     * @param typeid
     * @param billid
     * @return
     */
    public List<ViewFlowNodeInstence> getHistory(String typeid, String billid) {

        String sql = "select * from View_FlowNodeInstence where oprttypeid='"
                + typeid + "' and billid='" + billid + "' order by NodeOrder";
        List<ViewFlowNodeInstence> list = (List<ViewFlowNodeInstence>) dataManager
                .nativeQuery(sql, ViewFlowNodeInstence.class);

        return list;
    }

    public List<ViewFlowNodeInstence> getFlowNodeInstenceList(String typeId,
            String billid, String compId) {

        DataQuery query = dataManager.createQuery(FlowInstence.class)
                .queryWhere(
                        "BillID ='" + billid + "' and OprtTypeID='" + typeId
                                + "'");
        List<ViewFlowNodeInstence> list = new ArrayList<ViewFlowNodeInstence>();
        if (query != null) {
            List<FlowInstence> flows = query.list();
            FlowInstence instance = null;
            if (flows != null && flows.size() > 0) {
                instance = flows.get(0);
            }
            if (instance != null)
                list = getHistoryByFlowInsteceId(String.valueOf(instance
                        .getFlowId()));
        }
        return list;
    }

    /**
     * 获取审批历史数据 根据业务类型、业务ID
     * 
     * @param flowInsteceId
     * @return
     */
    public List<ViewFlowNodeInstence> getHistoryByFlowInsteceId(
            String flowInsteceId) {

        String sql = "select * from View_FlowNodeInstence where FlowID='"
                + flowInsteceId + "' order by NodeOrder";
        List<ViewFlowNodeInstence> list = (List<ViewFlowNodeInstence>) dataManager
                .nativeQuery(sql, ViewFlowNodeInstence.class);

        return list;
    }

    /**
     * 处理审批
     * 
     * @param flowNodeInstence
     *            这个是当前用户处理的节点
     * @param turnUser
     * @param moduecode
     * @return
     */
    public List<FlowNodeInstence> sendFlowTodo(ViewFlowApproval currentFni,
            MsPerson turnUser, String moduecode) throws FlowException {
        if (fniHasDone(currentFni)) {

            throw new FlowException("该待办已经处理过了，不需再处理。");
        }
        List<FlowNodeInstence> resultList = new ArrayList<FlowNodeInstence>();
        FlowInstence flowInstence = (FlowInstence) dataManager.findById(
                FlowInstence.class, currentFni.getFlowId());

        List<ViewFlowApproval> allFNIDs = (List<ViewFlowApproval>) dataManager
                .nativeQuery("select * from View_FlowApproval where FlowID ="
                        + currentFni.getFlowId() + " and MakeRubbish = '0'",
                        ViewFlowApproval.class);

        // 同意
        if (currentFni.getCheckState() == 145) {
            // 得到同级节点（除自己）
            List<ViewFlowApproval> brogherFNIlist = getBrotherFNIlist(allFNIDs,
                    currentFni);
            // 判断是否并联兄弟审批
            Boolean isBrogther = false;
            if ((currentFni.getNodeAttr() == 0 || currentFni.getNodeAttr() == 5)
                    && brogherFNIlist.size() > 0)
                isBrogther = true;
            // 转办
            if (currentFni.getNodeAttr() != -2 && turnUser != null) {
                FlowNodeInstence turnNode = addNextNodeByTurnNode(currentFni,
                        turnUser, isBrogther, moduecode);

                turnNode.setOther1(currentFni.getOther1());
                dataManager.add(turnNode);
                FlowNodeInstence tempFlowNodeInstence = this.nodesDStoObject(
                        currentFni, false);
                tempFlowNodeInstence.setNextids("," + turnNode.getNodeNumber());
                tempFlowNodeInstence.setHandleDate(new Date());
                dataManager.update(tempFlowNodeInstence);
                // 并联审批 修改并联条件 加上转办节点
                if (isBrogther) {
                    for (ViewFlowApproval brogherNode : brogherFNIlist) {
                        FlowNodeInstence tempInstence = nodesDStoObject(
                                brogherNode, false);
                        tempInstence.setOther1(currentFni.getOther1());
                        dataManager.update(tempInstence);
                    }
                }

                resultList.add(turnNode);
                return resultList;
            }

            // 得到下级节点
            List<ViewFlowApproval> nextFNIlist = getNextFNIlist(allFNIDs,
                    currentFni, true);

            if (currentFni.getNodeAttr() == -2) {
                // 如果当前节点为 -2 表示最后一节点。
                FlowNodeInstence tempFlowNodeInstence = this.nodesDStoObject(
                        currentFni, false);
                tempFlowNodeInstence.setHandleDate(new Date());
                dataManager.update(tempFlowNodeInstence);
                return resultList;
            }

            if (isBrogther) {
                String outAndNodeName = "";
                int checkState = checkAndCheckState(brogherFNIlist, currentFni);
                if (checkState != 145) // 等待其他人审批
                {
                    FlowNodeInstence tempFlowNodeInstence = this
                            .nodesDStoObject(currentFni, false);
                    ;
                    tempFlowNodeInstence.setHandleDate(new Date());
                    dataManager.update(tempFlowNodeInstence);
                    tempFlowNodeInstence.setHandlerName("--等待其他同事审批-"
                            + outAndNodeName);
                    resultList.add(tempFlowNodeInstence);
                    return resultList;
                } else // 判断是否并联竞争
                {
                    if (getAndNodeIsCompetition(currentFni)) // 如果为真将未审批的兄弟节点都审批通过
                    {
                        for (ViewFlowApproval brogherNode : brogherFNIlist) {
                            if (brogherNode.getCheckState() == 142) {
                                FlowNodeInstence tempFlowNodeInstence = this
                                        .nodesDStoObject(currentFni, false);
                                tempFlowNodeInstence.setCheckState(145);
                                tempFlowNodeInstence.setOther2("被竞争审批-"
                                        + brogherNode.getOther2());
                                tempFlowNodeInstence.setHandleDate(new Date());
                                dataManager.update(tempFlowNodeInstence);
                            }
                        }
                    }
                }
            }

            FlowNodeInstence flowNodeInstence = this.nodesDStoObject(
                    currentFni, false);
            flowNodeInstence.setHandleDate(new Date());
            dataManager.update(flowNodeInstence);

            for (ViewFlowApproval nextfni : nextFNIlist) {
                FlowNodeInstence tempInstence = nodesDStoObject(nextfni, false);
                tempInstence.setComeDate(new Date());
                tempInstence.setHandleDate(new Date());
                if (tempInstence.getNodeAttr() == -2) { // 如果下个节点是结束节点， 则将
                    // 流程的状态修改为137 结束
                    flowInstence.setFlowState(137);
                    flowInstence.setSendTime(new Date());
                    dataManager.update(flowInstence);
                    // try
                    // {
                    // if (currentFni.getBillId() != 0)
                    // SendEndExe(flowInstence.getOprtTypeID(),
                    // currentFni.getBillId());
                    // }
                    // catch (Exception ex) //回调执行有误
                    // {
                    // tempInstence.setOther1(ex.getMessage());
                    // }
                    // finally
                    // {
                    tempInstence.setTotalFlowId(137);
                    tempInstence.setDealWithIdea("通过");
                    resultList.add(tempInstence);
                    // }
                }

                tempInstence.setCheckState(142);
                // 定义是否已经是否修改下一步人
                // Boolean isModifyFlow = updateNextNode(currentFni, nextfni);

                dataManager.update(tempInstence);
                if (tempInstence.getHandlerId() == currentFni.getHandlerId()
                        && tempInstence.getIsNodeOneself() == false) { // 如果下一个人还是自己。
                    nextfni.setCheckState(145);

                    /** 执行sendFlowTodo将会把以前的事务提交，需要重新创建一个事务 **/
                    dataManager.flush();
                    sendFlowTodo(nextfni, null, "");
                } else {
                    resultList.add(tempInstence);
                }
            }

            return resultList;
        }

        // 不同意
        else if (currentFni.getCheckState() == 146) // 拒绝
        {
            if (currentFni.getNodeAttr() == -2) {
                throw new FlowException("通过通知，不能处理“不同意”操作。");
            }

            List<FlowNodeInstence> nextFNIlist = getAllNextFNIlist(allFNIDs,
                    currentFni);
            for (FlowNodeInstence netFlownode : nextFNIlist) {
                if (netFlownode.getCheckState() != 141) {
                    throw new FlowException("该待办已经流转到下一步，不能处理“不同意”操作。");
                }

            }

            // 逐级回退的情况
            FlowModel flowModel = (FlowModel) dataManager.findById(
                    FlowModel.class, currentFni.getFlowModelId());
            if (flowModel.getIsOneByOne()) // 逐级回退的情况
            {
                String FathersFlowNodesStr = "";
                String BrothersFlowNodesStr = "";
                List<ViewFlowApproval> FathersFlowNodes = getFatherFNlist(
                        allFNIDs, currentFni);
                for (ViewFlowApproval fathernodeInstence : FathersFlowNodes) {
                    FathersFlowNodesStr += fathernodeInstence.getInstId() + ",";
                }

                FathersFlowNodesStr = FathersFlowNodesStr.trim();
                if (FathersFlowNodesStr.endsWith(",")) {
                    FathersFlowNodesStr = FathersFlowNodesStr.substring(0,
                            FathersFlowNodesStr.lastIndexOf(","));
                }

                List<ViewFlowApproval> BrotherFlowNodes = getBrotherFNIlist(
                        allFNIDs, currentFni);
                for (ViewFlowApproval brothernodeInstence : BrotherFlowNodes) {
                    BrothersFlowNodesStr += brothernodeInstence.getInstId()
                            + ",";
                }

                if (BrothersFlowNodesStr.length() > 0)
                    BrothersFlowNodesStr += currentFni.getInstId();
                else {
                    // BrothersFlowNodesStr = BrothersFlowNodesStr.Substring(0,
                    // FathersFlowNodesStr.Length - 1);
                    BrothersFlowNodesStr = String.valueOf(currentFni
                            .getInstId());
                }

                long flowId = flowInstence.getFlowId();
                long instId = currentFni.getInstId();
                String instIds = BrothersFlowNodesStr;
                String prarentInstIDs = FathersFlowNodesStr;
                String dealWithIdea = currentFni.getDealWithIdea();

                flowUntreadtoPrev(flowId, instId, instIds, prarentInstIDs,
                        dealWithIdea);

            } else { // 直接回退的情况
                String sql = "select * from View_FlowApproval where FlowID='"
                        + currentFni.getFlowId() + "' and CheckState='145'";
                List<ViewFlowApproval> list = (List<ViewFlowApproval>) dataManager
                        .nativeQuery(sql, ViewFlowApproval.class);
                FlowNodeInstence oldBeginNoder = getBeginNodeInstence(list);
                FlowNodeInstence toSenderFNI = new FlowNodeInstence();
                toSenderFNI.setFlowId(flowInstence.getFlowId());
                toSenderFNI.setFlowModelId(flowInstence.getFlowModelId());
                // toSenderFNI.setFlowTitle("被退回 - " +
                // flowInstence.getFlowTitle());
                toSenderFNI.setHandlerId(flowInstence.getSenderId());
                toSenderFNI.setCheckState(142);
                // toSenderFNI.setBillId(currentFni.getBillId());

                toSenderFNI.setMakeRubbish(false);
                toSenderFNI.setModuleCode(oldBeginNoder.getModuleCode());
                toSenderFNI.setNextids(oldBeginNoder.getNextids());
                toSenderFNI.setNodeAttr(-3);
                toSenderFNI.setNodeId(currentFni.getNodeId());
                toSenderFNI.setNodeNumber("BeginNode");
                toSenderFNI.setTotalFlowId(oldBeginNoder.getTotalFlowId());
                MsPerson cusUsers = (MsPerson) dataManager.findById(
                        MsPerson.class,
                        Integer.valueOf(flowInstence.getSenderId().toString()));
                toSenderFNI.setHandlerName("呈报者（" + cusUsers.getPersonName()
                        + "）");

                resultList.add(toSenderFNI);

                long flowId = flowInstence.getFlowId();
                long instId = currentFni.getInstId();
                String dealWithIdea = currentFni.getDealWithIdea();

                flowUntreadtoBegin(flowId, instId, dealWithIdea);
            }
        }

        return resultList;
    }

    /**
     * 验证待办是否处理
     * 
     * @param flowNodeInstence
     * @return
     */
    private boolean fniHasDone(ViewFlowApproval flowNodeInstence) {
        FlowNodeInstence fni = (FlowNodeInstence) dataManager
                .createQuery(FlowNodeInstence.class)
                .queryWhere("instId='" + flowNodeInstence.getInstId() + "'")
                .single();

        if (fni != null && fni.getCheckState() != 142) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得同级节点信息
     * 
     * @param allflownodeinstence
     *            所有节点
     * @param currentFni
     *            当前
     * @return
     */
    private List<ViewFlowApproval> getBrotherFNIlist(
            List<ViewFlowApproval> allflownodeinstence,
            ViewFlowApproval currentFni) {
        List<String> borhternumbers = getBrotherNumbers(allflownodeinstence,
                currentFni);
        List<ViewFlowApproval> resultList = new ArrayList<ViewFlowApproval>();
        ViewFlowApproval currentFNI;
        for (String brothernumber : borhternumbers) {
            if (brothernumber.equals("")) {
                continue;
            }
            currentFNI = getFNIbyNumber(brothernumber, allflownodeinstence);
            resultList.add(currentFNI);
        }
        return resultList;
    }

    private List<String> getBrotherNumbers(
            List<ViewFlowApproval> allflownodeinstence,
            ViewFlowApproval currentFni) {
        String brotherNumbers = "";
        List<String> brothernumberlist = new ArrayList<String>();
        for (ViewFlowApproval view : allflownodeinstence) {
            String nextIds = view.getNextids();

            if (nextIds == null || nextIds.equals(""))
                continue;
            for (String nextid : nextIds.split(",")) {

                if (nextid != "" && currentFni.getNodeNumber().equals(nextid)) {
                    brotherNumbers = nextIds;
                }
            }
        }
        for (String brotherNumber : brotherNumbers.split(",")) {
            if (!brotherNumber.equals(currentFni.getNodeNumber())) {
                brothernumberlist.add(brotherNumber);
            }
        }
        return brothernumberlist;

    }

    private ViewFlowApproval getFNIbyNumber(String flownodeInstenceNumber,
            List<ViewFlowApproval> allflownodeinstence) {
        for (ViewFlowApproval view : allflownodeinstence) {
            if (view.getNodeNumber().equals(flownodeInstenceNumber)) {
                return view;
            }
        }
        return null;
    }

    /**
     * 转换vo
     * 
     * @param view
     * @return
     */
    private FlowNodeInstence nodesDStoObject(ViewFlowApproval view, boolean bool) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String json = "{}";

        JsonObject jo = new JsonObject();
        json = gson.toJson(view);
        Set<Entry<String, JsonElement>> set = new JsonParser().parse(json)
                .getAsJsonObject().entrySet();
        Iterator<Entry<String, JsonElement>> it = set.iterator();
        String key = "";
        while (it.hasNext()) {
            Entry<String, JsonElement> entry = it.next();
            key = entry.getKey();
            jo.add(key.substring(0, 1).toLowerCase() + key.substring(1),
                    entry.getValue());
        }

        json = gson.toJson(jo);

        FlowNodeInstence flowNodeInstence = gson.fromJson(json,
                FlowNodeInstence.class);

        return flowNodeInstence;
    }

    /**
     * 转办
     * 
     * @param currentFni
     * @param turnUser
     * @param isBrogther
     * @param moduecode
     * @return
     */
    private FlowNodeInstence addNextNodeByTurnNode(ViewFlowApproval currentFni,
            MsPerson turnUser, Boolean isBrogther, String moduecode) {
        FlowNodeInstence turnNode = new FlowNodeInstence();
        turnNode.setNodeId(currentFni.getNodeId());
        turnNode.setNodeNumber(currentFni.getNodeNumber() + "-t");
        turnNode.setNodeAttr(currentFni.getNodeAttr());
        turnNode.setFlowModelId(currentFni.getFlowModelId());
        turnNode.setFlowId(currentFni.getFlowId());
        turnNode.setModuleCode(moduecode == null || moduecode.equals("") ? currentFni
                .getModuleCode() : moduecode);
        turnNode.setCheckState(142);
        turnNode.setMakeRubbish(currentFni.getMakeRubbish());
        turnNode.setDealWithIdea("");
        turnNode.setHandlerName(turnUser.getPersonName());
        turnNode.setHandlerId(Long.valueOf(turnUser.getId()));
        turnNode.setModuleID(Integer.valueOf(String.valueOf(currentFni
                .getModuleId())));
        turnNode.setHandleDate(new Date());
        turnNode.setNextids(currentFni.getNextids());
        turnNode.setOther1(currentFni.getOther1());
        turnNode.setOther2(currentFni.getOther2());
        turnNode.setTotalFlowId(Integer.valueOf(String.valueOf(currentFni
                .getTotalFlowId())));
        turnNode.setSenderId(currentFni.getSenderId());
        turnNode.setSenderName(currentFni.getSenderName());
        turnNode.setSenderDeptId(currentFni.getSenderDeptId());
        turnNode.setNodeOrder(currentFni.getNodeOrder() + 0.01);
        turnNode.setFlowRoleName(currentFni.getFlowRoleName() + "(转办)");

        if (isBrogther) // 如果是并联审批
        {
            String nodeconditons = currentFni.getOther1();
            if (nodeconditons != null && !nodeconditons.equals("")) {
                // TaskNode6|false&TaskNode7|false&TaskNode8|false&$true
                String nodemarks = getAndNodeArticle(currentFni);
                String[] nodeMark = nodemarks.split("&");
                for (String mark : nodeMark) // 判断兄弟节点条件
                {
                    if (mark == null || mark.equals(""))
                        continue;
                    String[] vlues = mark.split("|");
                    if (vlues.length > 1) {
                        if (vlues[0].equals(currentFni.getNodeNumber())) {
                            currentFni.setOther1(turnNode.getNodeNumber() + '|'
                                    + vlues[1] + '&' + currentFni.getOther1());
                            break;
                        }
                    }
                }
            }
        }
        turnNode.setOther1(currentFni.getOther1());
        return turnNode;
    }

    /**
     * 得到并联审批条件
     * 
     * @param currentFni
     * @return
     */
    private String getAndNodeArticle(ViewFlowApproval currentFni) {
        String flowmark = currentFni.getOther1();
        int findex = flowmark.indexOf("$");
        String nodemarks = flowmark.substring(0,
                findex == -1 ? flowmark.length() : findex);
        return nodemarks;
    }

    private List<ViewFlowApproval> getNextFNIlist(
            List<ViewFlowApproval> allflownodeinstence,
            ViewFlowApproval currentFni, boolean needAll) throws FlowException {
        List<ViewFlowApproval> nextFNIlist = new ArrayList<ViewFlowApproval>();
        String[] nodeNumbers;
        ViewFlowApproval nextFlowNodeinstence;
        boolean MakeRubbish = false;
        List<ViewFlowApproval> bortherList = new ArrayList<ViewFlowApproval>();
        bortherList = getBrotherFNIlist(allflownodeinstence, currentFni);
        boolean brotherAgreed = true;
        for (ViewFlowApproval brotherfni : bortherList) {
            if (brotherfni.getCheckState() != 145) // 145 流程节点通过
            {
                brotherAgreed = false;
            }
        }

        if (currentFni.getNextids() != null) {
            int flag = 0;
            for (String nextid : currentFni.getNextids().split(",")) {
                for (ViewFlowApproval myflowTodoRow : allflownodeinstence) {
                    nodeNumbers = myflowTodoRow.getNodeNumber().split(",");
                    MakeRubbish = myflowTodoRow.getMakeRubbish();
                    if (myflowTodoRow.getCheckState() != 141) // 141 44 流程节点未到
                    {
                        continue;
                    }
                    for (String nodeNumber : nodeNumbers) {

                        if (nodeNumber.equals(nextid)
                                && (brotherAgreed || needAll)
                                && MakeRubbish == false) {
                            nextFlowNodeinstence = new ViewFlowApproval();
                            nextFlowNodeinstence = myflowTodoRow; // nodesDStoObject(myflowTodoRow,
                            // false);

                            // 处理待定节点
                            if (nextFlowNodeinstence.getHandlerId() == -1
                                    && "待定".equals(nextFlowNodeinstence
                                            .getHandlerName())) {

                                Long billid = nextFlowNodeinstence.getBillId();
                                Long nodeId = nextFlowNodeinstence.getNodeId();
                                List csDs = null;

                                FlowNodeModel node = dataManager.findById(
                                        FlowNodeModel.class, nodeId);

                                if(node == null)
                                    throw new FlowException(
                                            "节点未找到!");
                                
                                String cssql = node.getNodeMark();
                                if (cssql.contains("%d")) {
                                    cssql = cssql.replace("%d",
                                            String.valueOf(billid));
                                    cssql += " order by UserId asc";
                                } else {
                                    throw new FlowException("流程模型中的"
                                            + node.getNodeName()
                                            + " 不含有%d 标识符 ");
                                }

                                csDs = dataManager.nativeQuery(cssql);

                                if (csDs != null) {
                                    if (csDs.size() == 0) {
                                        throw new FlowException(
                                                node.getNodeName() + "人员查询设置有误");
                                    }

                                    if(flag < csDs.size())
                                    for (int j = flag; j < csDs.size(); j++) {
                                        Map map = (Map) csDs.get(j);
                                        if (map.size() == 2) {
                                            Object valueId = map.get("userId"
                                                    .toUpperCase());
                                            Object valueName = map
                                                    .get("userName"
                                                            .toUpperCase());
                                            String userId = String
                                                    .valueOf(valueId);
                                            String userName = String
                                                    .valueOf(valueName);
                                            nextFlowNodeinstence.setHandlerId(Long.parseLong(userId));
                                            nextFlowNodeinstence.setHandlerName(userName);
                                            flag++;
                                            break;
                                        }
                                    }
                                }
                                
                                
//                                nextFlowNodeinstence.setHandlerId(currentFni.getHandlerId());
//                                nextFlowNodeinstence.setHandlerName(currentFni.getHandlerName());
                            }
                            nextFlowNodeinstence.setCheckState(142);
                            SimpleDateFormat sdf = new SimpleDateFormat();
                            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                            nextFlowNodeinstence.setHandleDate(sdf
                                    .format(new Date()));
                            nextFlowNodeinstence.setSenderId(currentFni
                                    .getHandlerId());
                            nextFlowNodeinstence.setSenderName(currentFni
                                    .getHandlerName());
                            nextFlowNodeinstence.setTotalFlowId(currentFni
                                    .getTotalFlowId());
                            nextFlowNodeinstence.setBillId(currentFni
                                    .getBillId());

                            nextFNIlist.add(nextFlowNodeinstence);
                        }
                    }

                }

            }
        }
        return nextFNIlist;
    }

    /**
     * 判断并联节点
     * 
     * @param brogherFNIlist
     * @param currentFni
     * @return
     */
    private int checkAndCheckState(List<ViewFlowApproval> brogherFNIlist,
            ViewFlowApproval currentFni) {
        int checkState = 145;
        String outAndNodeName = "";
        String nodemarks = getAndNodeArticle(currentFni);
        String[] nodeMark = nodemarks.split("&");
        for (String mark : nodeMark) // 判断兄弟节点是否含有必须审批的人
        {
            if (mark == null || mark.equals(""))
                continue;
            String[] vlues = mark.split("|");
            if (vlues.length > 1) {
                if (vlues[0] == currentFni.getNodeNumber())
                    continue;
                if (vlues[1] == "true") {
                    for (ViewFlowApproval brogherNode : brogherFNIlist) {
                        if (vlues[0] == brogherNode.getNodeNumber()) {
                            if (brogherNode.getCheckState() < 145) {
                                outAndNodeName = brogherNode.getHandlerName();
                                return brogherNode.getCheckState();
                            }
                        }
                    }
                }
            }
        }
        return checkState;
    }

    /**
     * 得到并联审批条件
     * 
     * @param currentFni
     * @return
     */
    private String getAndNodeArticle(FlowNodeInstence currentFni) {
        String flowmark = currentFni.getOther1();
        int findex = flowmark.indexOf("$");
        String nodemarks = flowmark.substring(0,
                findex == -1 ? flowmark.length() : findex);
        return nodemarks;
    }

    /**
     * 得到并联审批 是否启用并联竞争
     * 
     * @param currentFni
     * @return
     */
    private Boolean getAndNodeIsCompetition(ViewFlowApproval currentFni) {
        String flowmark = currentFni.getOther1();
        int findex = flowmark.indexOf("$");
        String math = findex == -1 ? "" : flowmark.substring(findex + 1);
        if ("true".equals(math.trim())) // 如果为真将未审批的兄弟节点都审批通过
            return true;
        return false;
    }

    /**
     * 获得本节点所有下级节点
     * 
     * @param allflownodeinstence
     * @param currentFni
     * @return
     */
    private List<FlowNodeInstence> getAllNextFNIlist(
            List<ViewFlowApproval> allflownodeinstence,
            ViewFlowApproval currentFni) {
        String[] nodeNombers = currentFni.getNextids().split(",");
        FlowNodeInstence nextFlowNodeinstence;
        List<FlowNodeInstence> nextFNIlist = new ArrayList<FlowNodeInstence>();
        for (String nextid : nodeNombers) {
            for (ViewFlowApproval myflowTodoRow : allflownodeinstence) {
                String nodeNumber = myflowTodoRow.getNodeNumber();
                if (nextid != "" && nextid.equals(nodeNumber)) {
                    nextFlowNodeinstence = new FlowNodeInstence();
                    nextFlowNodeinstence = nodesDStoObject(myflowTodoRow, false);
                    nextFNIlist.add(nextFlowNodeinstence);
                }
            }
        }
        return nextFNIlist;
    }

    private List<ViewFlowApproval> getFatherFNlist(
            List<ViewFlowApproval> allflownodeinstence,
            ViewFlowApproval currentFni) {
        List<String> FatherFN = getFatherNumbers(allflownodeinstence,
                currentFni);
        List<ViewFlowApproval> resultList = new ArrayList<ViewFlowApproval>();
        ViewFlowApproval currentFNI;
        for (String brothernumber : FatherFN) {
            if (brothernumber == "") {
                continue;
            }
            currentFNI = getFNIbyNumber(brothernumber, allflownodeinstence);
            resultList.add(currentFNI);
        }
        return resultList;
    }

    private List<String> getFatherNumbers(
            List<ViewFlowApproval> allflownodeinstence,
            ViewFlowApproval currentFni) {
        String FatherNumber = "";
        List<String> FatherNumbers = new ArrayList<String>();
        boolean hasCurrFN = false;
        for (ViewFlowApproval myflowTodoRow : allflownodeinstence) {
            hasCurrFN = false;
            String nextids = myflowTodoRow.getNextids();
            if (nextids == null || nextids.equals(""))
                continue;

            for (String nextid : nextids.split(",")) {
                if (nextid != "" && currentFni.getNodeNumber().equals(nextid)) {
                    hasCurrFN = true;
                    FatherNumber = myflowTodoRow.getNodeNumber();
                    break;
                }
            }
            if (hasCurrFN)
                FatherNumbers.add(FatherNumber);
        }
        return FatherNumbers;
    }

    /**
     * 逐级回退的情况 替换掉存储过程
     * 
     * @param flowId
     * @param instId
     * @param instIds
     * @param prarentInstIDs
     * @param dealWithIdea
     */
    private void flowUntreadtoPrev(long flowId, long instId, String instIds,
            String prarentInstIDs, String dealWithIdea) throws FlowException {

        String nodesCount = "";
        String nodesMax = "";
        String nodesMin = "";
        String nodesOne = "";

        // 上下节点的最大排序号、上下节点的最小排序号
        String sql = "select count(InstID) nodesCount,max(NodeOrder) nodesMax, MIN(NodeOrder) nodesMin from FlowNodeInstence"
                + " where FlowID='"
                + flowId
                + "' and MakeRubbish = '0' and InstID in ("
                + instIds
                + ","
                + prarentInstIDs + ")";

        Map<String, String> map = (Map<String, String>) dataManager
                .nativeQuery(sql).get(0);

        if (map.size() == 3) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String tempKey = key.toLowerCase();
                String value = String.valueOf(map.get(key));
                if (tempKey.equals("nodesCount".toLowerCase())) {
                    nodesCount = value != null ? value : "0";
                } else if (tempKey.equals("nodesMax".toLowerCase())) {
                    nodesMax = value != null ? value : "0";
                } else if (tempKey.equals("nodesMin".toLowerCase())) {
                    nodesMin = value != null ? value : "0";
                }
            }

            sql = "select nodeOrder from FlowNodeInstence"
                    + " where FlowID='"
                    + flowId
                    + "' and (NodeAttr='-1' or NodeAttr='-3') and MakeRubbish='0'";

            Map<String, Object> nodeOrderMap = (Map<String, Object>) dataManager
                    .nativeQuery(sql).get(0);
            if (nodeOrderMap != null) {
                String value = String.valueOf(nodeOrderMap.get("nodeOrder"
                        .toUpperCase()));
                nodesOne = value != null ? value : "";
            }
            // 设置本节点为否决、日期、处理意见、作废

            dataManager
                    .createNativeQuery(
                            "update FlowNodeInstence set MakeRubbish='1',CheckState='146',HandleDate=sysdate,"
                                    + " DealWithIdea=case when InstID='"
                                    + instId
                                    + "' then '"
                                    + dealWithIdea
                                    + "' else DealWithIdea end where flowId='"
                                    + flowId
                                    + "' and makeRubbish='0' and instId in("
                                    + instIds + ")").executeUpdate();

            // 更新上级和本级节点为作废

            dataManager.createNativeQuery(
                    "update FlowNodeInstence t set t.makeRubbish='1'"
                            + " where FlowID='" + flowId
                            + "' and t.makeRubbish='0' and t.instID in("
                            + instIds + "," + prarentInstIDs + ")")
                    .executeUpdate();

            // 更新上下级节点最大排序号后的所有节点增加排序个数，用于复制上下级节点
            dataManager.createNativeQuery(
                    "update FlowNodeInstence t set t.nodeOrder=t.nodeOrder+"
                            + nodesMax + " where t.flowId='" + flowId
                            + "' and nodeOrder>" + nodesMax).executeUpdate();

            // FlowNodeInstence flowNodeInstence = (FlowNodeInstence)
            // dataManager
            // .findById(FlowNodeInstence.class, Long.valueOf(instIds));
            // String nodeNumber = flowNodeInstence.getNodeNumber();
            //
            // if (nodeNumber.substring(nodeNumber.length() - 2,
            // nodeNumber.length()).equals("-t")) {
            // update FlowNodeInstence set Nextids=(select Nextids from
            // FlowNodeInstence
            // where InstID=@InstIDs) from FlowNodeInstence where
            // InstID=@PrarentInstIDs
            // }

            String where = "FlowID='" + flowId + "' and InstID in ("
                    + prarentInstIDs + ")";
            List<FlowNodeInstence> list = dataManager
                    .createQuery(FlowNodeInstence.class).queryWhere(where)
                    .list();

            for (FlowNodeInstence instence : list) {

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
                        .create();
                String json = gson.toJson(instence);
                FlowNodeInstence tempInstence = gson.fromJson(json,
                        FlowNodeInstence.class);

                tempInstence.setInstId(null);
                if (instence.getNodeAttr() == -1
                        || instence.getNodeAttr() == -3)
                    tempInstence.setNodeAttr(-3);
                tempInstence.setCheckState(142); // 流程节点到了
                tempInstence.setMakeRubbish(false);
                tempInstence.setDealWithIdea("");
                tempInstence.setHandleDate(new Date());
                tempInstence.setNodeOrder(instence.getNodeOrder()
                        + Long.valueOf(nodesMax));
                dataManager.add(tempInstence);
            }

            where = "FlowID='" + flowId + "' and InstID in (" + instIds + ")";
            list = dataManager.createQuery(FlowNodeInstence.class)
                    .queryWhere(where).list();

            for (FlowNodeInstence instence : list) {

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
                        .create();
                String json = gson.toJson(instence);
                FlowNodeInstence tempInstence = gson.fromJson(json,
                        FlowNodeInstence.class);

                tempInstence.setInstId(null);
                if (instence.getNodeAttr() == -1
                        || instence.getNodeAttr() == -3)
                    tempInstence.setNodeAttr(-3);
                tempInstence.setCheckState(141); // 流程节点未到
                tempInstence.setMakeRubbish(false);
                tempInstence.setDealWithIdea("");
                tempInstence.setHandleDate(new Date());
                tempInstence.setNodeOrder(instence.getNodeOrder()
                        + Long.valueOf(nodesMax));
                dataManager.add(tempInstence);
            }

            // FlowNodeInstence flowNodeInstence = (FlowNodeInstence)
            // dataManager
            // .findById(FlowNodeInstence.class, Long.valueOf(instIds));
            //
            // String nodeNumber = flowNodeInstence.getNodeNumber();

            // if (!nodeNumber.substring(nodeNumber.length() - 2,
            // nodeNumber.length()).equals("-t")) {
            // --插入上级数据
            // set @strSQL = 'insert into
            // FlowNodeInstence(NodeID,NodeNumber,NodeAttr,
            // FlowModelId,FlowID,ModuleCode, CheckState,
            // MakeRubbish,DealWithIdea,HandlerName,HandlerId,ModuleID,HandleDate,
            // Nextids,Other1,Other2,TotalFlowId,SenderId,SenderName,SenderDeptId,
            // NodeOrder,FlowRoleName)
            // select a.NodeID,a.NodeNumber,CASE When (a.NodeAttr = -1
            // or a.NodeAttr = -3) then -3 else a.NodeAttr end,
            // a.FlowModelId,a.FlowID,a.ModuleCode, 141,
            // 0,'''',a.HandlerName,a.HandlerId,a.ModuleID,getdate(),
            // a.Nextids,a.Other1,a.Other2,a.TotalFlowId,a.SenderId,a.SenderName,a.SenderDeptId,
            // a.NodeOrder+'+Convert(varchar(10),@NodesMax) + ',
            // a.FlowRoleName
            // from FlowNodeInstence a
            // where a. FlowID=' + Convert(varchar(10),@FlowID) + ' and
            // a.InstID in ('+@InstIDs + ')';
            // }

            FlowInstence flowInstence = (FlowInstence) dataManager.findById(
                    FlowInstence.class, Long.valueOf(flowId));
            if (nodesOne.equals(nodesMin)) {
                flowInstence.setFlowState(138);
            } else {
                flowInstence.setFlowState(136);
            }

            dataManager.update(flowInstence);

        } else {
            throw new FlowException("流程审批错误！");
        }
    }

    private FlowNodeInstence getBeginNodeInstence(
            List<ViewFlowApproval> allFNIDs) {
        FlowNodeInstence beginNodeInstence = new FlowNodeInstence();
        for (ViewFlowApproval beginNodeRow : allFNIDs) {

            if (beginNodeRow.getNodeAttr() == -1) {
                beginNodeInstence = nodesDStoObject(beginNodeRow, false);
                break;
            }
        }
        return beginNodeInstence;
    }

    /**
     * 直接回退的情况 替换掉存储过程
     * 
     * @param flowId
     * @param instId
     * @param instIds
     * @param prarentInstIDs
     * @param dealWithIdea
     */
    private void flowUntreadtoBegin(long flowId, long instId,
            String dealWithIdea) throws FlowException {

        // 删除未处理节点
        dataManager.createNativeQuery(
                "delete FlowNodeInstence where flowID='" + flowId
                        + "' and CheckState in(141)").executeUpdate();

        // 更新状态为否决
        dataManager.createNativeQuery(
                "update FlowNodeInstence set CheckState='146'"
                        + ", HandleDate=sysdate where FlowID='" + flowId
                        + "' and MakeRubbish = '0' and CheckState='142'")
                .executeUpdate();

        dataManager.createNativeQuery(
                "update FlowNodeInstence set DealWithIdea='" + dealWithIdea
                        + "' where instId='" + instId + "'").executeUpdate();

        // 修改所有已处理节点为作废
        dataManager.createNativeQuery(
                "update FlowNodeInstence set MakeRubbish='1'"
                        + " where FlowID='" + flowId
                        + "' and MakeRubbish = '0'").executeUpdate();

        // 查询之前有多少节点
        String sql = "select decode(max(NodeOrder), null, 0, max(NodeOrder)) NodesCount from FlowNodeInstence where FlowID='"
                + flowId + "' and MakeRubbish = '1'";
        Map<String, Object> map = (Map<String, Object>) dataManager
                .nativeQuery(sql).get(0);

        String nodesCount = String.valueOf(map.get("NodesCount".toUpperCase()));

        List<FlowNodeInstenceNew> newList = dataManager
                .createQuery(FlowNodeInstenceNew.class)
                .queryWhere("FlowID='" + flowId + "'").list();

        for (FlowNodeInstenceNew newInstence : newList) {
            FlowNodeInstence instence = transformObject(newInstence);
            if (instence.getMakeRubbish() == false) { // 更新开始节点到发起者
                instence.setCheckState(141);
                if (instence.getNodeNumber().equals("BeginNode")) {
                    instence.setNodeAttr(-3);
                    instence.setCheckState(142);
                    instence.setHandleDate(new Date());
                }
            }

            instence.setInstId(null);
            instence.setHandleDate(new Date());

            double nodeOrder = Double.valueOf(nodesCount);
            instence.setNodeOrder(instence.getNodeOrder() + (int) nodeOrder);

            dataManager.add(instence);
        }

        dataManager.createNativeQuery(
                "update FlowInstence set FlowState='138' where flowId='"
                        + flowId + "'").executeUpdate();
    }

    /**
     * 转换vo
     * 
     * @param list
     * @return
     */
    private FlowNodeInstence transformObject(
            FlowNodeInstenceNew flowNodeInstenceNew) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        String json = gson.toJson(flowNodeInstenceNew);
        FlowNodeInstence flowNodeInstence = gson.fromJson(json,
                FlowNodeInstence.class);
        return flowNodeInstence;
    }
}
