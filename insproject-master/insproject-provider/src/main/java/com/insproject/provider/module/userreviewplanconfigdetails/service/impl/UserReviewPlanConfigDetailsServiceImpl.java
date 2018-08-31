package com.insproject.provider.module.userreviewplanconfigdetails.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;
import com.insproject.provider.module.common.SessionUser;
import com.insproject.provider.module.user.entity.User;
import com.insproject.provider.module.userreviewplanconfig.entity.UserReviewPlanConfig;
import com.insproject.provider.module.userreviewplanconfig.repository.UserReviewPlanConfigRepository;
import com.insproject.provider.module.userreviewplanconfigdetails.entity.UserReviewPlanConfigDetails;
import com.insproject.provider.module.userreviewplanconfigdetails.repository.UserReviewPlanConfigDetailsRepository;
import com.insproject.provider.module.userreviewplanconfigdetails.service.UserReviewPlanConfigDetailsService;

@Service("UserReviewPlanConfigDetailsServiceImpl")
public class UserReviewPlanConfigDetailsServiceImpl extends BaseServiceImpl<UserReviewPlanConfigDetails> implements UserReviewPlanConfigDetailsService{
	
	@Autowired
	@Qualifier("UserReviewPlanConfigDetailsRepositoryImpl")
	private UserReviewPlanConfigDetailsRepository userReviewPlanConfigDetailsRepository;

	@Autowired
	private UserReviewPlanConfigRepository userReviewPlanConfigRepository;

	@Override
	public BaseRepository<UserReviewPlanConfigDetails> getRepository() {		
		return userReviewPlanConfigDetailsRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return userReviewPlanConfigDetailsRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return userReviewPlanConfigDetailsRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public UserReviewPlanConfigDetails get(Serializable id) {		
		return userReviewPlanConfigDetailsRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(UserReviewPlanConfigDetails entity) {	
		return userReviewPlanConfigDetailsRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(UserReviewPlanConfigDetails entity) {		
		return userReviewPlanConfigDetailsRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return userReviewPlanConfigDetailsRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return userReviewPlanConfigDetailsRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return userReviewPlanConfigDetailsRepository.loadAllGrid(condition);
	}

	@Override
	public void createWithDefaultReviewPlanConfig() {
		//创建个人复习计划规则父记录
		UserReviewPlanConfig userReviewPlanConfig = new UserReviewPlanConfig();
		User user = SessionUser.threadLocal.get();
		userReviewPlanConfig.setUserId(user.getId());
		Serializable userReviewPlanConfigId = userReviewPlanConfigRepository.save(userReviewPlanConfig);
		
		userReviewPlanConfigDetailsRepository.createWithDefaultReviewPlanConfig(userReviewPlanConfigId);
	}


}
