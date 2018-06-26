package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.Role;

public interface RoleService extends BaseService<Role>{
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> loadList(Condition condition);
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(Serializable id);
	
	/**
	 * 判断用户是否有当前角色
	 * @param userId
	 * @param roleCode
	 * @return
	 */
	boolean checkUserRoleByUserIdAndRoleCode(Serializable userId, String roleCode);
	
	/**
	 * 将用户添加到角色中
	 * @param roleId
	 * @param userIds
	 */
	void addRoleUser(String roleId, String [] userIds);
	
	/**
	 * 删除角色下的用户
	 * @param id
	 * @param userIds
	 */
	void deleteRoleUser(String id, String [] userIds);	
	
	/**
	 * 检查名称和编码是否重复
	 * @param name
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkNameAndCode(String name, String code, String id);	
	
	/**
	 * 更新角色下的权限
	 * @param roleId
	 * @param authzIds
	 */
	void updateRoleAuthz(String roleId, String [] authzIds);
	
	/**
	 * 更新角色下的数据权限
	 * @param id
	 * @param level
	 * @param dataAuthzIds
	 */
	void updateRoleDataAuthz(String id, int level, String[] dataAuthzIds);
	
}
