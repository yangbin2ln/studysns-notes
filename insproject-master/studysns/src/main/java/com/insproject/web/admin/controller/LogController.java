package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.Condition;
import com.insplatform.core.http.messager.Messager;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.service.LogService;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

/**
 * 日志控制器
 *
 */
@Controller
@RequestMapping("/system/log")
public class LogController extends InsBaseController{
	
	@Autowired
	@Qualifier("LogService")
	private LogService logService;
	
	/**
	 * 加载所有系统操作日志
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadAll")
	public @ResponseBody Map<String, Object> loadAll(HttpServletRequest request){	
		String type = request.getParameter("type");
		CurrentUser user = UserSession.getCurrentUser(request);
		Condition condition = new Condition(request, "menuName", "operateName", "createUserName", "account", "orgName");
		return logService.loadAll(condition, type, user.getId());
	}
	
	
	/**
	 * 删除日志
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	public @ResponseBody Map<String, Object> delete(
		   @RequestParam("type") String type, HttpServletRequest request){
		logService.deleteLog(getSelectedItems(request, null), type);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 清空日志
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteAll")
	public @ResponseBody Map<String, Object> deleteAll(
		   @RequestParam("type") String type, HttpServletRequest request){
		logService.deleteAllLog(type);
		return Messager.getJsonMessager().success();
	}
	
}
