package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.impl.JsonMessager;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.module.notesreviewplanexecute.service.NotesReviewPlanExecuteService;

@Controller
@RequestMapping("/notesreviewplanexecute")
public class NotesReviewPlanExecuteController extends InsBaseController{
	
	@Autowired
	private NotesReviewPlanExecuteService notesReviewPlanExecuteService;
	
 
	/**
	 * 复习了一次笔记
	 * @param notesId 笔记id
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateReview")	
	public @ResponseBody Map<String, Object> updateReview(Integer notesId, HttpServletRequest request){
		return new JsonMessager().data(notesReviewPlanExecuteService.updateReview(notesId));
	}
	
}
