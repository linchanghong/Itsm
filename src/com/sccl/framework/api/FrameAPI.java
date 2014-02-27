package com.sccl.framework.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sccl.framework.entity.MsRole;
import com.sccl.framework.entity.MsUser;
import com.sccl.framework.service.attachment.IAttachmentService;
import com.sccl.framework.service.constant.IConstService;
import com.sccl.framework.service.log.ILogService;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.service.login.IUserLoginServive;
import com.sccl.framework.service.notice.INoticeService;
import com.sccl.framework.service.org.IOrgService;
import com.sccl.framework.service.person.IPersonService;
import com.sccl.framework.service.roleRight.IRoleRightService;
import com.sccl.framework.service.set.ISetService;
import com.sccl.framework.service.user.IUserService;
import com.sun.jersey.api.core.InjectParam;

import flex.messaging.FlexContext;

@SuppressWarnings("all")
@RemotingDestination
@Component("frameAPI")
@Path(value = "/frame")
public class FrameAPI {
	
	@InjectParam private IRoleRightService roleRightService;
	@InjectParam private IUserLoginServive userLoginServive;
	@InjectParam private IConstService constService;
	@InjectParam private IUserService userService;
	@InjectParam private ILogService logService;
	@InjectParam private IOrgService orgService;
	@InjectParam private IPersonService personService;
	@InjectParam private ISetService setService;
	@InjectParam private IAttachmentService attachmentService;
	@InjectParam private INoticeService noticeService;
	
	@Transactional
	@POST
	@Path(value = "/menu")
	public String addMenuButton(String menuJson) {
		return roleRightService.addMenuButton(menuJson);
	}
	
	@Transactional
	@POST
	@Path(value = "/menus")
	public String addMenuButtons(String menusJson) {
		return roleRightService.addMenuButtons(menusJson);
	}
	
	@Transactional
	@PUT 
	@Path(value = "/menu")
	public String updateMenuButton(String menuJson) {
		return roleRightService.updateMenuButton(menuJson);
	}
	
	@Transactional
	@PUT
	@Path(value = "/menus")
	public String updateMenuButtons(String menusJson) {
		return roleRightService.updateMenuButtons(menusJson);
	}

	@DELETE
	@Transactional
	@Path(value = "/menu/id/{menuId}")
	public String deleteMenuById(@PathParam(value = "menuId") String menuId) {
		return roleRightService.deleteMenuById(menuId);
	}

	@DELETE
	@Transactional
	@Path(value = "/menus/ids/{menuIds}")
	public String deleteMenuByIds(@PathParam(value = "menuIds") String menuIds) {
		return roleRightService.deleteMenuByIds(menuIds);
	}
	
	@GET
	@Path(value = "/menu/id/{menuId}")
	@Produces(value = {"application/json"})
	public String findMenuById(@PathParam(value = "menuId") String menuId) {
		return roleRightService.findMenuById(menuId);
	}
	
	@GET
	@Path(value = "/menus/role/{roleId}")
	@Produces(value = {"application/json"})
	public String findMenuByRoleId(@PathParam(value = "roleId") String roleId) {
		return roleRightService.findMenuByRoleId(roleId);
	}
	
	@GET
	@Path(value = "/menus")
	@Produces(value = {"application/json"})
	public String findAllMenuButton() {
		return roleRightService.findAllMenuButton();
	}
	
	@GET
	@Path(value = "/menus/tree")
	@Produces(value = {"application/json"})
	public String findAllMenuButtonTree() {
		return roleRightService.findAllMenuButtonTree("");
	}
	
	@Transactional
	@POST
	@Path(value = "/role")
	public String addRole(String roleJson) {
		return roleRightService.addRole(roleJson);
	}
	
	@Transactional
	@POST
	@Path(value = "/roles")
	public String addRoles(String rolesJson) {
		return roleRightService.addRoles(rolesJson);
	}
	
	@Transactional
	@PUT
	@Path(value = "/role")
	public String updateRole(String roleJson) {
		return roleRightService.updateRole(roleJson);
	}
	
	@Transactional
	@PUT
	@Path(value = "/roles")
	public String updateRoles(String rolesJson) {
		return roleRightService.updateRoles(rolesJson);
	}
	
	@Transactional
	@DELETE
	@Path(value = "/role/id/{roleId}")
	public String deleteRoleById(@PathParam(value = "roleId") String roleId) {
		return roleRightService.deleteRoleById(roleId);
	}
	
