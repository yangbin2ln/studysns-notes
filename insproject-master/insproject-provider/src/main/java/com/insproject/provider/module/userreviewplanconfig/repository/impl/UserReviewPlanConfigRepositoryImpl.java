package com.insproject.provider.module.userreviewplanconfig.repository.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insplatform.spring.mapper.BeanPropertyRowMapper;
import com.insproject.provider.module.userreviewplanconfig.repository.UserReviewPlanConfigRepository;
import com.insproject.provider.module.common.SessionUser;
import com.insproject.provider.module.userreviewplanconfig.entity.UserReviewPlanConfig;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;

@Repository("UserReviewPlanConfigRepositoryImpl")
public class UserReviewPlanConfigRepositoryImpl extends BaseRepositoryImpl<UserReviewPlanConfig>
		implements UserReviewPlanConfigRepository {

	@Autowired
	private GridService gridService;

	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {
		String sql = "select t.* from t_user_review_plan_config t ";
		return jdbcAssistant.query(sql, condition.valueArray());
	}

	@Override
	public Map<String, Object> load(String id) {
		String sql = "select t.* from t_user_review_plan_config t where t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[] { id });
	}

	/**
	 * 重写父类get方法
	 */
	@Override
	public UserReviewPlanConfig get(Serializable id) {
		return super.get(id);
	}

	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(UserReviewPlanConfig entity) {
		return super.save(entity);
	}

	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(UserReviewPlanConfig entity) {
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
		String sql = "select t.* from t_user_review_plan_config t where 1=1";

		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}

	@Override
	public UserReviewPlanConfig loadLast() {
		String sql = "select * from t_user_review_plan_config where user_id = ? order by create_time desc limit 0,1";
		System.out.println(SessionUser.threadLocal.get().getId());
		return jdbcAssistant.queryAsObject(sql, new Object[] { SessionUser.threadLocal.get().getId() },
				BeanPropertyRowMapper.newInstance(getEntityClass()));
	}

}
