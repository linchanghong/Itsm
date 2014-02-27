﻿package com.sccl.framework.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 上次文件
 * @author Administrator
 *
 */
public class SendFileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -854468947719227517L;
	private int maxPostSize=100 * 1024 * 1024;//限制文件大小
	private PrintWriter writer;
	private String uploadPath = "/upload/";
	
	/**
	 * 上传文件返回文件的路径
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void sendFile(HttpServletRequest request,HttpServletResponse response, String uploadPath)throws Exception
	{
		response.setContentType("text/html; charset=UTF-8");
//		request.setCharacterEncoding("UTF-8");
		//response.setCharacterEncoding("UTF-8");
		String userName = (String)request.getParameter("userName"); //因为flex传过来的url本身就是utf8的编码，所以不用转换，但只适用于flex
		userName = new String(userName.getBytes("iso-8859-1"),"UTF-8");
		String filePaths=""; 
		String relativePath = "";
		writer=response.getWriter();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); // 检查是否是一个文件上传请求
		if (isMultipart) {
			DiskFileItemFactory factory = new DiskFileItemFactory(); // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
			factory.setSizeThreshold(1024 * 4);// 设置上传文件时用于临时存放文件的内存大小,这里是2K.多于的部分将临时存在硬盘
			String path = getServletConfig().getServletContext().getRealPath(uploadPath);
			if(userName!=null){
				path=path+"/"+userName;
			}
			File file=new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			factory.setRepository(file);// 设置存放临时文件的目录
			ServletFileUpload upload = new ServletFileUpload(factory);// 用上面的工厂实例化上传组件，
			// 如果ServletFileUpload实例中不设置上面的Factory则报java.lang.NullPointerException:
			// No FileItemFactory has been set.
			upload.setFileSizeMax(maxPostSize);
			String tempName = "";
			List fileItems = null;
			//filePath = new ArrayList();//返回的文件路径的list
			try {
				fileItems = upload.parseRequest(request);
				Iterator iter = fileItems.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (!item.isFormField()) {// 判断是否是表单元素
						String fileName = item.getName();
						try {
							if (fileName != null) {
								tempName = fileName.substring(0, fileName
										.indexOf("."))
										+ Calendar.getInstance()
												.getTimeInMillis()
										+ fileName.substring(fileName
												.lastIndexOf("."));
							}
							String uploadFilePath = path + "/" + tempName;
							if(userName!=null){
								relativePath = uploadPath + userName + "/" + tempName;
							}
							else{
								relativePath = uploadPath + tempName;
							}
							File uploadFile = new File(uploadFilePath);
							item.write(uploadFile);
							//filePath.add(uploadFilePath);
							filePaths=filePaths+uploadFilePath+",";
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			} catch (FileUploadException ex) {
				ex.printStackTrace();
			}
		}
		writer.write(filePaths);
		writer.write(relativePath);
		//return filePaths;同时返回绝对路径和相对路径
	}
//	private void doUploadAdd(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		try {
//			ServletContext context = getServletConfig().getServletContext();
//			ApplicationContext ctx = WebApplicationContextUtils
//					.getRequiredWebApplicationContext(context);
//			resourceService = (IResourceService) ctx
//					.getBean("resourceServiceImpl");
//			constantsFlexService=(ConstantsFlexService)ctx.getBean("constantsFlexService");
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		saveFiletoServer(request, response, uploadPath);
//	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			sendFile(request, response,uploadPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			sendFile(request, response,uploadPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMaxPostSize() {
		return maxPostSize;
	}

	public void setMaxPostSize(int maxPostSize) {
		this.maxPostSize = maxPostSize;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

}
