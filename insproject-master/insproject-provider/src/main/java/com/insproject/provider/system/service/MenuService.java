package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.Menu;

public interface MenuService extends BaseService<Menu>{
	
	/**
	 *	系统菜单树（系统管理左侧菜单树，系统管理员角色可以看到所有，其它角色根据自身权限）
	 * @param request
	 * @param condition
	 * @return
	 */
	Map<String, Object> loadSysTree(Serializable request);
	
	/**
	 * 菜单管理树
	 * @param request
	 * @param condition
	 * @return
	 */
	Map<String, Object> loadMgtTree();
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(Serializable id);
	
	/**
	 * 加载所有的菜单map(以reesourcePath为key)
	 */
	public Map<String, Map<String, Object>> loadAllMenuMap();
	
	/**
	 * 加载角色菜单树
	 * @param roleId
	 * @return
	 */
	Map<String, Object> loadMgtTree(String roleId, String userId);
}
