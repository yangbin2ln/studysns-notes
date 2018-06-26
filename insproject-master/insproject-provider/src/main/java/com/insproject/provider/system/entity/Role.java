package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Generator;
import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insplatform.spring.persistence.constant.Strategy;

@Table("sys_role")
public class Role implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Generator(strategy=Strategy.UUID)
	/***/
	private Serializable id;
	/***/
	private String name;
	/***/
	private String code;
	/***/
	private java.lang.Double orderIndex;
	/***/
	private String remark;
	/**0：超级管理员以外角色不可见 1：超级管理员以外角色可见*/
	private java.lang.Integer allowAuthz;

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
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setOrderIndex(java.lang.Double value) {
		this.orderIndex = value;
	}
	
	public java.lang.Double getOrderIndex() {
		return this.orderIndex;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setAllowAuthz(java.lang.Integer value) {
		this.allowAuthz = value;
	}
	
	public java.lang.Integer getAllowAuthz() {
		return this.allowAuthz;
	}
	
}