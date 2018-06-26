package com.insproject.web.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 复习计划
 * @author yangbin
 *
 */
@Controller
@RequestMapping("reviewPlan")
public class ReviewPlanController {
	
	@RequestMapping("noReviewNotes")
	public String getNotReviewNotes(){
		return "review-plan";
	}
}
