package com.insproject.provider.module.notesreviewplanexecute.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_notes_review_plan_execute")
public class NotesReviewPlanExecute implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private java.lang.Integer id;
	/**笔记复习计划id*/
	private java.lang.Integer notesReviewPlanId;
	/**计划复习时间*/
	private java.util.Date planReviewTime;
	/**实际复习时间*/
	private java.util.Date realReviewTime;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setNotesReviewPlanId(java.lang.Integer value) {
		this.notesReviewPlanId = value;
	}
	
	public java.lang.Integer getNotesReviewPlanId() {
		return this.notesReviewPlanId;
	}
	
	public void setPlanReviewTime(java.util.Date value) {
		this.planReviewTime = value;
	}
	
	public java.util.Date getPlanReviewTime() {
		return this.planReviewTime;
	}
	
	public void setRealReviewTime(java.util.Date value) {
		this.realReviewTime = value;
	}
	
	public java.util.Date getRealReviewTime() {
		return this.realReviewTime;
	}
	
}