	@Transactional
	@DELETE
	@Path(value = "/roles/ids/{roleIds}")
	public String deleteRoleByIds(@PathParam(value = "roleIds") String roleIds) {
		return roleRightService.deleteRoleByIds(roleIds);
	}
	
	@GET
	@Path(value = "/role/name/{roleName}")
	@Produces(value = {"application/json"})
	public MsRole findByRoleName(@PathParam(value = "roleName") String roleName) {
		return roleRightService.findByRoleName(roleName);
	}

	@GET
	@Path(value = "/roles")
	@Produces(value = {"application/json"})
	public String findAllRoles() {
		return roleRightService.findAllRoles();
	}

	@GET
	@Path(value = "/roles/tree/{roleId}")
	@Produces(value = {"application/json"})
	public String findAllRoleTree(@PathParam(value = "roleId") int roleId) {
		return roleRightService.findAllRoleTree(roleId);
	}
	
	@Transactional
	@POST
	@Path(value = "/user")
	public String addUser(String userJson) {
		return userService.addUser(userJson);
	}
	
	@Transactional
	@POST
	@Path(value = "/users")
	public String addUsers(String usersJson) {
		return userService.addUsers(usersJson);
	}

	@Transactional
	@PUT
	@Path(value = "/user")
	public String updateUser(String userJson) {
		return userService.updateUser(userJson);
	}

	@Transactional
	@PUT
	@Path(value = "/users")
	public String updateUsers(String usersJson) {
		return userService.updateUsers(usersJson);
	}

	@Transactional
	@DELETE
	@Path(value = "/user/id/{userId}")
	public String deleteUserById(@PathParam(value = "userId") String userId) {
		return userService.deleteUserById(userId);
	}

	@Transactional
	@DELETE
	@Path(value = "/users/ids/{userIds}")
	public String deleteUserByIds(@PathParam(value = "userIds") String userIds) {
		return userService.deleteUserByIds(userIds);
	}
	
	@GET
	@Path(value = "/users")
	public String findAllUser(@PathParam(value = "userInfoCondtion") String userInfoCondtion, @PathParam(value = "first") int first,
			@PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount) {
		return userService.findAllUser(userInfoCondtion,first, size, isCount);
	}

	@GET
	@Path(value = "/user/id/{userId}")
	public String findUserById(@PathParam(value = "userId") String userId) {
		return userService.findUserById(userId);
	}

	@GET
	@Path(value = "/user/name/{userName}")
	public String findUserByName(@PathParam(value = "userName") String userName) {
		return userService.findUserByName(userName);
	}

	@GET
	@Path(value = "/users/org/{orgId}")
	public String findUserByOrgId(@PathParam(value = "orgId") String orgId) {
		return userService.findUserByOrgId(orgId);
	}
	
	
	@GET
	@Path(value = "/users/orgid/{orgId}/first/{first}/size/{size}/isCount/{isCount}")
	public String findAllUserByOrgIdPage(@PathParam(value = "orgId") String orgId, 
			@PathParam(value = "first") int first, @PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount) {
		return userService.findAllUserByOrgIdPage(orgId, first, size, isCount);
	}

	@POST
	@Transactional
	@Path(value = "/const/type")
	public String addConstType(String constTypeJson) {
		return constService.addConstType(constTypeJson);
	}

	@POST
	@Transactional
	@Path(value = "/const/detail")
	public String addConstDetail(String constDetailJson) {
		return constService.addConstDetail(constDetailJson);
	}

	@POST
	@Transactional
	@Path(value = "/const/details")
	public String addConstDetails(String constDetailsJson) {
		return constService.addConstDetails(constDetailsJson);
	}

	@PUT
	@Transactional
	@Path(value = "/const/type")
	public String updateConstType(String constTypeJson) {
		return constService.updateConstType(constTypeJson);
	}

	@PUT
	@Transactional
	@Path(value = "/const/types")
	public String updateConstTypes(String constTypesJson) {
		return constService.updateConstTypes(constTypesJson);
	}

	@PUT
	@Transactional
	@Path(value = "/const/detail")
	public String updateConstDetail(String constDetailJson) {
		return constService.updateConstDetail(constDetailJson);
	}

	@PUT
	@Transactional
	@Path(value = "/const/details")
	public String updateConstDetails(String constDetailsJson) {
		return constService.updateConstDetails(constDetailsJson);
	}
	
	@DELETE
	@Transactional
	@Path(value = "/const/detail/id/{detailId}")
	public String deleteConstDetailById(@PathParam(value = "detailId") String constDetailId) {
		return constService.deleteConstDetailById(constDetailId);
	}

