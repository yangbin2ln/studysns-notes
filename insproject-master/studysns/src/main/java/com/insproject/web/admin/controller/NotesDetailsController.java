package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.Condition;
import com.insplatform.core.http.messager.impl.JsonMessager;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.notesdetails.service.NotesDetailsService;

@Controller
@RequestMapping("/notesdetails")
public class NotesDetailsController extends InsBaseController{
	
	@Autowired
	@Qualifier("NotesDetailsServiceImpl")
	private NotesDetailsService notesDetailsService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadAllGrid")
	public @ResponseBody Map<String, Object> loadAllGrid(
			HttpServletRequest request, HttpServletResponse response){
		Condition condition = new Condition(request);
		return notesDetailsService.loadAllGrid(condition);
	}
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadAllList")
	public @ResponseBody Map<String, Object> loadAllList(
			HttpServletRequest request, HttpServletResponse response){
		Condition condition = new Condition(request);
		JsonMessager jsonMessager = new JsonMessager();
		return jsonMessager.success().data(notesDetailsService.loadAllList(condition));
	}	
	
	
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){	
		JsonMessager jsonMessager = new JsonMessager();
		return jsonMessager.success().data(notesDetailsService.load(id));		
	}
	
	/**
	 * 新增
	 * @param dict
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(NotesDetails notesdetails, HttpServletRequest request){
		JsonMessager jsonMessager = new JsonMessager();
		notesDetailsService.save(notesdetails);
		return jsonMessager.success();
	}
	
	/**
	 * 编辑
	 * @param dict
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(
		   @RequestParam("id") String id,
		   HttpServletRequest request){
		JsonMessager jsonMessager = new JsonMessager();
		NotesDetails notesdetails = notesDetailsService.get(id);
		this.bindObject(request, notesdetails);
		notesDetailsService.update(notesdetails);
		return jsonMessager.success();
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		JsonMessager jsonMessager = new JsonMessager();
		String [] ids = this.getSelectedItems(request,null);
		notesDetailsService.deleteByIds(ids);
		return jsonMessager.success();
	}
	
	
}
