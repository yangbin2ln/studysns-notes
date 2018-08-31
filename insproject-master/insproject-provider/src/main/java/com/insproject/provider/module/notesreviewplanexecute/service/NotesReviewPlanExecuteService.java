package com.insproject.provider.module.notesreviewplanexecute.service;

import java.util.*;


import com.insplatform.spring.baseclass.service.BaseService;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.notesreviewplanexecute.entity.NotesReviewPlanExecute;

public interface NotesReviewPlanExecuteService extends BaseService<NotesReviewPlanExecute>{
	
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
	 * 记录一次笔记复习（自动计算当前复习所在复习计划中的时间段，并进行状态更新）
	 * @param notesId 笔记id
	 * @return
	 */
	Boolean updateReview(Integer notesId);
}
