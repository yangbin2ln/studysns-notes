package com.insproject.provider.module.notesreviewplan.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;
import com.insproject.provider.module.notesreviewplan.repository.NotesReviewPlanRepository;
import com.insproject.provider.module.notesreviewplan.service.NotesReviewPlanService;

@Service("NotesReviewPlanServiceImpl")
public class NotesReviewPlanServiceImpl extends BaseServiceImpl<NotesReviewPlan> implements NotesReviewPlanService{
	
	@Autowired
	@Qualifier("NotesReviewPlanRepositoryImpl")
	private NotesReviewPlanRepository notesReviewPlanRepository;

	@Override
	public BaseRepository<NotesReviewPlan> getRepository() {		
		return notesReviewPlanRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return notesReviewPlanRepository.loadAllList(condition);
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
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NotesReviewPlan entity) {	
		return notesReviewPlanRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NotesReviewPlan entity) {		
		return notesReviewPlanRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return notesReviewPlanRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return notesReviewPlanRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return notesReviewPlanRepository.loadAllGrid(condition);
	}


}
