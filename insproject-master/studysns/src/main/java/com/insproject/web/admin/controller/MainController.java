package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.Messager;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.jdbc.JdbcAssistant;
import com.insproject.common.controller.InsBaseController;
import com.insproject.web.admin.common.curruser.CurrentUser;
import com.insproject.web.admin.common.curruser.UserSession;

@Controller
public class MainController extends InsBaseController{	
	
	@Autowired
	protected JdbcAssistant jdbcAssistant;	
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){		
		return "main";
	}
	
	/**
	 * 改变主题
	 * @param request
	 */	
	@RequestMapping("/main/changeStyle")
	@ResponseBody			
	public Map<String,Object> changeStyle(HttpServletRequest request){
		String style = request.getParameter("style");
		if(TextUtil.isNotEmpty(style)){
			CurrentUser user = UserSession.getCurrentUser(request);
			updateExtSkin(style,user.getId());
			request.getSession().setAttribute("EXT_STYLE", style);
		}
		return Messager.getJsonMessager().success();
	}


	private int updateExtSkin(String extSkin, String userId) {
		String sql = "UPDATE sys_user SET  ext_skin = ? WHERE id = ?";
		return jdbcAssistant.update(sql,new Object[]{extSkin,userId});
	}
	
	private String getExtSkin(String userId) {
		String sql = "SELECT ext_skin FROM sys_user t WHERE t.id = ?";
		return jdbcAssistant.queryAsString(sql,new Object[]{userId});
	}
	
} 
