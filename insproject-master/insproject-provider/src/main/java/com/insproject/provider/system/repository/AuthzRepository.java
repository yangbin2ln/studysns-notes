package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.List;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.Authz;


public interface AuthzRepository extends BaseRepository<Authz>{
	
	/**
	 * 添加菜单权限
	 * @param type
	 */
	void addAuthzMenu(String menuId);
	
	/**
	 * 添加操作权限
	 * @param type
	 */
	void addAuthzOperate(String operateId);	
	
	/**
	 * 根据菜单id删除权限
	 * @param id
	 */
	void deleteAuthzByMenuId(String menuId);
	
	/**
	 * 根据操作id删除权限
	 * @param operateId
	 */
	void deleteAuthzByOperateId(String operateId);
	
	/**
	 * 获取角色的权限id集合
	 * @param userId
	 * @return
	 */
	List<String> loadAuthzIdsByRoleId(String roleId);	
	
	/**
	 * 获取用户的所有权限id集合(包括用户的相关角色)
	 * @param userId
	 * @return
	 */
	List<String> loadUserAllAuthzIds(Serializable userId);	
	
	
}
