package com.insproject.provider.module.notesreviewplan.repository;

import java.util.*;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;

import com.insplatform.core.http.Condition;


public interface NotesReviewPlanRepository extends BaseRepository<NotesReviewPlan>{
	
	
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
	 * 查询复习中的笔记
	 * @param condition
	 * @return
	 */
	Map<String, Object> loadReviewingGrid(Condition condition);
	
}
