package com.insproject.provider.module.yesknowledge.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_yes_knowledge")
public class YesKnowledge implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private Serializable id;
	/**笔记主键id*/
	private Serializable notesId;
	/**讲解详细强度*/
	private Integer explainDegree;
	/**知识点名称*/
	private String name;
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
	public java.lang.Integer getExplainDegree() {
		return explainDegree;
	}
	public void setExplainDegree(java.lang.Integer explainDegree) {
		this.explainDegree = explainDegree;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}