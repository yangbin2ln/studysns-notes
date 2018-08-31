package com.insproject.provider.module.userreviewplanconfigdetails.service;

import java.util.*;


import com.insplatform.spring.baseclass.service.BaseService;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.userreviewplanconfigdetails.entity.UserReviewPlanConfigDetails;

public interface UserReviewPlanConfigDetailsService extends BaseService<UserReviewPlanConfigDetails>{
	
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
	 * 使用系统默认配置创建个人复习计划规则
	 */
	void createWithDefaultReviewPlanConfig();
}
