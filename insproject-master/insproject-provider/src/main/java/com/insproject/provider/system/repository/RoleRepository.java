package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.Role;


public interface RoleRepository extends BaseRepository<Role>{
	
	/**
	 * 获取查询sql语句
	 * @return
	 */
	public String getQuerySql(Condition condition);
	
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
	public boolean checkUserRoleByUserIdAndRoleCode(Serializable userId,
			String roleCode);
	
	/**
	 * 判断用户是否是超级管理员
	 * @param userId
	 * @param roleCode
	 * @return
	 */
	public boolean checkUserisSysAdmin(Serializable userId);
	
	/**
	 * 加载用户关联的角色
	 * @return
	 */
	List<String> loadUserRoleIds(Serializable userId);
		
	/**
	 * 根据编码获取角色
	 * @param code
	 * @return
	 */
	Map<String, Object> loadRoleByCode(String code);
	
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
	 * 检查角色名称或编码是否重复
	 * @param name
	 * @param code
	 * @param id
	 * @return 如果重复，返回true，不重复返回false
	 */
	boolean checkNameAndCode(String name, String code, String id);
	
	/**
	 * 添加到角色中
	 * @param roleId
	 * @param userIds
	 */
	void addRoleAuthz(String roleId, String [] authzIds);
	
	/**
	 * 删除角色下的权限
	 * @param id
	 * @param userIds
	 */
	void deleteRoleAuthz(String roleId, String [] authzIds);
	
	
	
}
