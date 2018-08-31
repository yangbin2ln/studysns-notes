package com.insproject.provider.module.notesdetails.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.notesdetails.repository.NotesDetailsRepository;
import com.insproject.provider.module.notesdetails.service.NotesDetailsService;

@Service("NotesDetailsServiceImpl")
public class NotesDetailsServiceImpl extends BaseServiceImpl<NotesDetails> implements NotesDetailsService{
	
	@Autowired
	@Qualifier("NotesDetailsRepositoryImpl")
	private NotesDetailsRepository notesDetailsRepository;

	@Override
	public BaseRepository<NotesDetails> getRepository() {		
		return notesDetailsRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return notesDetailsRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return notesDetailsRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public NotesDetails get(Serializable id) {		
		return notesDetailsRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NotesDetails entity) {	
		return notesDetailsRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NotesDetails entity) {		
		return notesDetailsRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return notesDetailsRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return notesDetailsRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return notesDetailsRepository.loadAllGrid(condition);
	}

}
