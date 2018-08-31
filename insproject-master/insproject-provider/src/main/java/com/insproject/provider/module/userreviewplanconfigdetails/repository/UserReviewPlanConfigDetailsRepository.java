package com.insproject.provider.module.userreviewplanconfigdetails.repository;

import java.io.Serializable;
import java.util.*;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.module.userreviewplanconfigdetails.entity.UserReviewPlanConfigDetails;

import com.insplatform.core.http.Condition;


public interface UserReviewPlanConfigDetailsRepository extends BaseRepository<UserReviewPlanConfigDetails>{
	
	
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
	 * 创建用户复习计划配置规则明细
	 * @param userReviewPlanConfigId
	 */
	void createWithDefaultReviewPlanConfig(Serializable userReviewPlanConfigId);
	
}
