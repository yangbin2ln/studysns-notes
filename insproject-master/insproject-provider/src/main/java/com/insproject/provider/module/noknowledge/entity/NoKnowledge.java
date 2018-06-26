package com.insproject.provider.module.noknowledge.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;


@Table("t_no_knowledge")
public class NoKnowledge implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private Serializable id;
	/**笔记主键id*/
	private Serializable notesId;
	/**是否已解决*/
	private Integer resolve;
	/**知识点名称*/
	private String name;
	/**描述*/
	private String description;
	/***/
	private String bookName;
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
	public Integer getResolve() {
		return resolve;
	}
	public void setResolve(Integer resolve) {
		this.resolve = resolve;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	
}