package com.insproject.provider.module.notesreviewplan.entity;

import java.io.Serializable;
import java.util.Date;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insproject.provider.module.notesreviewplan.constant.ReviewStateEnum;


/**
 * @author Administrator
 *
 */
@Table("t_notes_review_plan")
public class NotesReviewPlan implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private java.lang.Integer id;
	/***/
	private Serializable notesId;
	/***/
	private java.lang.Integer fiveMState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer thirtyMState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer twelveHState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer oneDState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer twoDState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer fourDState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer sevenDState = ReviewStateEnum.NO.getValue();
	/***/
	private java.lang.Integer fifteenDState = ReviewStateEnum.NO.getValue();
	
	/**
	 * 最后一次复习时间
	 */
	private Date lastReviewTime;
	
	/**
	 * 加入复习计划时间
	 * */
	private Date createTime = new Date();
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setNotesId(Serializable value) {
		this.notesId = value;
	}
	
	public Serializable getNotesId() {
		return this.notesId;
	}
	
	public void setFiveMState(java.lang.Integer value) {
		this.fiveMState = value;
	}
	
	public java.lang.Integer getFiveMState() {
		return this.fiveMState;
	}
	
	public void setThirtyMState(java.lang.Integer value) {
		this.thirtyMState = value;
	}
	
	public java.lang.Integer getThirtyMState() {
		return this.thirtyMState;
	}
	
	public void setTwelveHState(java.lang.Integer value) {
		this.twelveHState = value;
	}
	
	public java.lang.Integer getTwelveHState() {
		return this.twelveHState;
	}
	
	public void setOneDState(java.lang.Integer value) {
		this.oneDState = value;
	}
	
	public java.lang.Integer getOneDState() {
		return this.oneDState;
	}
	
	public void setTwoDState(java.lang.Integer value) {
		this.twoDState = value;
	}
	
	public java.lang.Integer getTwoDState() {
		return this.twoDState;
	}
	
	public void setFourDState(java.lang.Integer value) {
		this.fourDState = value;
	}
	
	public java.lang.Integer getFourDState() {
		return this.fourDState;
	}
	
	public void setSevenDState(java.lang.Integer value) {
		this.sevenDState = value;
	}
	
	public java.lang.Integer getSevenDState() {
		return this.sevenDState;
	}
	
	public void setFifteenDState(java.lang.Integer value) {
		this.fifteenDState = value;
	}
	
	public java.lang.Integer getFifteenDState() {
		return this.fifteenDState;
	}

	public Date getLastReviewTime() {
		return lastReviewTime;
	}

	public void setLastReviewTime(Date lastReviewTime) {
		this.lastReviewTime = lastReviewTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}