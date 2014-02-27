package com.sccl.framework.service.org;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.entity.Organization;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.vo.OrgTree;

@Component
public class OrgService implements IOrgService {
	
	private DataManager dataManager;
	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	@Transactional
	@Log
	public String addOrg(String orgJson) {
		try {
			Organization organization = dataManager.add(new Gson().fromJson(orgJson, Organization.class));
			return new Gson().toJson(organization);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String addOrgs(String orgsJson) {
		try {
			Type orgType = new TypeToken<List<Organization>>(){}.getType();
			List<Organization> organizations = new Gson().fromJson(orgsJson, orgType);
			Iterator<Organization> iterator = organizations.iterator();
			while (iterator.hasNext()) {
				Organization organization = iterator.next();
				dataManager.add(organization);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String updateOrg(String orgJson) {
		try {
			dataManager.update(new Gson().fromJson(orgJson, Organization.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String updateOrgs(String orgsJson) {
		try {
			Type orgType = new TypeToken<List<Organization>>(){}.getType();
			List<Organization> organizations = new Gson().fromJson(orgsJson, orgType);
			Iterator<Organization> iterator = organizations.iterator();
			while (iterator.hasNext()) {
				Organization organization = iterator.next();
				dataManager.update(organization);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String deleteOrgById(String orgId) {
		try {
			dataManager.deleteById(Organization.class, Integer.parseInt(orgId));
			List<Organization> organizations = dataManager.findAll(Organization.class);
			Iterator<Organization> iterator = organizations.iterator();
			while (iterator.hasNext()) {
				Organization organization =  iterator.next();
				if(organization.getParentId().intValue() == Integer.parseInt(orgId)) {
					dataManager.deleteById(Organization.class, organization.getId());
					deleteChildren(organizations, organization.getId());
				}
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	private void deleteChildren(List<Organization> organizations, Integer orgId) {
		Iterator<Organization> iterator = organizations.iterator();
		while (iterator.hasNext()) {
			Organization organization =  iterator.next();
			if(organization.getParentId().intValue() == orgId.intValue()) {
				dataManager.deleteById(Organization.class, organization.getId());
				deleteChildren(organizations, organization.getId());
			}
		}
	}

	@Override
	@Transactional
	@Log
	public String deleteOrgByIds(String orgIds) {
		try {
			Integer[] ids = new Gson().fromJson(orgIds, Integer[].class);
			dataManager.deleteByIdBatch(Organization.class, ids);
			List<Organization> organizations = dataManager.findAll(Organization.class);
			Iterator<Organization> iterator = organizations.iterator();
			for(int i=0; i<ids.length; i++) {
				while (iterator.hasNext()) {
					int orgId = ids[i].intValue();
					Organization organization =  iterator.next();
					if(organization.getParentId().intValue() == orgId) {
						dataManager.deleteById(Organization.class, organization.getId());
						deleteChildren(organizations, organization.getId());
					}
				}
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Log
	public String findAllOrg() {
		try {
			return new Gson().toJson(dataManager.findAll(Organization.class));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Log
	public String findAllOrgPage(int first, int max) {
		try {
			List<Organization> organizations = dataManager.createNamedQuery("findOrgOrdered", Organization.class).setFirstResult(first).setMaxResults(max).getResultList();
			return new Gson().toJson(organizations);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Log
	public String findAllOrgTree() {
		try {
			List<OrgTree> orgTrees = getOrgTreeByOrgId(0);
			if(orgTrees != null) {
				return new Gson().toJson(orgTrees);
			} else {
				return "null";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	private List<OrgTree> getOrgTreeByOrgId(int orgId) {
		try {
			List<OrgTree> orgTrees = new ArrayList<OrgTree>();
			List<Organization> organizations = dataManager.createQuery("select o from Organization o order by o.id",Organization.class).getResultList();
			Iterator<Organization> iterator = organizations.iterator();
			List<OrgTree>  rootTree = new ArrayList<OrgTree>();
			OrgTree root = new OrgTree();
			while (iterator.hasNext()) {
				Organization organization = iterator.next();
				if(organization.getParentId().intValue() == orgId) {
					OrgTree orgTree = StaticMethods.toOrgTree(organization);
					
					List<OrgTree> orgTrees2 = getChildrenOrgs(organizations, orgTree.getId());
					if(orgTrees2 != null && orgTrees2.size() != 0) {
						orgTree.setChildren(orgTrees2);
					}
					orgTrees.add(orgTree);
				}
				
				if(organization.getId().intValue() == orgId) {
					root = StaticMethods.toOrgTree(organization);
				}
			}
			
			root.setChildren(orgTrees);
			rootTree.add(root);
			return rootTree;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<OrgTree> getChildrenOrgs(List<Organization> organizations, int parentId) {
		List <OrgTree> orgTrees = new ArrayList<OrgTree>();
		Iterator<Organization> iter = organizations.iterator();
		while(iter.hasNext()) {
			Organization organization = iter.next();
			if(organization.getParentId() == parentId) {
				OrgTree orgTree = StaticMethods.toOrgTree(organization);
				List<OrgTree> orgTrees2 = getChildrenOrgs(organizations, orgTree.getId());
				if(orgTrees2 != null && orgTrees2.size() != 0) {
					orgTree.setChildren(orgTrees2);
				}
				orgTrees.add(orgTree);
			}
		}
		return orgTrees;
	}

	@Override
	@Log
	public String findOrgTreeByOrgId(String orgId) {
		try {
			List<OrgTree> orgTrees = getOrgTreeByOrgId(Integer.parseInt(orgId));
			if(orgTrees != null) {
				return new Gson().toJson(orgTrees);
			} else {
				return "null";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	

	@Override
	@Log
	public String findAllCompany() {
		try {
			List<Organization> organizations = dataManager.createNamedQuery("Organization.findAllCompany", Organization.class).getResultList();
			return new Gson().toJson(organizations);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
