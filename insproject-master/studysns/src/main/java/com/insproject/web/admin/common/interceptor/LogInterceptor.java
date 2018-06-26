package com.insproject.web.admin.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 记录日志
 * 
 * @author guom
 *
 */
public class LogInterceptor implements HandlerInterceptor {

	private Logger logger = Logger.getLogger(LogInterceptor.class);
	
	private ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	private ThreadLocal<Long> endTime = new ThreadLocal<Long>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info(request.getServletPath());
		
		// 设置开始时间
		startTime.set(System.currentTimeMillis());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {		
		
	}
	

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception { 
		if(ex != null){
			System.out.println(ex);
		}
	}

}
