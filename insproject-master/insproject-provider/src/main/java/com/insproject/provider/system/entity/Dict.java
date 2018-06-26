package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;

@Table("sys_dict")
public class Dict implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	/***/
	private Serializable id;
	/**名称*/
	private String name;
	/**备注*/
	private String remark;
	/**排序号*/
	private java.lang.Double orderIndex = 100d;
	/**1:已删除 0:未删除*/
	private java.lang.Integer isDelete = 0;
	/**分类id*/
	private java.lang.Integer classifyId;

	public void setId(Serializable value) {
		this.id = value;
	}
	
	public Serializable getId() {
		return this.id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setOrderIndex(java.lang.Double value) {
		this.orderIndex = value;
	}
	
	public java.lang.Double getOrderIndex() {
		return this.orderIndex;
	}
	
	public void setIsDelete(java.lang.Integer value) {
		this.isDelete = value;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setClassifyId(java.lang.Integer value) {
		this.classifyId = value;
	}
	
	public java.lang.Integer getClassifyId() {
		return this.classifyId;
	}
	
}