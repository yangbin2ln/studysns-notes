package com.insproject.provider.module.userreviewplanconfigdetails.repository.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.module.userreviewplanconfigdetails.repository.UserReviewPlanConfigDetailsRepository;
import com.insproject.provider.module.userreviewplanconfigdetails.entity.UserReviewPlanConfigDetails;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;


@Repository("UserReviewPlanConfigDetailsRepositoryImpl")
public class UserReviewPlanConfigDetailsRepositoryImpl extends BaseRepositoryImpl<UserReviewPlanConfigDetails> implements UserReviewPlanConfigDetailsRepository{
	
	@Autowired
	private GridService gridService;
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {
		List<Object> queryParams = new ArrayList<Object>();
		String sql = "select t.* from t_user_review_plan_config_details t ";
		if(condition.containsKey("userReviewPlanConfigId")){
			queryParams.add(condition.get("userReviewPlanConfigId"));
			sql += " and t.user_review_plan_config_id = ?";
		}
		return jdbcAssistant.query(sql, queryParams.toArray());
	}
	
	@Override
	public Map<String, Object> load(String id) {	
		String sql = "select t.* from t_user_review_plan_config_details t where t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public UserReviewPlanConfigDetails get(Serializable id) {		
		return super.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(UserReviewPlanConfigDetails entity) {	
		return super.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(UserReviewPlanConfigDetails entity) {		
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
		String sql = "select t.* from t_user_review_plan_config_details t where 1=1";
		 
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}

	@Override
	public void createWithDefaultReviewPlanConfig(Serializable userReviewPlanConfigId) {
		String sql = "insert into t_user_review_plan_config_details(user_review_plan_config_id,name,time,code)"
				   + " select ?,name, time,code from base_config_review_plan";
		jdbcAssistant.update(sql, new Object[]{userReviewPlanConfigId});
		
	}
	
}
