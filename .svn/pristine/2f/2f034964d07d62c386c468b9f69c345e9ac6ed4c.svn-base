package com.sccl.framework.service.person;

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
import com.sccl.framework.entity.MsPerson;
import com.sccl.framework.service.log.Log;

@Component
public class PersonService implements IPersonService {
	
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
	public String addPerson(String personJson) {
		try {
			dataManager.add(new Gson().fromJson(personJson, MsPerson.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String addPersons(String personsJson) {
		try {
			Type personType = new TypeToken<List<MsPerson>>(){}.getType();
			List<MsPerson> msPersons = new Gson().fromJson(personsJson, personType);
			Iterator<MsPerson> iterator = msPersons.iterator();
			while (iterator.hasNext()) {
				MsPerson msPerson = iterator.next();
				dataManager.add(msPerson);
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
	public String updatePerson(String personJson) {
		try {
			dataManager.update(new Gson().fromJson(personJson, MsPerson.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String updatePersons(String personsJson) {
		try {
			Type personType = new TypeToken<List<MsPerson>>(){}.getType();
			List<MsPerson> msPersons = new Gson().fromJson(personsJson, personType);
			Iterator<MsPerson> iterator = msPersons.iterator();
			while (iterator.hasNext()) {
				MsPerson msPerson = iterator.next();
				dataManager.update(msPerson);
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
	public String deletePersonById(String personId) {
		try {
			dataManager.deleteById(MsPerson.class, Integer.parseInt(personId));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String deletePersonByIds(String personIds) {
		try {
			Integer[] ids = new Gson().fromJson(personIds, Integer[].class);
			dataManager.deleteByIdBatch(MsPerson.class, ids);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public String findAllperson() {
		try {
			return new Gson().toJson(dataManager.findAll(MsPerson.class));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	@Log
	public String findAllpersonPage(int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsPerson> msPersons = dataManager.createQuery("select p from MsPerson p order by p.id, p.organization.id", MsPerson.class).setFirstResult(first).setMaxResults(size).getResultList();
//			String mpsString = StaticMethods.getExposeGson().toJson(msPersons);
            reList.add(0, msPersons);
			if(isCount) {
				Long count = dataManager.createQuery("select count(p) from MsPerson p ", Long.class).getSingleResult();
				reList.add(1, count);
			}
			return StaticMethods.getExposeGson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllpersonByOrgId(String orgId) {
		try {
			List<MsPerson> msPersons = dataManager.createNamedQuery("MsPerson.findByOrgId", MsPerson.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").getResultList();
			return new Gson().toJson(msPersons);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllpersonByOrgIdPage(String orgId, int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsPerson> msPersons = dataManager.createNamedQuery("MsPerson.findByOrgId", MsPerson.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").setFirstResult(first).setMaxResults(size).getResultList();
			reList.add(0, msPersons);
			if(isCount) {
				Long count = dataManager.createNamedQuery("MsPerson.findByOrgIdCount", Long.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").getSingleResult();
				reList.add(1, count);
			}
			return new Gson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public String findAllpersonByOrgIdPageCondition(String orgId, String condition, int first, int size, boolean isCount) {
		try {
			List<Object> reList = new ArrayList<Object>();
			List<MsPerson> msPersons = dataManager.createQuery("SELECT p FROM MsPerson p WHERE (p.organization.id=:orgId or p.organization.parentPath like :orgIdLike) and (p.personName like '%"+ condition +"%' or p.personCode like '%"+ condition +"%') order by p.id, p.organization.id", MsPerson.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").setFirstResult(first).setMaxResults(size).getResultList();
			reList.add(0, msPersons);
			if(isCount) {
				Long count = dataManager.createQuery("SELECT count(p) FROM MsPerson p WHERE (p.organization.id=:orgId or p.organization.parentPath like :orgIdLike) and (p.personName like '%"+ condition +"%' or p.personCode like '%"+ condition +"%') order by p.id, p.organization.id", Long.class).setParameter("orgId", Integer.parseInt(orgId)).setParameter("orgIdLike", orgId + ",%").getSingleResult();
				reList.add(1, count);
			}
			return new Gson().toJson(reList);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
