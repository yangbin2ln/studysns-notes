package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.impl.JsonMessager;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.module.notes.entity.Notes;
import com.insproject.provider.module.notes.service.NotesService;

@Controller
@RequestMapping("/notes")
public class NotesController extends InsBaseController{
	
	@Autowired
	@Qualifier("NotesServiceImpl")
	private NotesService notesService;
	
	/**
	 * 新增
	 * @param dict
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(String obj, HttpServletRequest request){
		JsonMessager jsonMessager = new JsonMessager();
		notesService.save(obj);
		return jsonMessager.success();
	}
	
}
