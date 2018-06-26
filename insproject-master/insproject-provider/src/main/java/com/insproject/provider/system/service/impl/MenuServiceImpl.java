package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.component.service.ext.tree.TreeService;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.common.context.App;
import com.insproject.provider.system.SystemCache;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Menu;
import com.insproject.provider.system.entity.Role;
import com.insproject.provider.system.repository.AuthzRepository;
import com.insproject.provider.system.repository.MenuRepository;
import com.insproject.provider.system.repository.OperateRepository;
import com.insproject.provider.system.repository.RoleRepository;
import com.insproject.provider.system.service.MenuService;

@Service("MenuServiceImpl")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService{	
	
	@Autowired
	@Qualifier("MenuRepositoryImpl")
	private MenuRepository menuRepository;
	
	@Autowired
	private TreeService treeService;
	
	@Autowired
	@Qualifier("AuthzRepositoryImpl")
	private AuthzRepository authzRepository;
	
	@Autowired
	@Qualifier("OperateRepositoryImpl")
	private OperateRepository operateRepository;
	
	@Autowired
	@Qualifier("RoleRepositoryImpl")
	private RoleRepository RoleRepository;
	
	@Override
	public Map<String, Object> load(Serializable id) {		
		return menuRepository.load(id);
	}

	@Override
	public Map<String, Object> loadSysTree(Serializable userId) {			
		boolean isAdmin = RoleRepository.checkUserisSysAdmin(userId);			
		Map<String, Object> condition = new HashMap<String, Object>();	
		//设置只显示enabled的
		condition.put("enabled", 1);
		//如果不是超级管理员,则设置过滤条件
		if(!isAdmin){			
			List<String> authzIds = authzRepository.loadUserAllAuthzIds(userId);
			condition.put("authzIds", authzIds);				
		}			
		List<Map<String, Object>> data = menuRepository.loadSysTreeData(condition);				
		for(Map<String, Object> record : data){
			if(record.get("icon") != null){
				record.put("icon", App.STATIC_PATH + record.get("icon"));
			}
		}		
		List<Map<String, Object>> treeList = treeService.parse(data, null, true, false);		
		return treeService.bulidFinalTree(treeList);
	}

	
	@Override
	public Map<String, Object> loadMgtTree() {
		List<Map<String, Object>> data = menuRepository.loadMgtTreeData();	
		data = updateMgtTreeIcon(data);
		List<Map<String, Object>> treeList = treeService.parse(data, null, true, false);
		return treeService.bulidFinalTree(treeList);
	}
	
	@Override
	public Map<String, Object> loadMgtTree(String roleId, String userId) {
		//判断当前用户是否是超级管理员
		boolean isAdmin = RoleRepository.checkUserisSysAdmin(userId);			
		//判断当前角色是否为超级管理员
		boolean roleIsSysAdmin = false;
		Role role = RoleRepository.get(roleId);
		if(role.getId().toString().equals(SystemConstant.SYS_ROLE_CODE_SYSADMIN)){
			roleIsSysAdmin = true;
		}
		//查询角色所拥有的权限
		List<String> roleAuthzIds = authzRepository.loadAuthzIdsByRoleId(roleId);
		//查询所有菜单功能权限
		List<Map<String, Object>> data = menuRepository.loadMgtTreeData();	
		
		for(Map<String, Object> node : data){
			//如果当前角色是超级管理员或者当前角色拥有此节点的权限，设置颜色为蓝色
			if(roleAuthzIds.contains(node.get("authzId").toString()) || roleIsSysAdmin){
				node.put("text", "<span style='color:#003399;'>"+node.get("text")+"</span>");
			}
			//如果当前用户是超级管理员或者当前节点为可授权状态，显示复选框
			int allowAuthz = (Integer) node.get("allowAuthz");			
			if(allowAuthz == 1 || isAdmin){
				//如果当前角色是超级管理员或者当前角色拥有此节点的权限，设置为选中
				if(roleAuthzIds.contains(node.get("authzId").toString()) || roleIsSysAdmin){
					node.put("checked", true);
				}else{
					node.put("checked", false);
				}
			}
		}		
		data = updateMgtTreeIcon(data);
		List<Map<String, Object>> treeList = treeService.parse(data, null, true, false);
		return treeService.bulidFinalTree(treeList);
	}
	
	
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(Menu entity) {		
		String id = (String) menuRepository.save(entity);
		//添加权限
		authzRepository.addAuthzMenu(id);
		//更新所有的父节点enable和allow_authz字段		
		entity.setId(id);
		updateParentEnabledAndAllowAuthz(entity);
		//清除缓存中的所有菜单列表
		SystemCache.remove(SystemCache.CACHE_SYSTEM_MENU_ALL_MAP);
		return id;
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(Menu entity) {		
		int returnValue = menuRepository.update(entity);
		//更新所有的父节点enable和allow_authz字段		
		updateParentEnabledAndAllowAuthz(entity);	
		//更新所有子节点的enabled和allowauthz字段
		updateChildrenEnabledAndAllowAuthz(entity);
		//清除缓存中的所有菜单列表
		SystemCache.remove(SystemCache.CACHE_SYSTEM_MENU_ALL_MAP);
		return returnValue;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Map<String, Object>> loadAllMenuMap() {
		Map<String, Map<String, Object>> map = null;
		if(SystemCache.contain(SystemCache.CACHE_SYSTEM_MENU_ALL_MAP)){			
			map = 
				(Map<String, Map<String, Object>>) SystemCache.get(SystemCache.CACHE_SYSTEM_MENU_ALL_MAP);			
		}else{
			String sql = "select v.* from v_sys_authz_menus v";
			List<Map<String, Object>> list = jdbcAssistant.query(sql);
			map = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> item : list){
				if(item.get("resourcePath") != null){
					map.put(item.get("resourcePath").toString(), item);
				}
			}
			SystemCache.add(SystemCache.CACHE_SYSTEM_MENU_ALL_MAP, map);
		}		
		return map;
	}
	
	
	/**
	 * 重写父类deleteById方法
	 * 
	 */
	@Override
	public int deleteById(Serializable id) {		
		//删除权限
		authzRepository.deleteAuthzByMenuId((String)id);
		//删除其操作
		List<Map<String, Object>> operates = operateRepository.loadByMenuId((String)id);
		for(Map<String, Object> operate : operates){
			authzRepository.deleteAuthzByOperateId(operate.get("id").toString());
		}	
		//删除自身
		int returnValue = super.deleteById(id);
		//清除缓存中的所有菜单列表
		SystemCache.remove(SystemCache.CACHE_SYSTEM_MENU_ALL_MAP);
		return returnValue;		
	}
	
	
	/**
	 * 更新所有父节点的enabled和allowauthz字段
	 * ps：只有当前节点的enabled为true时、allowauthz为true时才更新
	 * @param entity
	 */
	private void updateParentEnabledAndAllowAuthz(Menu entity){
		String updateCondition = "";
		if(entity.getEnabled().equals(1)){
			updateCondition += "enabled=1"; 
		}
		if(entity.getAllowAuthz().equals(1)){
			updateCondition += ",allow_authz=1"; 
		}
		if(TextUtil.isNotEmpty(updateCondition)){
			if(updateCondition.startsWith(",")){
				updateCondition = updateCondition.substring(1, updateCondition.length());
			}
			List<Map<String, Object>> sup = menuRepository.loadCascadeMenuOpers(String.valueOf(entity.getId()), false, true);
			if(sup.size() > 0){
				String [] sqls = new String [sup.size()];			
				for(int i=0; i<sqls.length; i++){
					sqls[i] = "update sys_menu set "+updateCondition+" where id = '"+sup.get(i).get("id")+"' ";
				}
				jdbcAssistant.batchUpdate(sqls);
			}		
		}		
	}
	
	/**
	 * 更新所有子节点的enabled和allowauthz字段
	 * @param entity
	 */
	private void updateChildrenEnabledAndAllowAuthz(Menu entity){
		Integer enabled = entity.getEnabled();
		Integer allowAuthz = entity.getAllowAuthz();	
		List<Map<String, Object>> children = menuRepository.loadCascadeMenuOpers(String.valueOf(entity.getId()), false, false);		
		if(children.size() > 0){
			String [] sqls = new String [children.size()];
			for(int i=0; i<sqls.length; i++){
				Map<String, Object> child = children.get(i);
				if(Integer.parseInt(child.get("type").toString())==SystemConstant.SYS_MENU_OPERATE){
					sqls[i] = "update sys_operate set enabled='"+enabled+"' , allow_authz='"+allowAuthz+"' where id = '"+child.get("id")+"' ";
				}else{
					sqls[i] = "update sys_menu set enabled='"+enabled+"' , allow_authz='"+allowAuthz+"' where id = '"+child.get("id")+"' ";
				}
			}
			jdbcAssistant.batchUpdate(sqls);
		}
	}

	@Override
	public BaseRepository<Menu> getRepository() {		
		return menuRepository;
	}

	/**
	 * 设置树形结构图标
	 * @param data
	 * @return
	 */
	private List<Map<String, Object>> updateMgtTreeIcon(List<Map<String, Object>> data){
		for(Map<String, Object> node : data){
			//组			
			if(Integer.parseInt(node.get("type").toString())==SystemConstant.SYS_MENU_GROUP){				
				node.put("icon", App.STATIC_PATH + "/common/image/icon/folder.png");				
			}
			//模块
			else if(Integer.parseInt(node.get("type").toString())==SystemConstant.SYS_MENU_MODULE){
				node.put("icon", App.STATIC_PATH + "/common/image/icon/form.png");
			}
			//操作
			else if(Integer.parseInt(node.get("type").toString())==SystemConstant.SYS_MENU_OPERATE){
				node.put("icon", App.STATIC_PATH + "/common/image/icon/config.png");
			}
		}
		return data;
	}

	
	
}
