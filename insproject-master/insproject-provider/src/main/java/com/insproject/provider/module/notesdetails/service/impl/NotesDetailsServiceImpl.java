package com.insproject.provider.module.notesdetails.service.impl;

import java.io.Serializable;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.core.http.Condition;
import com.insproject.provider.module.notesdetails.constant.ReviewPlanTimeEnum;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.notesdetails.repository.NotesDetailsRepository;
import com.insproject.provider.module.notesdetails.service.NotesDetailsService;
import com.insproject.provider.module.notesreviewplan.constant.ReviewStateEnum;

@Service("NotesDetailsServiceImpl")
public class NotesDetailsServiceImpl extends BaseServiceImpl<NotesDetails> implements NotesDetailsService{
	
	@Autowired
	@Qualifier("NotesDetailsRepositoryImpl")
	private NotesDetailsRepository notesDetailsRepository;

	@Override
	public BaseRepository<NotesDetails> getRepository() {		
		return notesDetailsRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return notesDetailsRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return notesDetailsRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public NotesDetails get(Serializable id) {		
		return notesDetailsRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(NotesDetails entity) {	
		return notesDetailsRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(NotesDetails entity) {		
		return notesDetailsRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return notesDetailsRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return notesDetailsRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return notesDetailsRepository.loadAllGrid(condition);
	}

	@Override
	public List<Map<String, Object>> loadSummaryList() {
		List<Map<String, Object>> reviewPlanList = new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> list = notesDetailsRepository.loadSummaryList();
		
		//根据遗忘规律提醒复习
		for(Map<String, Object> map: list){
			Long timeDifference = (Long) map.get("timeDifference");
			if(ReviewPlanTimeEnum.FIVE_M.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.THIRTY_M.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("fiveMState")) ){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.THIRTY_M.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.TWELVE_H.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("thirtyMState"))){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.TWELVE_H.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.ONE_D.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("twelveHState"))){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.ONE_D.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.TWO_D.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("oneDState"))){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.TWO_D.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.FOUR_D.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("twoDState"))){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.FOUR_D.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.SEVEN_D.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("fourDState"))){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.SEVEN_D.getValue() <= timeDifference && timeDifference < ReviewPlanTimeEnum.FIFTEEN_D.getValue()
					&& ReviewStateEnum.NO.getValue().equals( map.get("sevenDState"))){
				reviewPlanList.add(map);
			}else if(ReviewPlanTimeEnum.FIFTEEN_D.getValue() <= timeDifference
					&& ReviewStateEnum.NO.getValue().equals( map.get("fifteenDState"))){
				reviewPlanList.add(map);
			}
			
			
			
		}
	
		return reviewPlanList;

	}


}
