package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.system.entity.Dict;
import com.insproject.provider.system.repository.DictRepository;
import com.insproject.provider.system.service.DictService;

@Service("DictServiceImpl")
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService{	
	
	@Autowired
	@Qualifier("DictRepositoryImpl")
	private DictRepository dictRepository;

	@Override
	public BaseRepository<Dict> getRepository() {		
		return dictRepository;
	}
	
	@Override
	public Map<String, Object> loadList(Condition condition) {			
		return dictRepository.loadList(condition);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return dictRepository.load(id);
	}
		
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {	
		int [] returnValue = new int[ids.length];
		returnValue = dictRepository.deleteByIds(ids);
		return returnValue;
	}
	
	@Override
	public List<Map<String, Object>> loadDictByClassify(String code) {
		return dictRepository.loadDictByClassify(code);
	}

}
