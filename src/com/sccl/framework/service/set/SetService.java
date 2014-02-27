package com.sccl.framework.service.set;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sccl.framework.DataManager;
import com.sccl.framework.common.tools.StaticMethods;
import com.sccl.framework.entity.Customcolumns;
import com.sccl.framework.entity.SetDetail;
import com.sccl.framework.entity.SetType;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.vo.SetTree;

@Component
public class SetService implements ISetService {

	private DataManager dataManager;

	public DataManager getDataManager() {
		return dataManager;
	}

	@Resource
	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Log
	@Transactional
	public String addSetType(String setTypeJson) {
		try {
			Gson gson = new Gson(); 
			SetType setType = dataManager.add(gson.fromJson(setTypeJson, SetType.class));
			return gson.toJson(setType);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateSetType(String setTypeJson) {
		try {
			dataManager.update(new Gson().fromJson(setTypeJson, SetType.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String deleteSetTypeById(String id) {
		try {
			dataManager.deleteById(SetType.class, Integer.parseInt(id));
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	@Log
	public String findAllSetTypes() {
		try {
			return new Gson().toJson(dataManager.findAll(SetType.class));
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String addSetDetail(String setDetailJson) {
		try {
			Gson gson = new Gson(); 
			SetDetail setDetail = dataManager.add(gson.fromJson(setDetailJson, SetDetail.class));
			return gson.toJson(setDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String updateSetDetail(String setDetailJson) {
		try {
			dataManager.update(new Gson().fromJson(setDetailJson, SetDetail.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	@Transactional
	public String deleteSetDetailById(String id) {
		try {
			dataManager.deleteById(SetDetail.class, Integer.parseInt(id));
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "0";
		}
		return "1";
	}

	@Log
	public String findSetDetailsByTypeId(String id) {
		try {
			List<SetDetail> setDetails = dataManager.createNamedQuery("SetDetail.findAllByTypeId", SetDetail.class).setParameter("typeId", Integer.parseInt(id)).getResultList();
			return new Gson().toJson(setDetails);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Log
	public List<SetType> findAllSetType(){
		return dataManager.findAll(SetType.class);
	}
	
	@Log
	public List<SetDetail> findAllSetDetail(){
		return dataManager.findAll(SetDetail.class);
	}

	public String findAllSetTree() {
		try {
			List<SetType> setTypes = findAllSetType();
			List<SetDetail> setDetails = findAllSetDetail();
			List<SetTree> setTrees = new ArrayList<SetTree>();
			Iterator<SetType> iter = setTypes.iterator();
			while (iter.hasNext()) {
				SetType setType = iter.next();
				SetTree setTree = StaticMethods.toSetTree(setType);
				List<SetDetail> children = new ArrayList<SetDetail>();
				Iterator<SetDetail> iter1 = setDetails.iterator();
				while (iter1.hasNext()) {
					SetDetail setDetail = iter1.next();
					if(setDetail.getSetType().getId().equals(setType.getId())) {
						children.add(setDetail);
					}
				}
				setTree.setChildren(children);
				setTrees.add(setTree);
			}
			return new Gson().toJson(setTrees);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Transactional
	public String addCustomcolumns(String columnsJson) {
		try {
			Gson gson = new Gson(); 
			Customcolumns customcolumn = gson.fromJson(columnsJson, Customcolumns.class);
			if(customcolumn.getId() != null && customcolumn.getId().toString() != "") {
				dataManager.update(customcolumn);
				return "1";
			} else {
				Customcolumns customcolumns = dataManager.add(customcolumn);
				return gson.toJson(customcolumns);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	public String findCustomcolumns(String moduleName, Integer userId) {
		try {
			List<Customcolumns> customColumns = dataManager.createQuery("select c from Customcolumns c where c.moduleName = :moduleName and c.userId = :userId order by c.id desc", Customcolumns.class).setParameter("moduleName", moduleName).setParameter("userId", userId).getResultList();
			return new Gson().toJson(customColumns);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