	@DELETE
	@Transactional
	@Path(value = "/const/details/ids/{detailIds}")
	public String deleteConstDetailByIds(@PathParam(value = "detailIds") String constDetailIds) {
		return constService.deleteConstDetailByIds(constDetailIds);
	}

	@DELETE
	@Transactional
	@Path(value = "/const/type/id/{constTypeId}")
	public String deleteConstByTypeId(@PathParam(value = "constTypeId") String constTypeId) {
		return constService.deleteConstByTypeId(constTypeId);
	}
	
	@GET
	@Path(value = "/const/tree")
	public String findAllConstTree() {
		return constService.findAllConstTree();
	}
	
	@GET
	@Path(value = "/logs")
	public String findAllLog() {
		return logService.findAllLog();
	}

	@GET
	@Path(value = "/logs/page/{first}/{max}/{isCount}")
	public String findAllLogPage(@PathParam(value = "first") int first,@PathParam(value = "max") int max, 
			@PathParam(value = "isCount") boolean isCount) {
		return logService.findAllLogPage(first, max, isCount);
	}

	@GET
	@Path(value = "/logs/usercode/{userCode}")
	public String findLogByUserCode(@PathParam(value = "userCode") String userCode) {
		return logService.findLogByUserCode(userCode);
	}

	@GET
	@Path(value = "/logs/userCode/page/{userCode}/{first}/{max}/{isCount}")
	public String findLogByUserCodePage(@PathParam(value = "userCode") String userCode, @PathParam(value = "first") int first, 
			@PathParam(value = "max") int max, @PathParam(value = "isCount") boolean isCount) {
		return logService.findLogByUserCodePage(userCode, first, max, isCount);
	}
	
	@GET
	@Path(value = "/logs/conditions/page/{conditions}/{first}/{max}/{isCount}")
	public String findAllLogPageByCondition(@PathParam(value = "conditions") String conditions, @PathParam(value = "first") int first, 
			@PathParam(value = "max") int max, @PathParam(value = "isCount") boolean isCount) {
		return logService.findAllLogPageByCondition(conditions, first, max, isCount);
	}

	@POST
	@Transactional
	@Path(value = "/org")
	public String addOrg(String orgJson) {
		return orgService.addOrg(orgJson);
	}

	@POST
	@Transactional
	@Path(value = "/orgs")
	public String addOrgs(String orgsJson) {
		return orgService.addOrgs(orgsJson);
	}

	@PUT
	@Transactional
	@Path(value = "/org")
	public String updateOrg(String orgJson) {
		return orgService.updateOrg(orgJson);
	}

	@PUT
	@Transactional
	@Path(value = "/orgs")
	public String updateOrgs(String orgsJson) {
		return orgService.updateOrgs(orgsJson);
	}

	@DELETE
	@Transactional
	@Path(value = "/org/id/{orgId}")
	public String deleteOrgById(@PathParam(value = "orgId") String orgId) {
		return orgService.deleteOrgById(orgId);
	}

	@DELETE
	@Transactional
	@Path(value = "/orgs/ids/{orgIds}")
	public String deleteOrgByIds(@PathParam(value = "orgIds") String orgIds) {
		return orgService.deleteOrgByIds(orgIds);
	}

	@GET
	@Path(value = "/orgs")
	public String findAllOrg() {
		return orgService.findAllOrg();
	}

	@GET
	@Path(value = "/orgs/page/{first}/{max}")
	public String findAllOrgPage(@PathParam(value = "first") int first, @PathParam(value = "max") int max) {
		return orgService.findAllOrgPage(first, max);
	}

	@GET
	@Path(value = "/orgs/tree")
	public String findAllOrgTree() {
		return orgService.findAllOrgTree();
	}

	@GET
	@Path(value = "/orgs/tree/{orgId}")
	public String findOrgTreeByOrgId(@PathParam(value = "orgId") String orgId) {
		return orgService.findOrgTreeByOrgId(orgId);
	}

	@GET
	@Path(value = "/orgs/company")
	public String findAllCompany() {
		return orgService.findAllCompany();
	}

	@POST
	@Transactional
	@Path(value = "/person")
	public String addPerson(String personJson) {
		return personService.addPerson(personJson);
	}

	@POST
	@Transactional
	@Path(value = "/persons")
	public String addPersons(String personsJson) {
		return personService.addPersons(personsJson);
	}

	@PUT
	@Transactional
	@Path(value = "/person")
	public String updatePerson(String personJson) {
		return personService.updatePerson(personJson);
	}
	

