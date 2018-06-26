package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;

@Table("sys_authz")
public class Authz implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	/**id*/
	private Serializable id;
	/**1:菜单 2:功能*/
	private java.lang.Integer type;

	public void setId(Serializable value) {
		this.id = value;
	}
	
	public Serializable getId() {
		return this.id;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
}