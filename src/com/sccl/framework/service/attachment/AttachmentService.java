package com.sccl.framework.service.attachment;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sccl.framework.DataManager;
import com.sccl.framework.entity.Attachment;
import com.sccl.framework.service.log.Log;
import com.sccl.framework.utils.Tools;

@Component
public class AttachmentService implements IAttachmentService {
	
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
	public String addAttachment(String attachmentJson) {
		try {
			Attachment attachment = dataManager.add(Tools.getGson().fromJson(attachmentJson, Attachment.class));
			return Tools.getGson().toJson(attachment);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String addAttachments(String attachmentJsons) {
		try {
			Type attachmentType = new TypeToken<List<Attachment>>(){}.getType();
			List<Attachment> attachments = Tools.getGson().fromJson(attachmentJsons, attachmentType);
			Iterator<Attachment> iter = attachments.iterator();
			while (iter.hasNext()) {
				Attachment attachment = iter.next();
				dataManager.add(attachment);
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
	public String updateAttachment(String attachmentJson) {
		try {
			dataManager.update(Tools.getGson().fromJson(attachmentJson, Attachment.class));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String updateAttachments(String attachmentJsons) {
		try {
			Type attachmentType = new TypeToken<List<Attachment>>(){}.getType();
			List<Attachment> attachments = Tools.getGson().fromJson(attachmentJsons, attachmentType);
			Iterator<Attachment> iter = attachments.iterator();
			while (iter.hasNext()) {
				Attachment attachment = iter.next();
				dataManager.update(attachment);
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
	public String deleteAttachmentById(String id) {
		try {
			dataManager.deleteById(Attachment.class, Integer.parseInt(id));
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Transactional
	@Log
	public String deleteAttachmentByIds(String ids) {
		try {
			Integer[] idsArr = new Gson().fromJson(ids, Integer[].class);
			dataManager.deleteByIdBatch(Attachment.class, idsArr);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	@Log
	public String findRelateAttachments(String busTableName, String busDataId) {
		try {
			List<Attachment> attachments = dataManager.createNamedQuery("Attachment.findRelateAttachments", Attachment.class).setParameter("busTableName", busTableName).setParameter("busDataId", busDataId).getResultList();
			return Tools.getGson().toJson(attachments);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

}
