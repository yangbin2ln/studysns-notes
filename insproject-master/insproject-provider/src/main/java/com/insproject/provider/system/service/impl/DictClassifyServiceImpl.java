package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.system.entity.DictClassify;
import com.insproject.provider.system.repository.DictClassifyRepository;
import com.insproject.provider.system.service.DictClassifyService;

@Service("DictClassifyServiceImpl")
public class DictClassifyServiceImpl extends BaseServiceImpl<DictClassify> implements DictClassifyService{	
	
	@Autowired
	@Qualifier("DictClassifyRepositoryImpl")
	private DictClassifyRepository dictClassifyRepository;

	@Override
	public BaseRepository<DictClassify> getRepository() {		
		return dictClassifyRepository;
	}
	
	@Override
	public Map<String, Object> loadList(Condition condition) {			
		return dictClassifyRepository.loadList(condition);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return dictClassifyRepository.load(id);
	}	
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {	
		int returnValue = dictClassifyRepository.deleteById(id);
		return returnValue;
	}

	@Override
	public boolean checkNameAndCode(String name, String code, String id) {
		return dictClassifyRepository.checkNameAndCode(name, code, id);
	}	
	

}
