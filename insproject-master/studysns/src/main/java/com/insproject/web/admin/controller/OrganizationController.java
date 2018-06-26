package com.insproject.web.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.Messager;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.Organization;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.service.OrganizationService;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

@Controller
@RequestMapping("/system/organization")
public class OrganizationController extends InsBaseController{
	
	@Autowired
	@Qualifier("OrganizationServiceImpl")
	private OrganizationService organizationService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadTree")
	public @ResponseBody Map<String, Object> loadTree(
			HttpServletRequest request, HttpServletResponse response){
		CurrentUser user = UserSession.getCurrentUser(request);
		return organizationService.loadTree(user.getId());
	}
	
	
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(organizationService.load(id));		
	}
	
	/**
	 * 新增
	 * @param organization
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(Organization organization){
		organizationService.save(organization);
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
		Organization organization = organizationService.get(id);
		bindObject(request, organization);
		organizationService.update(organization);
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
		organizationService.deleteById(id);
		return Messager.getJsonMessager().success();
	}
	
	
	/**
	 * 检查机构管理员是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkHasOrgAdmin")
	public @ResponseBody Map<String, Object> checkHasOrgAdmin(HttpServletRequest request){
		String id = request.getParameter("id");
		String account = request.getParameter("account");
		boolean has = organizationService.checkHasAccount(account, id);
		return Messager.getJsonMessager().success().put("has", has);
	}
	
	/**
	 * 加载机构管理员
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadOrgAdmin")
	public @ResponseBody Map<String, Object> loadOrgAdmin(HttpServletRequest request){
		String id = request.getParameter("id");
		User orgAdmin = organizationService.loadOrgAdmin(id);
		return Messager.getJsonMessager().success().put("orgAdmin", orgAdmin);
	}
	
	/**
	 * 加载有权限的子节点
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadAuthzOrg")
	public @ResponseBody Map<String, Object> loadAuthzOrg(HttpServletRequest request){
		String deptId = request.getParameter("deptId");
		CurrentUser user = UserSession.getCurrentUser(request);
		List<Map<String, Object>> children = organizationService.loadAuthzOrg(deptId, user.getId());
		return Messager.getJsonMessager().success().put("children", children.toArray());
	}
	
	
	/**
	 * 删除机构下的用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteOrgUser")	
	public @ResponseBody Map<String, Object> deleteOrgUser(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] userIds = this.getSelectedItems(request, null);
		organizationService.deleteOrgUser(id, userIds);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 添加机构下的用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOrgUser")	
	public @ResponseBody Map<String, Object> addOrgUser(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] userIds = this.getSelectedItems(request, null);
		organizationService.addOrgUser(id, userIds);
		return Messager.getJsonMessager().success();
	}

	
	
}
