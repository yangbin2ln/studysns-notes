package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.Menu;


public interface MenuRepository extends BaseRepository<Menu>{
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(Serializable id);
	
	/**
	 * 加载数据
	 * @param condition
	 * @return
	 */
	List<Map<String, Object>> loadSysTreeData(Map<String, Object> condition);
	
	/**
	 * 加载菜单操作数据
	 * @param condition
	 * @return
	 */
	List<Map<String, Object>> loadMgtTreeData();	
	
	/**
	 * 根据菜单id查询级联查询（菜单和功能操作）
	 * @param id
	 * @param includeSelf 是否包含自己
	 * @param isSup true：向上查， false 向下查
	 * @return
	 */
	public List<Map<String, Object>> loadCascadeMenuOpers(String id, boolean includeSelf, boolean isSup);
	
}
