package com.insproject.provider.module.notesdetails.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.notesdetails.repository.NotesDetailsRepository;


@Repository("NotesDetailsRepositoryImpl")
public class NotesDetailsRepositoryImpl extends BaseRepositoryImpl<NotesDetails> implements NotesDetailsRepository{
	
	@Autowired
	private GridService gridService;
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {	
		String sql = "select t.* from t_notes_details t ";
		return jdbcAssistant.query(sql, condition.valueArray());
	}
	
	@Override
	public Map<String, Object> load(String id) {	
		String sql = "select t.* from t_notes_details t where t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public NotesDetails get(Serializable id) {		
		return super.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NotesDetails entity) {	
		return super.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NotesDetails entity) {		
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
		String sql = "select t.* from t_notes_details t where 1=1";
		 
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}

}
