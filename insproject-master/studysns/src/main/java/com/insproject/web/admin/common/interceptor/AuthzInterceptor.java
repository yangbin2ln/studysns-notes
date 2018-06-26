package com.insproject.web.admin.common.interceptor;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insproject.provider.system.service.OperateService;


/**
 * 登录验证
 * @author guom
 *
 */
public class AuthzInterceptor  extends HandlerInterceptorAdapter {
	
	private Logger logger = Logger.getLogger(AuthzInterceptor.class);
	
	@Autowired
	@Qualifier("OperateServiceImpl")
	private OperateService operateService;
	
	//需要排除的页面
	public static List<String> excludedPageArray;
	
	static {
		excludedPageArray = new ArrayList<String>();	
		excludedPageArray.add("/locale");							//国际化
		excludedPageArray.add("/login");							//登陆页面
		excludedPageArray.add("/captcha");							//验证码		
		excludedPageArray.add("/errorHandler");						//错误处理
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception { 
		return true;
	}

	

}
