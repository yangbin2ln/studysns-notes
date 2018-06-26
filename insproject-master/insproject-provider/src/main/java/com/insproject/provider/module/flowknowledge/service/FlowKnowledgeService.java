package com.insproject.provider.module.flowknowledge.service;

import java.util.*;


import com.insplatform.spring.baseclass.service.BaseService;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.flowknowledge.entity.FlowKnowledge;

public interface FlowKnowledgeService extends BaseService<FlowKnowledge>{
	
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
}
