package com.insproject.provider.module.flowknowledge.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.flowknowledge.entity.FlowKnowledge;
import com.insproject.provider.module.flowknowledge.repository.FlowKnowledgeRepository;
import com.insproject.provider.module.flowknowledge.service.FlowKnowledgeService;

@Service("FlowKnowledgeServiceImpl")
public class FlowKnowledgeServiceImpl extends BaseServiceImpl<FlowKnowledge> implements FlowKnowledgeService{
	
	@Autowired
	@Qualifier("FlowKnowledgeRepositoryImpl")
	private FlowKnowledgeRepository flowKnowledgeRepository;

	@Override
	public BaseRepository<FlowKnowledge> getRepository() {		
		return flowKnowledgeRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return flowKnowledgeRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return flowKnowledgeRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public FlowKnowledge get(Serializable id) {		
		return flowKnowledgeRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(FlowKnowledge entity) {	
		return flowKnowledgeRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(FlowKnowledge entity) {		
		return flowKnowledgeRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return flowKnowledgeRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return flowKnowledgeRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return flowKnowledgeRepository.loadAllGrid(condition);
	}


}
