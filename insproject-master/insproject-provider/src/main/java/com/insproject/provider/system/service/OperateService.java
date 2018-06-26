package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.Operate;

public interface OperateService extends BaseService<Operate>{
	
	/**
	 * 检查编码是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkHasCode(String code, String id);
	
	/**
	 * 加载所有的功能操作map(以code为key)
	 */
	public Map<String, Map<String, Object>> loadAllOperateMap();
	
	/**
	 * 加载所有功能操作资源集合
	 * @return
	 */
	public List<String> loadAllOperateResources();
	
	/**
	 * 加载用户的功能操作map(以code为key)
	 */
	public Map<String, Map<String, Object>> loadUserOperateMap(Serializable userId);
	
	/**
	 * 加载用户功能操作资源集合
	 * @return
	 */
	public List<String> loadUserOperateResources(Serializable userId);
 	
}
