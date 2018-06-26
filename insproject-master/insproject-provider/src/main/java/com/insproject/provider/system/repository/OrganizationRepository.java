package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.Organization;


public interface OrganizationRepository extends BaseRepository<Organization>{
	

	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(Serializable id);

	/**
	 * 加载组织机构列表
	 * @param condition
	 * @return
	 */
	public List<Map<String, Object>> loadTreeData();
	
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
	 * 根据id查询级联查询
	 * @param id
	 * @param includeSelf 是否包含自己
	 * @param isSup true：向上查， false 向下查
	 * @return
	 */
	public List<Map<String, Object>> loadCascadeOrgs(String id, boolean includeSelf, boolean isSup);
	
	/**
	 * 根据用户id查询用户所在的部门
	 * @param userId
	 * @return
	 */
	Organization loadDepartmentByUserId(String userId);
	
	/**
	 * 加载用户的所属机构
	 * @param userId
	 * @return
	 */
	Map<String, Object> loadUserOrg(String userId);
	
}
