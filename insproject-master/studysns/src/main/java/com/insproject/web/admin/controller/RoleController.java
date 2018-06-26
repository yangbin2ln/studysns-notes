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
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.Role;
import com.insproject.provider.system.service.RoleService;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

@Controller
@RequestMapping("/system/role")
public class RoleController extends InsBaseController{
	
	@Autowired
	@Qualifier("RoleServiceImpl")
	private RoleService roleService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadList")
	public @ResponseBody Map<String, Object> loadList(
			HttpServletRequest request, HttpServletResponse response){
		CurrentUser user = UserSession.getCurrentUser(request);
		Condition condition = new Condition(request);
		condition.put("userId", user.getId());
		return roleService.loadList(condition);
	}
	
	
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(roleService.load(id));		
	}
	
	/**
	 * 新增
	 * @param role
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(Role role){
		roleService.save(role);
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
		Role role = roleService.get(id);
		bindObject(request, role);
		roleService.update(role);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		String id = request.getParameter("id");
		roleService.deleteById(id);
		return Messager.getJsonMessager().success();
	}

	/**
	 * 检查名称或编码是否重复
	 * @param name
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkNameAndCode")
	public @ResponseBody Map<String, Object> checkNameAndCode(
			@RequestParam("name") String name,
			@RequestParam("code") String code,
			@RequestParam(value="id", required=false) String id){
		boolean has = roleService.checkNameAndCode(name, code, id);
		return Messager.getJsonMessager().success().put("has", has);
	}
	
	/**
	 * 删除角色下的用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteRoleUser")	
	public @ResponseBody Map<String, Object> removeRoleUsers(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] userIds = this.getSelectedItems(request, null);
		roleService.deleteRoleUser(id, userIds);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 添加角色下的用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/addRoleUser")
	public @ResponseBody Map<String, Object> addRoleUser(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] userIds = this.getSelectedItems(request, null);
		roleService.addRoleUser(id, userIds);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 添加角色下的权限
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateRoleAuthz")
	public @ResponseBody Map<String, Object> updateRoleAuthz(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] authzIds = this.getSelectedItems(request, null);
		roleService.updateRoleAuthz(id, authzIds);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 添加角色下的数据权限
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateRoleDataAuthz")
	public @ResponseBody Map<String, Object> updateRoleDataAuthz(HttpServletRequest request){
		String id = request.getParameter("id");
		String level = request.getParameter("level");
		String[] dataAuthzIds = this.getSelectedItems(request, null);
		roleService.updateRoleDataAuthz(id, Integer.parseInt(level), dataAuthzIds);
		return Messager.getJsonMessager().success();
	}
	
}
