package com.insproject.provider.module.notes.entity;

import java.io.Serializable;
import java.util.List;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insplatform.spring.persistence.annotation.Transient;
import com.insproject.provider.module.flowknowledge.entity.FlowKnowledge;
import com.insproject.provider.module.noknowledge.entity.NoKnowledge;
import com.insproject.provider.module.notesdetails.entity.NotesDetails;
import com.insproject.provider.module.yesknowledge.entity.YesKnowledge;


@Table("t_notes")
public class Notes implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/***/
	@Id
	private Serializable id;
	/**笔记内容*/
	private String content;
	
	@Transient
	private NotesDetails notesDetails;
	
	@Transient
	private List<YesKnowledge> yesKnowledgeList;

	@Transient
	private List<NoKnowledge> noKnowledgeList;
	
	@Transient
	private List<FlowKnowledge> flowKnowledgeList;

	/**
	 * 用户复习计划配置id
	 */
	@Transient
	private Integer userReviewPlanConfigId;
	
	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}

	public NotesDetails getNotesDetails() {
		return notesDetails;
	}

	public void setNotesDetails(NotesDetails notesDetails) {
		this.notesDetails = notesDetails;
	}

	public List<YesKnowledge> getYesKnowledgeList() {
		return yesKnowledgeList;
	}

	public void setYesKnowledgeList(List<YesKnowledge> yesKnowledgeList) {
		this.yesKnowledgeList = yesKnowledgeList;
	}

	public List<NoKnowledge> getNoKnowledgeList() {
		return noKnowledgeList;
	}

	public void setNoKnowledgeList(List<NoKnowledge> noKnowledgeList) {
		this.noKnowledgeList = noKnowledgeList;
	}

	public List<FlowKnowledge> getFlowKnowledgeList() {
		return flowKnowledgeList;
	}

	public void setFlowKnowledgeList(List<FlowKnowledge> flowKnowledgeList) {
		this.flowKnowledgeList = flowKnowledgeList;
	}

	public Integer getUserReviewPlanConfigId() {
		return userReviewPlanConfigId;
	}

	public void setUserReviewPlanConfigId(Integer userReviewPlanConfigId) {
		this.userReviewPlanConfigId = userReviewPlanConfigId;
	}
	
}