package com.insproject.provider.module.notesreviewplanexecute.repository;

import java.util.*;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.module.notesreviewplanexecute.entity.NotesReviewPlanExecute;

import com.insplatform.core.http.Condition;


public interface NotesReviewPlanExecuteRepository extends BaseRepository<NotesReviewPlanExecute>{
	
	
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
	 * 根据计划配置id批量生产复习计划明细表
	 * @param notesReviewPlanId 单个笔记复习计划id
	 * @param userReviewPlanConfigId 用户复习计划配置id
	 */
	int batchInsertWithconfigId(Integer notesReviewPlanId, Integer userReviewPlanConfigId);

	/**
	 * 根据当前时间所在时间复习计划的时间段查询复习计划执行记录
	 * @return
	 */
	NotesReviewPlanExecute loadWithCurrentPeriod(Integer notesId);
	
}
