package com.insproject.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.component.service.file.BaseUploadService;
import com.insplatform.component.service.file.bean.FileBean;
import com.insplatform.core.http.messager.Messager;
import com.insplatform.core.utils.TextUtil;
import com.insproject.common.context.App;

@Controller
public class UploadController{
	
	@Autowired
	private BaseUploadService baseUploadService;
	
	/**
	 * 上传文件
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fileupload")
	public @ResponseBody Map<String, Object> upload(HttpServletRequest request) throws Exception{
		//文件input框的name
		String fileName = request.getParameter("fileName");
		//上传类型
		String isTemp = request.getParameter("isTemp");
		FileBean fileBean = null;
		if(TextUtil.isNotEmpty(isTemp) && Boolean.valueOf(isTemp)){
			fileBean = 
					baseUploadService.uploadFile(baseUploadService.getMultipartFile(request, fileName), App.FILE_REALPATH, null, "temp", false);			
		}else{	
			fileBean = 
					baseUploadService.uploadFile(baseUploadService.getMultipartFile(request, fileName), App.FILE_REALPATH, null, request.getParameter("module"));
		}		
		if(fileBean != null){
			return Messager.getJsonMessager().success().put("file", fileBean);
		}else{
			return Messager.getJsonMessager().error();
		}
	}
	
}