package com.insproject.common.context.init.impl;


import java.util.Properties;

import javax.servlet.ServletContext;

import com.insproject.common.context.App;
import com.insproject.common.context.init.Initializer;




/**
 * 初始化全局参数
 * @author guom
 *
 */
public class ContextInitializer implements Initializer{

	public void before(ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void init(ServletContext ctx) throws Exception {
		//加载全局配置文件
		Properties applictionCfg = 
				com.insplatform.core.utils.FileUtil.getProperties("/application.properties");			
		if(applictionCfg != null){	
			
			//加密解密key
			App.SECURITY_KEY = applictionCfg.getProperty("security.key");				
			
			//静态资源路径
			App.STATIC_PATH = applictionCfg.getProperty("static.path");
			ctx.setAttribute("STATIC_PATH", App.STATIC_PATH);		
			
			//上传文件路径
			App.FILE_PATH = applictionCfg.getProperty("file.path");	
			App.FILE_REALPATH = applictionCfg.getProperty("file.realPath");
			ctx.setAttribute("FILE_PATH", App.FILE_PATH);	
			
			//报表服务器地址
			App.REPORT_PATH = applictionCfg.getProperty("report.path");
			ctx.setAttribute("REPORT_PATH", App.REPORT_PATH);	
			
			//程序运行模式，Y开发模式，N正式发布模式
			App.IS_DEV_MODEL = applictionCfg.getProperty("is.dev.model");
			ctx.setAttribute("IS_DEV_MODEL", App.IS_DEV_MODEL);	
			
		}
		
	}

	public void after(ServletContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
	}
	


}
