package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.Organization;
import com.insproject.provider.system.entity.User;

public interface OrganizationService extends BaseService<Organization>{
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> loadTree(String userId);
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(Serializable id);
	
	/**
	 * 将用户添加到机构中
	 * @param roleId
	 * @param userIds
	 */
	void addOrgUser(String id, String [] userIds);
	
	/**
	 * 删除机构下的用户
	 * @param id
	 * @param userIds
	 */
	void deleteOrgUser(String id, String [] userIds);
	
	/**
	 * 检查机构管理员是否存在
	 * @param account
	 * @param id
	 * @return
	 */
	boolean checkHasAccount(String account, String id);	
	
	/**
	 * 加载机构管理员
	 * @param orgId
	 * @return
	 */
	User loadOrgAdmin(String orgId);
	
	/**
	 * 根据用户id查询用户所在的公司
	 * @param userId
	 * @return
	 */
	Organization loadCompanyByUserId(String userId);
	
	/**
	 * 根据用户id查询用户所在的部门
	 * @param userId
	 * @return
	 */
	Organization loadDepartmentByUserId(String userId);
	
	/**
	 * 查询子节点
	 * @param deptId	机构id
	 * @param includeSelfNode	
	 * @return
	 */
	List<Map<String, Object>> loadChildren(String orgId, boolean includeSelfNode);
	
	/**
	 * 查询有权限的节点
	 * @param deptId
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> loadAuthzOrg(String deptId, String userId);
}
