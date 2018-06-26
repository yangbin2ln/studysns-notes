package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;

@Table("sys_dict_classify")
public class DictClassify implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	/**id*/
	private Serializable id;
	/**数据字典分类名称*/
	private String name;
	/**备注*/
	private String remark;
	/**唯一编码*/
	private String code;
	/**1:已删除 0:未删除*/
	private java.lang.Integer isDelete = 0;

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
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setIsDelete(java.lang.Integer value) {
		this.isDelete = value;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
}