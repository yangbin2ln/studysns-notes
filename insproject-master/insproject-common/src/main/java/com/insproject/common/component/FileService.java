package com.insproject.common.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.insplatform.component.service.file.BaseDownloadService;
import com.insplatform.component.service.file.BaseUploadService;
import com.insplatform.component.service.file.bean.FileBean;
import com.insplatform.core.utils.FileUtil;
import com.insproject.common.context.App;

@Component
public class FileService {
	
	@Autowired
	private BaseDownloadService baseDownloadService;
	
	@Autowired
	private BaseUploadService baseUploadService;
	
	/**
	 * 下载文件	
	 * @param filePath 文件路径
	 * @param response
	 * @throws Exception
	 */
	public void downloadFile(String filePath, HttpServletResponse response){		
		baseDownloadService.downloadFile(filePath, null, response);
	}
	
	/**
	 * 下载文件 	 
	 * @param filePath
	 *            文件路径
	 * @param newFileName
	 *            下载文件名称（包含扩展名）
	 * @param response
	 * @throws Exception
	 */
	public void downloadFile(String filePath, String newFileName,
			HttpServletResponse response){
		baseDownloadService.downloadFile(filePath, newFileName, response);
	}
	
	/**
	 * 从互联网url下载文件
	 * @param url
	 * @param newFileName
	 * @param localPath
	 * @throws Exception
	 */
	public void downloadFileByNet(String url, String localPath){
		baseDownloadService.downloadFileByNet(url, localPath);
	}	
	
	/**
	 * 上传文件
	 * @param request
	 * @param filedName
	 * @param moduleName
	 * @return
	 * @throws Exception
	 */
	public FileBean uploadFile(HttpServletRequest request, String filedName, String moduleName) throws Exception{
		return baseUploadService.uploadFile(getMultipartFile(request, filedName), App.FILE_REALPATH, null, moduleName);
	}	
	
	
	/**
	 * 用流上传文件
	 * @param is
	 * @param fileName
	 * @param moduleName
	 * @return
	 * @throws Exception
	 */
	public FileBean saveFileByInputStream(InputStream is, String fileName, String moduleName) throws Exception{
		return baseUploadService.savefileByInputStream(is, fileName, App.FILE_REALPATH, null, moduleName, true);
	}	
	
	
	/**
	 * 拷贝文件到模块下
	 * @param tempUrl
	 * @param moduleName
	 * @param userTimeDir
	 * @return
	 */
	public FileBean copyFileToModule(String fileUrl, String moduleName){
		Assert.notNull(fileUrl);	
		Assert.notNull(moduleName);	
		FileBean fileBean = new FileBean();
		String fileRealPath = App.FILE_REALPATH + fileUrl;
		File file = new File(fileRealPath);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			String fileName = FileUtil.getName(file.getName());
			fileBean = saveFileByInputStream(is, fileName, moduleName);		
		} catch (Exception e) {		
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(is);
		}
		return fileBean;
	}
	
	/**
	 * 获取上传文件MultipartFile
	 * @param request
	 * @param name
	 * @return
	 */
	public MultipartFile getMultipartFile(HttpServletRequest request, String name) {
		return baseUploadService.getMultipartFile(request, name);
	}
	
	/**
	 * 获取上传文件列表
	 * @param request
	 * @param name
	 * @return
	 */
	public List<MultipartFile> getMultipartFiles(HttpServletRequest request, String name) {
		return baseUploadService.getMultipartFiles(request, name);
	}
	
	
}
