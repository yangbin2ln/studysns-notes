package com.insproject.provider.module.notesreviewplan.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;
import com.insproject.provider.module.notesreviewplan.repository.NotesReviewPlanRepository;
import com.insproject.provider.module.notesreviewplan.service.NotesReviewPlanService;
import com.insproject.provider.module.notesreviewplanexecute.repository.NotesReviewPlanExecuteRepository;

@Service("NotesReviewPlanServiceImpl")
public class NotesReviewPlanServiceImpl extends BaseServiceImpl<NotesReviewPlan> implements NotesReviewPlanService{
	
	@Autowired
	@Qualifier("NotesReviewPlanRepositoryImpl")
	private NotesReviewPlanRepository notesReviewPlanRepository;

	@Autowired
	private NotesReviewPlanExecuteRepository notesReviewPlanExecuteRepository;

	@Override
	public BaseRepository<NotesReviewPlan> getRepository() {		
		return notesReviewPlanRepository;
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return notesReviewPlanRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public NotesReviewPlan get(Serializable id) {		
		return notesReviewPlanRepository.get(id);
	}
	
	/**
	 * 创建笔记复习计划，并直接生成笔记预复习执行记录
	 */
	@Override
	public Serializable save(NotesReviewPlan entity) {	
		Serializable id = notesReviewPlanRepository.save(entity);
		Integer userReviewPlanConfigId = entity.getUserReviewPlanConfigId();
		//根据计划配置id批量生成复习计划明细记录
		return notesReviewPlanExecuteRepository.batchInsertWithconfigId(Integer.parseInt(id.toString()), userReviewPlanConfigId);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return notesReviewPlanRepository.loadAllGrid(condition);
	}

	@Override
	public Map<String, Object> loadReviewingGrid(Condition condition) {
		return notesReviewPlanRepository.loadReviewingGrid(condition);
	}


}
