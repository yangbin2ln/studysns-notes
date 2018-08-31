package com.insproject.provider.module.notes.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.insplatform.core.http.Condition;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.module.flowknowledge.entity.FlowKnowledge;
import com.insproject.provider.module.flowknowledge.repository.FlowKnowledgeRepository;
import com.insproject.provider.module.noknowledge.entity.NoKnowledge;
import com.insproject.provider.module.noknowledge.repository.NoKnowledgeRepository;
import com.insproject.provider.module.notes.entity.Notes;
import com.insproject.provider.module.notes.repository.NotesRepository;
import com.insproject.provider.module.notes.service.NotesService;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.notesdetails.repository.NotesDetailsRepository;
import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;
import com.insproject.provider.module.notesreviewplan.repository.NotesReviewPlanRepository;
import com.insproject.provider.module.notesreviewplan.service.NotesReviewPlanService;
import com.insproject.provider.module.userreviewplanconfig.entity.UserReviewPlanConfig;
import com.insproject.provider.module.userreviewplanconfig.repository.UserReviewPlanConfigRepository;
import com.insproject.provider.module.yesknowledge.entity.YesKnowledge;
import com.insproject.provider.module.yesknowledge.repository.YesKnowledgeRepository;

@Service("NotesServiceImpl")
public class NotesServiceImpl extends BaseServiceImpl<Notes> implements NotesService{
	
	@Autowired
	@Qualifier("NotesRepositoryImpl")
	private NotesRepository notesRepository;
	
	@Autowired
	private NoKnowledgeRepository noKnowledgeRepository;

	@Autowired
	private NotesDetailsRepository notesDetailsRepository;
	
	@Autowired
	private YesKnowledgeRepository yesKnowledgeRepository;
	
	@Autowired
	private FlowKnowledgeRepository flowKnowledgeRepository;

	@Autowired
	private NotesReviewPlanService notesReviewPlanService;

	@Autowired
	private UserReviewPlanConfigRepository userReviewPlanConfigRepository;

	@Override
	public BaseRepository<Notes> getRepository() {		
		return notesRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return notesRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return notesRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public Notes get(Serializable id) {		
		return notesRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(Notes entity) {	
		return notesRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(Notes entity) {		
		return notesRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return notesRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return notesRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return notesRepository.loadAllGrid(condition);
	}

	@Override
	public void save(String obj) {
		Notes notes = JSON.parseObject(obj, Notes.class);
		Serializable notesId = save(notes);
		
		NotesDetails notesDetails = notes.getNotesDetails();
		notesDetails.setNotesId(notesId);
		notesDetailsRepository.save(notesDetails);
		
		List<YesKnowledge> yesKnowledgeList = notes.getYesKnowledgeList();
		for(YesKnowledge yesKnowledge: yesKnowledgeList){
			if(TextUtil.isEmpty(yesKnowledge.getName())) continue;
			yesKnowledge.setNotesId(notesId);
			yesKnowledgeRepository.save(yesKnowledge);
		}
		
		List<NoKnowledge> noKnowledgeList = notes.getNoKnowledgeList();
		for(NoKnowledge noKnowledge: noKnowledgeList){
			if(TextUtil.isEmpty(noKnowledge.getName())) continue;
			noKnowledge.setNotesId(notesId);
			noKnowledgeRepository.save(noKnowledge);
		}
		
		List<FlowKnowledge> flowKnowledgeList = notes.getFlowKnowledgeList();
		for(FlowKnowledge flowKnowledge: flowKnowledgeList){
			if(TextUtil.isEmpty(flowKnowledge.getTitle())) continue;
			flowKnowledge.setNotesId(notesId);
			flowKnowledgeRepository.save(flowKnowledge);
		}
		
		//默认加入复习计划
		NotesReviewPlan notesReviewPlan = new NotesReviewPlan();
		notesReviewPlan.setNotesId(Integer.parseInt(notesId.toString()));
		if(notes.getUserReviewPlanConfigId() == null){
			UserReviewPlanConfig userReviewPlanConfig = userReviewPlanConfigRepository.loadLast();
			notesReviewPlan.setUserReviewPlanConfigId(userReviewPlanConfig.getId());
		}else{
			notesReviewPlan.setUserReviewPlanConfigId(notes.getUserReviewPlanConfigId());
		}
		notesReviewPlanService.save(notesReviewPlan);
		
	}

}
