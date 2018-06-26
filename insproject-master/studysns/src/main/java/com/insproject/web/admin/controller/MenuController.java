package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.Messager;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.core.utils.WebUtil;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Log;
import com.insproject.provider.system.entity.Menu;
import com.insproject.provider.system.service.LogService;
import com.insproject.provider.system.service.MenuService;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

@Controller
@RequestMapping("/system/menu")
public class MenuController extends InsBaseController{
	
	@Autowired	
	@Qualifier("MenuServiceImpl")
	private MenuService menuService;
	
	@Autowired
	@Qualifier("LogService")
	private LogService logService;
	
	
	/**
	 * 查询菜单树
	 * @param request
	 * @param condition
	 * @return
	 */
	@RequestMapping("/loadSysTree")	
	public @ResponseBody Map<String, Object> loadSysTree(HttpServletRequest request){		
		String userId = UserSession.getCurrentUser(request).getId();
		return menuService.loadSysTree(userId);		
	}	
	
	/**
	 * 查询菜单管理树
	 * @param request
	 * @param condition
	 * @return
	 */
	@RequestMapping("/loadMgtTree")	
	public @ResponseBody Map<String, Object> loadMgtTree(){				
		return menuService.loadMgtTree();		
	}	
	
	/**
	 * 查询角色菜单树
	 * @param request
	 * @param condition
	 * @return
	 */
	@RequestMapping("/loadRoleTree")	
	public @ResponseBody Map<String, Object> loadRoleTree(@RequestParam(name="roleId", required=false)String roleId, 
			HttpServletRequest request){	
		if(TextUtil.isEmpty(roleId)){
			return Messager.getJsonMessager().success();
		}
		CurrentUser user = UserSession.getCurrentUser(request);
		return menuService.loadMgtTree(roleId, user.getId());		
	}	
	
	/**
	 * 加载单条数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(HttpServletRequest request){
		String id = request.getParameter("id");
		//记录日志
		String log = request.getParameter("log");
		if(TextUtil.isNotEmpty(log)){
			CurrentUser user = UserSession.getCurrentUser(request);
			Log bean = new Log();
			bean.setType(SystemConstant.LogConstant.MENU.getType());
			bean.setCreateUserId(Integer.parseInt(user.getId()));
			bean.setCreateUserName(user.getName());
			bean.setIp(WebUtil.getIpAddr(request));
			bean.setMenuId(id);
			bean.setMenuName(menuService.get(id).getName());			
			logService.saveLog(bean);
		}
		return Messager.getJsonMessager().success().data(menuService.load(id));
	}
	
	/**
	 * 添加
	 * @param menu
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(Menu menu){
		menuService.save(menu);		
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 删除
	 * @param menu
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		String id = request.getParameter("id");
		menuService.deleteById(id);
		return Messager.getJsonMessager().success();
	}	
	
	/**
	 * 编辑
	 * @param menu
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(HttpServletRequest request){		
		String id = request.getParameter("id");
		Menu menu = menuService.get(id);
		bindObject(request, menu);
		menuService.update(menu);
		return Messager.getJsonMessager().success();
	}
}
