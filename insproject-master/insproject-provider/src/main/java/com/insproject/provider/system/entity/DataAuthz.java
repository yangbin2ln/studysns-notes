package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;

@Table("sys_data_authz")
public class DataAuthz implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	/***/
	private Serializable id;
	
	private String name;
	/***/
	private String code;
	
	/**
	 * 是否可修改默认级别
	 */
	private Integer isEdit = 0;
	
	/**1:自己 2:部门 3:公司 4:所有*/
	private java.lang.Integer defaultLevel;
	/***/
	private java.lang.Double orderIndex = 100d;
	
	private String remark;

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public java.lang.Integer getDefaultLevel() {
		return defaultLevel;
	}

	public void setDefaultLevel(java.lang.Integer defaultLevel) {
		this.defaultLevel = defaultLevel;
	}

	public java.lang.Double getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(java.lang.Double orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsEdit(){
		return isEdit;
	}
	
	public void setIsEdit(Integer isEdit){
		this.isEdit = isEdit;
	}
	
	
	
}