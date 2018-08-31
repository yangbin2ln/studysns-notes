package com.insproject.provider.module.notesreviewplanexecute.service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.notesreviewplanexecute.entity.NotesReviewPlanExecute;
import com.insproject.provider.module.notesreviewplanexecute.repository.NotesReviewPlanExecuteRepository;
import com.insproject.provider.module.notesreviewplanexecute.service.NotesReviewPlanExecuteService;

@Service("NotesReviewPlanExecuteServiceImpl")
public class NotesReviewPlanExecuteServiceImpl extends BaseServiceImpl<NotesReviewPlanExecute>
		implements NotesReviewPlanExecuteService {

	@Autowired
	@Qualifier("NotesReviewPlanExecuteRepositoryImpl")
	private NotesReviewPlanExecuteRepository notesReviewPlanExecuteRepository;

	@Override
	public BaseRepository<NotesReviewPlanExecute> getRepository() {
		return notesReviewPlanExecuteRepository;
	}

	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {
		return notesReviewPlanExecuteRepository.loadAllList(condition);
	}

	@Override
	public Map<String, Object> load(String id) {
		return notesReviewPlanExecuteRepository.load(id);
	}

	/**
	 * 重写父类get方法
	 */
	@Override
	public NotesReviewPlanExecute get(Serializable id) {
		return notesReviewPlanExecuteRepository.get(id);
	}

	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NotesReviewPlanExecute entity) {
		return notesReviewPlanExecuteRepository.save(entity);
	}

	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NotesReviewPlanExecute entity) {
		return notesReviewPlanExecuteRepository.update(entity);
	}

	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {
		return notesReviewPlanExecuteRepository.deleteById(id);
	}

	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {
		return notesReviewPlanExecuteRepository.deleteByIds(ids);
	}

	@Override
	public Map<String, Object> loadAllGrid(Condition condition) {
		return notesReviewPlanExecuteRepository.loadAllGrid(condition);
	}

	@Override
	public Boolean updateReview(Integer notesId) {
		Map<String, Object> one = notesReviewPlanExecuteRepository.loadWithCurrentPeriod(notesId);
		
		if(one == null){
			return false;
		}
		NotesReviewPlanExecute notesReviewPlanExecute = notesReviewPlanExecuteRepository.get(Integer.parseInt(one.get("id").toString()));
		notesReviewPlanExecute.setRealReviewTime(new Date());
		notesReviewPlanExecuteRepository.update(notesReviewPlanExecute);
		return true;
	}

}
