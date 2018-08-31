package com.insproject.provider.module.notesreviewplanexecute.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.module.notesreviewplanexecute.entity.NotesReviewPlanExecute;
import com.insproject.provider.module.notesreviewplanexecute.repository.NotesReviewPlanExecuteRepository;

@Repository("NotesReviewPlanExecuteRepositoryImpl")
public class NotesReviewPlanExecuteRepositoryImpl extends BaseRepositoryImpl<NotesReviewPlanExecute>
		implements NotesReviewPlanExecuteRepository {

	@Autowired
	private GridService gridService;

	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {
		String sql = "select t.* from t_notes_review_plan_execute t ";
		return jdbcAssistant.query(sql, condition.valueArray());
	}

	@Override
	public Map<String, Object> load(String id) {
		String sql = "select t.* from t_notes_review_plan_execute t where t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[] { id });
	}

	/**
	 * 重写父类get方法
	 */
	@Override
	public NotesReviewPlanExecute get(Serializable id) {
		return super.get(id);
	}

	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NotesReviewPlanExecute entity) {
		return super.save(entity);
	}

	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NotesReviewPlanExecute entity) {
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
		String sql = "select t.* from t_notes_review_plan_execute t where 1=1";

		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}

	@Override
	public int batchInsertWithconfigId(Integer notesReviewPlanId, Integer userReviewPlanConfigId) {
		String sql = "INSERT INTO t_notes_review_plan_execute (notes_review_plan_id, plan_review_time)"
				  + " SELECT ?, FROM_UNIXTIME(unix_timestamp(now()) + urpcd.time) "
				  + " FROM t_user_review_plan_config urpc "
				  + " LEFT JOIN t_user_review_plan_config_details urpcd ON ( "
				  + " urpcd.user_review_plan_config_id = urpc.id) WHERE urpc.id = ?";
		return jdbcAssistant.update(sql, new Object[] { notesReviewPlanId, userReviewPlanConfigId });
	}

	@Override
	public Map<String, Object> loadWithCurrentPeriod(Integer notesId) {
		String sql = "SELECT nrpe.* FROM t_notes t LEFT JOIN t_notes_review_plan nrp ON (t.id = nrp.notes_id) "
				+ "LEFT JOIN t_notes_review_plan_execute nrpe ON (nrpe.notes_review_plan_id = nrp.id) "
				+ "WHERE t.id = ? and nrpe.plan_review_time <= now() "
				+ "order by nrpe.plan_review_time desc limit 0,1";
		return jdbcAssistant.queryOne(sql, new Object[notesId]);
	}

}
