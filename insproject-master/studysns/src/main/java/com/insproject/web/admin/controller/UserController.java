package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.Condition;
import com.insplatform.core.http.messager.Messager;
import com.insplatform.core.utils.TextUtil;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.service.UserService;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

@Controller
@RequestMapping("/system/user")
public class UserController extends InsBaseController{
	
	@Autowired
	@Qualifier("UserServiceImpl")
	private UserService userService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadList")
	public @ResponseBody Map<String, Object> loadList(
			HttpServletRequest request, HttpServletResponse response){
		Condition condition = new Condition(request, "name", "account", "enabled", "orgName");
		CurrentUser user = UserSession.getCurrentUser(request);
		condition.put("userId", user.getId());
		
		//组织机构管理时需要用到的参数
		if(TextUtil.isNotEmpty(request.getParameter("orgId"))){
			condition.put("orgId", request.getParameter("orgId"));		
		}
		if(TextUtil.isNotEmpty(request.getParameter("notOrg"))){
			condition.put("notOrg", request.getParameter("notOrg"));		
		}
		
		//角色管理时需要用到的参数
		if(TextUtil.isNotEmpty(request.getParameter("roleId"))){
			condition.put("roleId", request.getParameter("roleId"));		
		}
		if(TextUtil.isNotEmpty(request.getParameter("notRoleId"))){
			condition.put("notRoleId", request.getParameter("notRoleId"));		
		}
		
		return userService.loadList(condition);
	}
		
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(userService.load(id));		
	}
	
	/**
	 * 新增
	 * @param sysuser
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(User sysuser, HttpServletRequest request){
		CurrentUser user = UserSession.getCurrentUser(request);
		sysuser.setCreateUserId(user.getId());
		userService.save(sysuser);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 编辑
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(
		   @RequestParam("id") String id,
		   HttpServletRequest request){		
		User sysuser = userService.get(id);
		bindObject(request, sysuser);
		userService.update(sysuser);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		String [] ids = getSelectedItems(request, null);	
		userService.deleteByIds(ids);
		return Messager.getJsonMessager().success();
	}

	/**
	 * 检查旧密码
	 * @param request
	 */
	@RequestMapping("/checkOldPwd")
	public @ResponseBody Map<String, Object> checkOldPwd(
		   @RequestParam("account") String account,
		   @RequestParam("password") String password){		
		boolean flag = userService.checkOldPwd(account, password);
		if(flag){
			return Messager.getJsonMessager().success();
		}else{
			return Messager.getJsonMessager().error();
		}
	}
	
	/**
	 * 修改密码
	 * @param request
	 */
	@RequestMapping("/changePwd")	
	public @ResponseBody Map<String, Object> changePwd(
		   @RequestParam("account") String account,
		   @RequestParam("password") String password){
		boolean flag = userService.updatePwd(account, password);
		if(flag){
			return Messager.getJsonMessager().success();
		}else{			
			return Messager.getJsonMessager().error();
		}		
	}
	
	/**
	 * 检查账号是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkHasAccount")
	public @ResponseBody Map<String, Object> checkHasAccount(HttpServletRequest request){
		String id = request.getParameter("id");
		String account = request.getParameter("account");
		boolean has = userService.checkHasAccount(account, id);
		return Messager.getJsonMessager().success().put("has", has);
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetPassword")	
	public @ResponseBody Map<String, Object> resetPassword(HttpServletRequest request){	
		userService.updateResetPwd(getSelectedItems(request, null));
		return Messager.getJsonMessager().success();
	}
	
}
