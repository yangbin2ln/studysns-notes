package com.insproject.provider;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.insplatform.spring.jdbc.JdbcAssistant;
import com.insproject.provider.module.notesreviewplan.constant.ReviewStateEnum;
import com.insproject.provider.module.notesreviewplan.entity.NotesReviewPlan;
import com.insproject.provider.module.notesreviewplan.repository.NotesReviewPlanRepository;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})  
public class ProviderBaseTestClass {
	@Autowired
	protected JdbcAssistant jdbcAssistant;	

	@Autowired
	protected NotesReviewPlanRepository notesReviewPlanRepository;	
	
	@Test
    public void time(){
		NotesReviewPlan entity = new NotesReviewPlan();
		entity.setFifteenDState(ReviewStateEnum.YES.getValue());
		notesReviewPlanRepository.save(entity);

	}	
	/*	@Test
		public void time(){
			String sql = "select UNIX_TIMESTAMP(?) - UNIX_TIMESTAMP(t.update_time) as time_difference from t_notes_details t ";
			List<Map<String, Object>> list = jdbcAssistant.query(sql, new Object[]{new Date()});
			System.out.println(list);
		}	
	*/
}
