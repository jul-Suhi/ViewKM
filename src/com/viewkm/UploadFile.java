package com.viewkm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.security.auth.message.callback.SecretKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadFile extends ActionSupport{
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	private String filename;
	private String uploadPath;
    private String result;
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@Override
	public String execute() throws Exception {
		String fn = "";
		HttpServletRequest request = ServletActionContext.getRequest();
		if(filename.equals(""))
		{
			fn = uploadPath + uploadFileName;
			request.setAttribute("sfilename", uploadFileName);
		}else{
			fn = uploadPath + filename;
			request.setAttribute("sfilename", filename);
		}
		if(new File(fn).exists())
		{
			result = "该文件已经存在!";
		}else{
			FileOutputStream fos = new FileOutputStream(fn);
			InputStream is = new FileInputStream(upload);
			byte[] buffer = new byte[10240];//每次读10k
			int count = 0;
			while((count = is.read(buffer)) > 0)
			{
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
			result = "文件上传成功";
		}
		return "result";
	}

}