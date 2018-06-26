package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.DictClassify;

public interface DictClassifyService extends BaseService<DictClassify>{
	
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
	 * 检查名称或编码是否重复
	 * @param name
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkNameAndCode(String name, String code, String id);
}
