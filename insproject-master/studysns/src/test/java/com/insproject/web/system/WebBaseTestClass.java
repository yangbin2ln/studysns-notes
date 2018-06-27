package com.insproject.web.system;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.insplatform.spring.jdbc.JdbcAssistant;

 


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})  
public class WebBaseTestClass {
	@Autowired
	protected JdbcAssistant jdbcAssistant;	
	
	@Test
    public void time(){
    	String sql = "select t.update_time - ? from t_notes_details t ";
    	List<Map<String, Object>> list = jdbcAssistant.query(sql, new Object[]{new Date()});
    	System.out.println(list);
    }	
}
