package com.insproject.provider.module.notesreviewplan.entity;

import java.io.Serializable;
import java.util.Date;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_notes_review_plan")
public class NotesReviewPlan implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private java.lang.Integer id;
	/**笔记id*/
	private java.lang.Integer notesId;
	/***/
	private java.util.Date lastReviewTime;
	/**加入复习的时间*/
	private java.util.Date createTime = new Date();
	/**用户复习计划配置*/
	private java.lang.Integer userReviewPlanConfigId;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setNotesId(java.lang.Integer value) {
		this.notesId = value;
	}
	
	public java.lang.Integer getNotesId() {
		return this.notesId;
	}
	
	public void setLastReviewTime(java.util.Date value) {
		this.lastReviewTime = value;
	}
	
	public java.util.Date getLastReviewTime() {
		return this.lastReviewTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setUserReviewPlanConfigId(java.lang.Integer value) {
		this.userReviewPlanConfigId = value;
	}
	
	public java.lang.Integer getUserReviewPlanConfigId() {
		return this.userReviewPlanConfigId;
	}
	
}