package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.Dict;


public interface DictRepository extends BaseRepository<Dict>{
	
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
	 * 根据分类id加载数据字典
	 * @param classifyId
	 * @return
	 */
	List<Map<String, Object>> loadDictByClassify(String code);
	
}
