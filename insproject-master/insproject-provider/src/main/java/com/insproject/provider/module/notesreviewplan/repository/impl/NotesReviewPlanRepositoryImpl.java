package com.insproject.provider.module.notesreviewplan.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.core.utils.DateUtil;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insplatform.spring.mapper.ValueMapper;
import com.insproject.provider.module.flowknowledge.service.FlowKnowledgeService;
import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;
import com.insproject.provider.module.notesreviewplan.repository.NotesReviewPlanRepository;
import com.insproject.provider.module.notesreviewplanexecute.service.NotesReviewPlanExecuteService;
import com.insproject.provider.module.userreviewplanconfig.service.UserReviewPlanConfigService;
import com.insproject.provider.module.userreviewplanconfigdetails.service.UserReviewPlanConfigDetailsService;
import com.insproject.provider.module.yesknowledge.entity.YesKnowledge;
import com.insproject.provider.module.yesknowledge.service.YesKnowledgeService;

@Repository("NotesReviewPlanRepositoryImpl")
public class NotesReviewPlanRepositoryImpl extends BaseRepositoryImpl<NotesReviewPlan>
		implements NotesReviewPlanRepository {

	@Autowired
	private GridService gridService;
	
	@Autowired
	private NotesReviewPlanExecuteService notesReviewPlanExecuteService;

	@Autowired
	private YesKnowledgeService yesKnowledgeService;

	@Autowired
	private FlowKnowledgeService flowKnowledgeService;

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
				+ " (UNIX_TIMESTAMP(?) - UNIX_TIMESTAMP(nrp.create_time)) time_difference from t_notes t "
				+ " left join t_notes_details nd on(t.id = nd.notes_id) "
				+ " inner join t_notes_review_plan nrp on(nrp.notes_id = t.id)" + " where nrp.create_time > ?) "
				+ " a  ";
		return gridService.loadData(condition.getRequest(), sql,
				new Object[] { new Date(), new Date(new Date().getTime() - (15 * 24 * 60 * 60 * 1000)) }, new ValueMapper(){

					@Override
					public RETURN_CODE map(Map<String, Object> record) throws Exception {
						//查询复习计划执行记录
						Condition condition2 = new Condition(condition.getRequest());
						condition2.put("notesReviewPlanId", record.get("id"));
						List<Map<String, Object>> notesReviewPlanExecuteList = notesReviewPlanExecuteService.loadAllList(condition2);
						
						record.put("notesReviewPlanExecuteList", notesReviewPlanExecuteList);
						record.put("nowTime", DateUtil.dateToString(new Date(), DateUtil.yyyyMMddHHmmss));
						
						//查询知识点列表
						condition2 = new Condition(condition.getRequest());
						condition2.put("notesId", record.get("notesId"));
						List<Map<String, Object>> yesKnowledgeList = yesKnowledgeService.loadAllList(condition2);
						record.put("yesKnowledgeList", yesKnowledgeList);
						
						//查询流程性知识点
						List<Map<String, Object>> flowKnowledgeList = flowKnowledgeService.loadAllList(condition2);
						record.put("flowKnowledgeList", flowKnowledgeList);
						
						return RETURN_CODE.NEXT;
					}
			
		});
	}

}
