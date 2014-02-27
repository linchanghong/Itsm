package com.sccl.framework.service.org;

public interface IOrgService {
	
	public String addOrg(String orgJson);
	public String addOrgs(String orgsJson);
	public String updateOrg(String orgJson);
	public String updateOrgs(String orgsJson);
	public String deleteOrgById(String orgId);
	public String deleteOrgByIds(String orgIds);
	public String findAllOrg();
	public String findAllOrgPage(int first, int max);
	public String findAllOrgTree();
	public String findOrgTreeByOrgId(String orgId);
	public String findAllCompany();

}
