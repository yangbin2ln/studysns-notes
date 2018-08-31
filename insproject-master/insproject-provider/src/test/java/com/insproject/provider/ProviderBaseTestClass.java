package com.insproject.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.insplatform.spring.jdbc.JdbcAssistant;
import com.insproject.provider.module.common.SessionUser;
import com.insproject.provider.module.user.entity.User;
import com.insproject.provider.module.userreviewplanconfig.entity.UserReviewPlanConfig;
import com.insproject.provider.module.userreviewplanconfig.repository.UserReviewPlanConfigRepository;
import com.insproject.provider.module.userreviewplanconfigdetails.service.UserReviewPlanConfigDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})  
public class ProviderBaseTestClass {
	@Autowired
	protected JdbcAssistant jdbcAssistant;	

	@Autowired
	protected UserReviewPlanConfigDetailsService userReviewPlanConfigDetailsService;	
	
	@Autowired
	protected UserReviewPlanConfigRepository userReviewPlanConfigRepository;	
	
	@Test
    public void time(){
		User user = new User();
		user.setId(1);
		SessionUser.threadLocal.set(user);
		
		UserReviewPlanConfig loadLast = userReviewPlanConfigRepository.loadLast();
		System.out.println(loadLast.getId());
	}	
	 
}
