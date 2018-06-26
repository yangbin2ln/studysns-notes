package com.insproject.provider.module.notesdetails.entity;

import java.io.Serializable;
import java.util.Date;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_notes_details")
public class NotesDetails implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private Serializable id;
	/**笔记主键id*/
	private Serializable notesId;
	/***/
	private java.util.Date createTime = new Date();
	/***/
	private java.util.Date updateTime = new Date();
	/***/
	private String createUserId;
	/**知识点是否已全部闭合*/
	private java.lang.Boolean knowledgeClosed;
	/**具有流程性的知识点数量*/
	private java.lang.Integer knowledgeFlowNum;
	/**已解决知识点总量*/
	private java.lang.Integer knowledgeYesNum;
	/**未解决知识点总量*/
	private java.lang.Integer knowledgeNoNum;
	/**应用场景*/
	private String applicationScene;
	/**抽取总结*/
	private String abstractSummary;
	
	/**笔记标题*/
	private String title;
	
	public Serializable getId() {
		return id;
	}
	public void setId(Serializable id) {
		this.id = id;
	}
	public Serializable getNotesId() {
		return notesId;
	}
	public void setNotesId(Serializable notesId) {
		this.notesId = notesId;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public java.lang.Boolean getKnowledgeClosed() {
		return knowledgeClosed;
	}
	public void setKnowledgeClosed(java.lang.Boolean knowledgeClosed) {
		this.knowledgeClosed = knowledgeClosed;
	}
	public java.lang.Integer getKnowledgeFlowNum() {
		return knowledgeFlowNum;
	}
	public void setKnowledgeFlowNum(java.lang.Integer knowledgeFlowNum) {
		this.knowledgeFlowNum = knowledgeFlowNum;
	}
	public java.lang.Integer getKnowledgeYesNum() {
		return knowledgeYesNum;
	}
	public void setKnowledgeYesNum(java.lang.Integer knowledgeYesNum) {
		this.knowledgeYesNum = knowledgeYesNum;
	}
	public java.lang.Integer getKnowledgeNoNum() {
		return knowledgeNoNum;
	}
	public void setKnowledgeNoNum(java.lang.Integer knowledgeNoNum) {
		this.knowledgeNoNum = knowledgeNoNum;
	}
	public String getApplicationScene() {
		return applicationScene;
	}
	public void setApplicationScene(String applicationScene) {
		this.applicationScene = applicationScene;
	}
	public String getAbstractSummary() {
		return abstractSummary;
	}
	public void setAbstractSummary(String abstractSummary) {
		this.abstractSummary = abstractSummary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
}