	@PUT
	@Transactional
	@Path(value = "/persons")
	public String updatePersons(String personsJson) {
		return personService.updatePersons(personsJson);
	}

	@DELETE
	@Transactional
	@Path(value = "/person/id/{personId}")
	public String deletePersonById(@PathParam(value = "personId") String personId) {
		return personService.deletePersonById(personId);
	}

	@DELETE
	@Transactional
	@Path(value = "/persons/ids/{personIds}")
	public String deletePersonByIds(@PathParam(value = "personIds") String personIds) {
		return personService.deletePersonByIds(personIds);
	}
	
	@GET
	@Path(value = "/persons")
	public String findAllperson() {
		return personService.findAllperson();
	}

	@GET
	@Path(value = "/persons/first/{first}/size/{size}/isCount/{isCount}")
	public String findAllpersonPage(@PathParam(value = "first") int first, @PathParam(value = "size") int size, 
			@PathParam(value = "isCount") boolean isCount) {
		return personService.findAllpersonPage(first, size, isCount);
	}
	
	@GET
	@Path(value = "/persons/orgid/{orgId}")
	public String findAllpersonByOrgId(@PathParam(value = "orgId") String orgId) {
		return personService.findAllpersonByOrgId(orgId);
	}
	
	@GET
	@Path(value = "/persons/orgid/{orgId}/first/{first}/size/{size}/isCount/{isCount}")
	public String findAllpersonByOrgIdPage(@PathParam(value = "orgId") String orgId, 
			@PathParam(value = "first") int first, @PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount) {
		return personService.findAllpersonByOrgIdPage(orgId, first, size, isCount);
	}
	
	@GET
	@Path(value = "/persons/condition/{condition}/{orgId}/{first}/{size}/{isCount}")
	public String findAllpersonByOrgIdPageCondition(@PathParam(value = "orgId") String orgId, @PathParam(value = "condition") String condition, 
			@PathParam(value = "first") int first, @PathParam(value = "size") int size, @PathParam(value = "isCount") boolean isCount) {
		return personService.findAllpersonByOrgIdPageCondition(orgId, condition, first, size, isCount);
	}
	
	@POST
	@Transactional
	@Path(value = "/settype")
	public String addSetType(String setTypeJson) {
		return setService.addSetType(setTypeJson);
	}
	
	@PUT
	@Transactional
	@Path(value = "/settype")
	public String updateSetType(String setTypeJson) {
		return setService.updateSetType(setTypeJson);
	}

	@DELETE
	@Transactional
	@Path(value = "/settype/id/{id}")
	public String deleteSetTypeById(@PathParam(value = "id") String id) {
		return setService.deleteSetTypeById(id);
	}

	@GET
	@Path(value = "/settypes")
	public String findAllSetTypes() {
		return setService.findAllSetTypes();
	}

	@POST
	@Transactional
	@Path(value = "/setdetail")
	public String addSetDetail(String setDetailJson) {
		return setService.addSetDetail(setDetailJson);
	}

	@PUT
	@Transactional
	@Path(value = "/setdetail")
	public String updateSetDetail(String setDetailJson) {
		return setService.updateSetDetail(setDetailJson);
	}

	@DELETE
	@Transactional
	@Path(value = "/setdetail/id/{id}")
	public String deleteSetDetailById(String id) {
		return setService.deleteSetDetailById(id);
	}

	@GET
	@Path(value = "/setdetails/typeid/{id}")
	public String findSetDetailsByTypeId(@PathParam(value = "id") String id) {
		return setService.findSetDetailsByTypeId(id);
	}

	@GET
	@Path(value = "/set/tree")
	public String findAllSetTree() {
		return setService.findAllSetTree();
	}

	@GET
	@Path(value = "/constset/tree")
	public String findAllConstSetTree() {
		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("constTree", findAllConstTree());
		reMap.put("setTree", findAllSetTree());
		return new Gson().toJson(reMap);
	}
	

	@POST
	@Transactional
	@Path(value = "/columns")
	public String addCustomcolumns(String columnsJson) {
		return setService.addCustomcolumns(columnsJson);
	}

	@GET
	@Path(value = "/columns/{moduleName}/{userId}")
	public String findCustomcolumns(@PathParam(value = "moduleName") String moduleName, @PathParam(value = "userId") Integer userId) {
		return setService.findCustomcolumns(moduleName, userId);
	}
	
	@POST
	@Transactional
	@Path(value = "/attachment")
	public String addAttachment(String attachmentJson) {
		return attachmentService.addAttachment(attachmentJson);
	}

