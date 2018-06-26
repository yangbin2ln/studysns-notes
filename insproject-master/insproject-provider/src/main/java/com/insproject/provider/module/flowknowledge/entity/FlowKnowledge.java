package com.insproject.provider.module.flowknowledge.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_flow_knowledge")
public class FlowKnowledge implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private Serializable id;
	/**标题*/
	private String title;
	/**描述*/
	private String description;
	
	private Serializable notesId;

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Serializable getNotesId() {
		return notesId;
	}

	public void setNotesId(Serializable notesId) {
		this.notesId = notesId;
	}

	
	 
	
}