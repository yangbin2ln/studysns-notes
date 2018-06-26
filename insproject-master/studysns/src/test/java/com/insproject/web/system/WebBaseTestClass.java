package com.insproject.web.system;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:applicationServlet.xml"})  
public class WebBaseTestClass {

	protected MockHttpServletRequest request;  
	protected MockHttpServletResponse response;  
	  
    @Before  
    public void before()  
    {  
        request = new MockHttpServletRequest();  
        response = new MockHttpServletResponse();  
        request.setCharacterEncoding("UTF-8");  
    }  

   
    
}
