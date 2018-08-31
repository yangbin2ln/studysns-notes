package com.insproject.provider.module.userreviewplanconfig.repository;

import java.util.List;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.module.userreviewplanconfig.entity.UserReviewPlanConfig;


public interface UserReviewPlanConfigRepository extends BaseRepository<UserReviewPlanConfig>{
	
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	List<Map<String, Object>> loadAllList(Condition condition);
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(String id);
	
	Map<String, Object> loadAllGrid(Condition condition);

	/**
	 * 查询最后添加的用户复习计划配置
	 * @return
	 */
	UserReviewPlanConfig loadLast();
	
}
