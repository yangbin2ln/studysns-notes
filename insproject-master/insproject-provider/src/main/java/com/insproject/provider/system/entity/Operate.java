package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Generator;
import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insplatform.spring.persistence.constant.Strategy;

@Table("sys_operate")
public class Operate implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Generator(strategy=Strategy.UUID)
	/**id*/
	private Serializable id;
	/**唯一标示编码*/
	private String code;	
	/**操作描述*/
	private String name;	
	/**1:启用 0:禁用*/
	private java.lang.Integer enabled;
	/**排序号*/
	private java.lang.Double orderIndex = 100d;
	/**对应后台的url地址,多个地址用逗号分隔*/
	private String resourcePath;
	/**外键，对应菜单表的id*/
	private String menuId;
	/**备注*/
	private String remark;
	/**1/0 是否允许将此操作授权给角色或用户*/
	private java.lang.Integer allowAuthz = 1;
	/**1/0 是否记录日志*/
	private java.lang.Integer isLog = 0;

	public void setId(Serializable value) {
		this.id = value;
	}
	
	public Serializable getId() {
		return this.id;
	}
	
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
	public void setEnabled(java.lang.Integer value) {
		this.enabled = value;
	}
	
	public java.lang.Integer getEnabled() {
		return this.enabled;
	}
	
	public void setOrderIndex(java.lang.Double value) {
		this.orderIndex = value;
	}
	
	public java.lang.Double getOrderIndex() {
		return this.orderIndex;
	}
	
	public void setResourcePath(String value) {
		this.resourcePath = value;
	}
	
	public String getResourcePath() {
		return this.resourcePath;
	}
	
	public void setMenuId(String value) {
		this.menuId = value;
	}
	
	public String getMenuId() {
		return this.menuId;
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
	
	public void setIsLog(java.lang.Integer value) {
		this.isLog = value;
	}
	
	public java.lang.Integer getIsLog() {
		return this.isLog;
	}
	
}