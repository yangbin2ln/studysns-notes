package com.insproject.provider.module.notesreviewplan.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;
import com.insproject.provider.module.notesreviewplan.repository.NotesReviewPlanRepository;

@Repository("NotesReviewPlanRepositoryImpl")
public class NotesReviewPlanRepositoryImpl extends BaseRepositoryImpl<NotesReviewPlan>
		implements NotesReviewPlanRepository {

	@Autowired
	private GridService gridService;

	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {
		String sql = "select t.* from t_notes_review_plan t ";
		return jdbcAssistant.query(sql, condition.valueArray());
	}

	@Override
	public Map<String, Object> load(String id) {
		String sql = "select t.* from t_notes_review_plan t where t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[] { id });
	}

	@Override
	public Map<String, Object> loadAllGrid(Condition condition) {
		List<Object> queryParams = new ArrayList<Object>();
		String sql = "select t.* from t_notes_review_plan t where 1=1";
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}

	@Override
	public Map<String, Object> loadReviewingGrid(Condition condition) {
		String sql = " select a.* from ("
				+ " select nd.title, nd.abstract_summary, nd.application_scene, nd.create_time notes_create_time, nrp.*,  "
				+ " (UNIX_TIMESTAMP(?) - UNIX_TIMESTAMP(nd.update_time)) time_difference from t_notes t "
				+ " left join t_notes_details nd on(t.id = nd.notes_id) "
				+ " inner join t_notes_review_plan nrp on(nrp.notes_id = t.id)" + " where nrp.create_time > ?) "
				+ " a  ";
		return gridService.loadData(condition.getRequest(), sql,
				new Object[] { new Date(), new Date(new Date().getTime() - (15 * 24 * 60 * 60)) });
	}

}
