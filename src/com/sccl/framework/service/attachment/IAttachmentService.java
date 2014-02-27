package com.sccl.framework.service.attachment;

public interface IAttachmentService {
	
	public String addAttachment(String attachmentJson);
	public String addAttachments(String attachmentJsons);
	public String updateAttachment(String attachmentJson);
	public String updateAttachments(String attachmentJsons);
	public String deleteAttachmentById(String id);
	public String deleteAttachmentByIds(String ids);
	
	public String findRelateAttachments(String busTableName, String busDataId);

}
