package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.DataAuthz;


public interface DataAuthzRepository extends BaseRepository<DataAuthz>{
	
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
	 * 检查code是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	public boolean checkHasCode(String code, String id);
	
	/**
	 * 根据code查询
	 * @param code
	 * @return
	 */
	DataAuthz loadDataAuthzByCode(String code);
	
	/**
	 * 根据code查询用户的数据权限级别
	 * @param userId
	 * @param code
	 * @return
	 */
	public int loadUserDataAuthzLevelByCode(String userId, String code);
	
	/**
	 * 更新角色下的数据权限
	 * @param id
	 * @param level
	 * @param dataAuthzIds
	 */
	public void updateRoleDataAuthz(String roleId, int level,
			String[] dataAuthzIds);
}
