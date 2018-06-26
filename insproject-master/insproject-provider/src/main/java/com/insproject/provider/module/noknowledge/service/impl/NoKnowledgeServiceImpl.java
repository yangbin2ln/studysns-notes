package com.insproject.provider.module.noknowledge.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.noknowledge.entity.NoKnowledge;
import com.insproject.provider.module.noknowledge.repository.NoKnowledgeRepository;
import com.insproject.provider.module.noknowledge.service.NoKnowledgeService;

@Service("NoKnowledgeServiceImpl")
public class NoKnowledgeServiceImpl extends BaseServiceImpl<NoKnowledge> implements NoKnowledgeService{
	
	@Autowired
	@Qualifier("NoKnowledgeRepositoryImpl")
	private NoKnowledgeRepository noKnowledgeRepository;

	@Override
	public BaseRepository<NoKnowledge> getRepository() {		
		return noKnowledgeRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return noKnowledgeRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return noKnowledgeRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public NoKnowledge get(Serializable id) {		
		return noKnowledgeRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NoKnowledge entity) {	
		return noKnowledgeRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NoKnowledge entity) {		
		return noKnowledgeRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return noKnowledgeRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return noKnowledgeRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return noKnowledgeRepository.loadAllGrid(condition);
	}


}
