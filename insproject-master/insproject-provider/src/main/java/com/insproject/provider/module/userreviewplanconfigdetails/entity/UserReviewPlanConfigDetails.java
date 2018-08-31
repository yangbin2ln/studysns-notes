package com.insproject.provider.module.userreviewplanconfigdetails.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_user_review_plan_config_details")
public class UserReviewPlanConfigDetails implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private java.lang.Integer id;
	/***/
	private java.lang.Integer userReviewPlanConfigId;
	/**时间段名称*/
	private String name;
	/**复习间隔时长（秒）*/
	private java.lang.Integer time;
	/**编码*/
	private String code;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setUserReviewPlanConfigId(java.lang.Integer value) {
		this.userReviewPlanConfigId = value;
	}
	
	public java.lang.Integer getUserReviewPlanConfigId() {
		return this.userReviewPlanConfigId;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setTime(java.lang.Integer value) {
		this.time = value;
	}
	
	public java.lang.Integer getTime() {
		return this.time;
	}
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	
}