package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.DictClassify;


public interface DictClassifyRepository extends BaseRepository<DictClassify>{
	
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
	 * 检查名称或编码是否重复
	 * @param name
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkNameAndCode(String name, String code, String id);
	
}
