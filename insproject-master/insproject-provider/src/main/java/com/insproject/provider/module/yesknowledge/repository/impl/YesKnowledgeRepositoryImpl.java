package com.insproject.provider.module.yesknowledge.repository.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.module.yesknowledge.repository.YesKnowledgeRepository;
import com.insproject.provider.module.yesknowledge.entity.YesKnowledge;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;


@Repository("YesKnowledgeRepositoryImpl")
public class YesKnowledgeRepositoryImpl extends BaseRepositoryImpl<YesKnowledge> implements YesKnowledgeRepository{
	
	@Autowired
	private GridService gridService;
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {	
		List<Object> queryParams = new ArrayList<Object>();
		String sql = "select t.* from t_yes_knowledge t where 1=1 ";
		if(condition.containsKey("notesId")){
			sql += " and t.notes_id = ?";
			queryParams.add(condition.get("notesId"));
		}
		return jdbcAssistant.query(sql, queryParams.toArray());
	}
	
	@Override
	public Map<String, Object> load(String id) {	
		String sql = "select t.* from t_yes_knowledge t where t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public YesKnowledge get(Serializable id) {		
		return super.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(YesKnowledge entity) {	
		return super.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(YesKnowledge entity) {		
		return super.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return super.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return super.deleteByIds(ids);
	}
	
	@Override
	public Map<String, Object> loadAllGrid(Condition condition) {
		List<Object> queryParams = new ArrayList<Object>();
		String sql = "select t.* from t_yes_knowledge t where 1=1";
		 
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}
	
}
