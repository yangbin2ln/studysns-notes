package com.insproject.provider.module.userreviewplanconfig.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;

import com.insproject.provider.module.userreviewplanconfig.entity.UserReviewPlanConfig;
import com.insproject.provider.module.userreviewplanconfig.repository.UserReviewPlanConfigRepository;
import com.insproject.provider.module.userreviewplanconfig.service.UserReviewPlanConfigService;

@Service("UserReviewPlanConfigServiceImpl")
public class UserReviewPlanConfigServiceImpl extends BaseServiceImpl<UserReviewPlanConfig> implements UserReviewPlanConfigService{
	
	@Autowired
	@Qualifier("UserReviewPlanConfigRepositoryImpl")
	private UserReviewPlanConfigRepository userReviewPlanConfigRepository;

	@Override
	public BaseRepository<UserReviewPlanConfig> getRepository() {		
		return userReviewPlanConfigRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return userReviewPlanConfigRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return userReviewPlanConfigRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public UserReviewPlanConfig get(Serializable id) {		
		return userReviewPlanConfigRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(UserReviewPlanConfig entity) {	
		return userReviewPlanConfigRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(UserReviewPlanConfig entity) {		
		return userReviewPlanConfigRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return userReviewPlanConfigRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return userReviewPlanConfigRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return userReviewPlanConfigRepository.loadAllGrid(condition);
	}


}
