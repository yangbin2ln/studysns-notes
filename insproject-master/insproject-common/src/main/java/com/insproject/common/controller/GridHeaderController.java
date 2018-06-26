package com.insproject.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.Messager;
import com.insplatform.spring.jdbc.JdbcAssistant;

/**
 * 表格表头服务
 * @author xw
 *
 */
@Controller
@RequestMapping("/grid/header")	
public class GridHeaderController {
	@Autowired
	protected JdbcAssistant jdbcAssistant;	
	

	/**
	 * 保存表头
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveGridColums")	
	public @ResponseBody Map<String, Object> saveGridColums(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String key = request.getParameter("key");
		String columsJson = request.getParameter("columsJson");
		deleteGridColums(request);
		String sql = "INSERT INTO sys_user_grid_colums(user_id, grid_key, colums_json) VALUES (?, ?, ?)";
		jdbcAssistant.update(sql,new Object[]{userId,key,columsJson});
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 获取表头
	 * @param request
	 * @return
	 */
	@RequestMapping("/getGridColums")	
	public @ResponseBody Map<String, Object> getGridColums(HttpServletRequest request){
		String key = request.getParameter("key");
		String userId = request.getParameter("userId");
		String sql = "SELECT t.user_id, t.grid_key, t.colums_json FROM sys_user_grid_colums t WHERE t.grid_key = ? AND t.user_id = ?";
		return jdbcAssistant.queryOne(sql,new Object[]{key,userId});
	}
	
	/**
	 * 清除表头
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteGridColums")	
	public @ResponseBody Map<String, Object> deleteGridColums(HttpServletRequest request){
		String key = request.getParameter("key");
		String userId = request.getParameter("userId");
		String sql = "DELETE FROM sys_user_grid_colums WHERE user_id = ? AND grid_key = ?";
		int result = jdbcAssistant.update(sql,new Object[]{userId,key});
		return Messager.getJsonMessager().success().put("result", result);
	}
}
