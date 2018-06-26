package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.DataAuthz;

public interface DataAuthzService extends BaseService<DataAuthz>{
	
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
	boolean checkHasCode(String code, String id);
	
	/**
	 * 更新默认级别
	 * @param id
	 * @param level
	 */
	void updateLevel(String id, String level);
	
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
	int loadUserDataAuthzLevelByCode(String userId, String code);
	
	/**
	 *  将传入的sql进行数据权限过滤，其中必须包含create_user_id、department_id、company_id字段
	 * @param userId
	 * @param dataAuthzCode
	 * @param sql
	 * @return
	 */
	String filter(String userId, String dataAuthzCode, String sql);
	
}
