package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.Condition;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.module.notesreviewplan.service.NotesReviewPlanService;

@Controller
@RequestMapping("/notesreviewplan")
public class NotesReviewPlanController extends InsBaseController{
	
	@Autowired
	@Qualifier("NotesReviewPlanServiceImpl")
	private NotesReviewPlanService notesReviewPlanService;
	
	/**
	 * 新增
	 * @param dict
	 * @return
	 */
	@RequestMapping("/loadReviewingGrid")	
	public @ResponseBody Map<String, Object> add(String obj, HttpServletRequest request){
		return notesReviewPlanService.loadReviewingGrid(new Condition(request));
	}
	
}