	@POST
	@Transactional
	@Path(value = "/attachments")
	public String addAttachments(String attachmentJsons) {
		return attachmentService.addAttachments(attachmentJsons);
	}

	@PUT
	@Transactional
	@Path(value = "/attachment")
	public String updateAttachment(String attachmentJson) {
		return attachmentService.updateAttachment(attachmentJson);
	}


	@PUT
	@Transactional
	@Path(value = "/attachments")
	public String updateAttachments(String attachmentJsons) {
		return attachmentService.updateAttachments(attachmentJsons);
	}

	@DELETE
	@Transactional
	@Path(value = "/attachment/id/{id}")
	public String deleteAttachmentById(@PathParam(value = "id") String id) {
		return attachmentService.deleteAttachmentById(id);
	}

	@DELETE
	@Transactional
	@Path(value = "/attachments/ids/{ids}")
	public String deleteAttachmentByIds(@PathParam(value = "ids") String ids) {
		return attachmentService.deleteAttachmentByIds(ids);
	}
	
	@GET
	@Path(value = "/attachments/{busTableName}/{busDataId}")
	public String findRelateAttachments(@PathParam(value = "busTableName") String busTableName, @PathParam(value = "busDataId") String busDataId) {
		return attachmentService.findRelateAttachments(busTableName, busDataId);
	}
	
	
	@POST
	@Transactional
	@Path(value = "/login")
	public String login(String loginJson) {
		return roleRightService.login(loginJson);
	}
	
	public HashMap<String, Object> userLogin(String usJson) {
		try {
			MsUser us = new Gson().fromJson(usJson, MsUser.class);
			FlexContext.getFlexSession().setAttribute("userCode", us.getUserCode());
			return userLoginServive.userLogin(us);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String autoLogin(String ltpaToken, String ltpa3DESKey, String ltpaPassword) {
		return userLoginServive.autoLogin(ltpaToken, ltpa3DESKey, ltpaPassword);
	}
	
	public MsUser finduser(String userCode) {
		return userLoginServive.finduser(userCode);
	}
	
	public MsUser isLogin(String userCode, String userBehave, String parameters) {
		return userLoginServive.isLogin(userCode, userBehave, parameters);
	}

	@Transactional
	public void changPassWord(Integer id, String password) {
		userService.changPassWord(id, password);
	}
	
	public String exportByData (List dataArr, String[][] headerArray, String templateFileName){
		return constService.exportByData (dataArr, headerArray, templateFileName);
	}

	public String exportByMethod (String methodObj, String[][] headerArray, String templateFileName) {
		return constService.exportByMethod (methodObj, headerArray, templateFileName);
	}
	
	/**
	 * 查询所有公告
	 * @return
	 */
	@Log
	public String findAllNotice(int first, int size, boolean isCount){
		return noticeService.findAllNotice(first, size, isCount);
	}
	
	/**
	 * 新增公告
	 * @param json
	 * @return
	 */
	@Transactional
	@Log
	public String addNotice(String json){
		return noticeService.addNotice(json);
	}
	
	/**
	 * 更新公告
	 * @param json
	 * @return
	 */
	@Transactional
	@Log
	public String updateNotice(String json){
		return noticeService.updateNotice(json);
	}
	
	/**
	 * 删除公告
	 * @param json
	 * @return
	 */
	@Transactional
	@Log
	public String deleteNotice(String json){
		return noticeService.deleteNotice(json);
	}
	
	/**
	 * 公告显示
	 * @return
	 */
	@Log
	public String findAllNotice(){
		return noticeService.findAllNotice();
	}
	
	/**
	 * 读取通知
	 * @return
	 */
	public String readContent() {
		String path="";
		try {
			path = this.getClass().getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String fileName = "\\notice.txt";
		String content = "";
		File file = new File(path+fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content+=tempString+"\n";
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
        
        return content;

	}
	
	/**
	 * 写通知
	 * @return
	 */
	public String writerContent(String txt) {
		boolean result = false;
		String path="";
		try {
			path = this.getClass().getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String fileName = "\\notice.txt";
		String content = "";
		File file = new File(path+fileName);
		
		BufferedWriter bw = null;
	    try {
	    	if(!file.exists())	file.createNewFile();
	    	
	    	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
	    	bw.write(txt);
	    	bw.close();
	      result=true;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    return String.valueOf(result);
	}
	
	@Log
	public String findAllUser(int first, int size, boolean isCount){
		return userService.findAllUser(first, size, isCount);
	}
}
