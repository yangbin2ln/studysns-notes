package com.insproject.provider.system.repository;

import java.util.List;
import java.util.Map;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.Operate;


public interface OperateRepository extends BaseRepository<Operate>{
	
	/**
	 * 检查编码是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkHasCode(String code, String id);
	
	/**
	 * 根据菜单id进行获取
	 * @param menuId
	 * @return
	 */
	List<Map<String, Object>> loadByMenuId(String menuId);
}
