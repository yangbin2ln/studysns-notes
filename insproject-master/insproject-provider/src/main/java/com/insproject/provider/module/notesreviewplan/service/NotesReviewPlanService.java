package com.insproject.provider.module.notesreviewplan.service;

import java.util.*;


import com.insplatform.spring.baseclass.service.BaseService;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;

public interface NotesReviewPlanService extends BaseService<NotesReviewPlan>{
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(String id);
	
	Map<String, Object> loadAllGrid(Condition condition);
	
	/**
	 * 查询复习中的笔记
	 * @param condition
	 * @return
	 */
	Map<String, Object> loadReviewingGrid(Condition condition);
}
