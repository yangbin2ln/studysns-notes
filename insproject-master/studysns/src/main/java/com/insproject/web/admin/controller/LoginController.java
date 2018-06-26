package com.insproject.web.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.Messager;
import com.insproject.common.context.App;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.Organization;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.service.OperateService;
import com.insproject.provider.system.service.OrganizationService;
import com.insproject.provider.system.service.RoleService;
import com.insproject.provider.system.service.UserService;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

@Controller
public class LoginController extends InsBaseController{
	
	Logger logger = Logger.getLogger(LoginController.class);	
	
	@Autowired
	@Qualifier("UserServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("RoleServiceImpl")
	private RoleService roleService;
	
	@Autowired
	@Qualifier("OperateServiceImpl")
	private OperateService operateService;
	
	@Autowired
	@Qualifier("OrganizationServiceImpl")
	private OrganizationService organizationService;
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request){		
		return "login";
	}	
	
	/**
	 * 登陆验证
	 * @param userName	账号
	 * @param password	密码
	 * @param captcha	验证码
	 * @param request
	 * @return
	 */
	@RequestMapping("/login/enter")
	@ResponseBody		
	public Map<String,Object> enter(@RequestParam(value="userName",required=false) String userName,
					  @RequestParam(value="password",required=false) String password,
					  @RequestParam(value="captcha",required=false) String captcha,
					  HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//检查验证码
		boolean isResponseCorrect = false;
		try{
			Object sessionCaptcha = request.getSession().getAttribute("SYSTEM_LOGIN_CAPTCHA");
			if(sessionCaptcha != null){
				isResponseCorrect = sessionCaptcha.toString().equals(captcha.toUpperCase());
				if(isResponseCorrect){
					map.put("result", "CAPTCHA_SUCCESS");
				}
			}
		}catch(Exception ex){
			logger.error("登陆验证码验证失败",ex);
		}		
				
		if(!isResponseCorrect){
			map.put("result", "CAPTCHA_ERROR");
		}else{	
			//检查用户名密码
			User sysUser = userService.loadSysUserByAccountAndPassword(userName, password);
			if(sysUser != null){				
				if(sysUser.getEnabled().equals(1)){
					map.put("result", "ACCOUNT_SUCCESS");
					setCurrentUserInfo(request, sysUser);	
				}else{
					map.put("result", "ACCOUNT_LOCKED");
				}								
			}else{
				map.put("result", "ACCOUNT_ERROR");
			}			
		}
		
		return map;		
	}
	
	/**
	 * 退出系统
	 * @param request
	 * @return
	 */
	@RequestMapping("/login/out")	
	public String out(HttpServletRequest request){
		request.getSession().invalidate();
		//request.getSession().setAttribute(App.CURRENT_USER, null);
		return "redirect:/main";
	}
	
	/**
	 * 获取当前用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/login/loadCurrentUserInfo")
	public @ResponseBody Map<String, Object> loadCurrentUserInfo(HttpServletRequest request){
		CurrentUser currUser = UserSession.getCurrentUser(request);
		if(currUser == null){
			throw new IllegalArgumentException();
		}
		currUser = setCurrentUserInfo(request, userService.get(currUser.getId()));
		//resources没用，节省流量不往前台返回了
		currUser.setOperateResources(null);
		return Messager.getJsonMessager().success().data(currUser);
	}
	
	/**
	 * 设置当前用户
	 * @param request
	 * @param sysUser
	 * @return
	 */
	private CurrentUser setCurrentUserInfo(HttpServletRequest request, User sysUser){		
		//设置基本信息
		CurrentUser currentUser = new CurrentUser();
		currentUser.setId(String.valueOf(sysUser.getId()));
		currentUser.setName(sysUser.getName());
		currentUser.setAccount(sysUser.getAccount());
		Organization dept = organizationService.loadDepartmentByUserId(String.valueOf(sysUser.getId()));
		if(dept != null){
			currentUser.setDepartmentId(String.valueOf(dept.getId()));
			currentUser.setDepartmentName(dept.getName());
		}
		//操作权限信息
		Map<String, Map<String, Object>> oeprateMap = operateService.loadUserOperateMap(sysUser.getId());
		currentUser.setOperateMap(oeprateMap);
		List<String> operateResources = operateService.loadUserOperateResources(sysUser.getId());
		currentUser.setOperateResources(operateResources);
		//将当前用户放在session中
		request.getSession().setAttribute(App.CURRENT_USER, currentUser);		
		return currentUser;
	}	

}
