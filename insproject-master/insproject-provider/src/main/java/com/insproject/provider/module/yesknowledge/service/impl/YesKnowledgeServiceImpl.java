package com.insproject.provider.module.yesknowledge.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.yesknowledge.entity.YesKnowledge;
import com.insproject.provider.module.yesknowledge.repository.YesKnowledgeRepository;
import com.insproject.provider.module.yesknowledge.service.YesKnowledgeService;

@Service("YesKnowledgeServiceImpl")
public class YesKnowledgeServiceImpl extends BaseServiceImpl<YesKnowledge> implements YesKnowledgeService{
	
	@Autowired
	@Qualifier("YesKnowledgeRepositoryImpl")
	private YesKnowledgeRepository yesKnowledgeRepository;

	@Override
	public BaseRepository<YesKnowledge> getRepository() {		
		return yesKnowledgeRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return yesKnowledgeRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return yesKnowledgeRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public YesKnowledge get(Serializable id) {		
		return yesKnowledgeRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(YesKnowledge entity) {	
		return yesKnowledgeRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(YesKnowledge entity) {		
		return yesKnowledgeRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return yesKnowledgeRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return yesKnowledgeRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return yesKnowledgeRepository.loadAllGrid(condition);
	}


}
