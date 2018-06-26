package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Generator;
import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insplatform.spring.persistence.constant.Strategy;

@Table("sys_menu")
public class Menu implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Generator(strategy=Strategy.UUID)
	/**id*/
	private Serializable id;
	/**菜单名称*/
	private String name;
	/**父id*/
	private String parentId;
	/**1:启用 0:禁用*/
	private java.lang.Integer enabled = 1;
	/**排序号*/
	private java.lang.Double orderIndex = 100d;
	/**图标*/
	private String icon;
	/**资源路径*/
	private String resourcePath;
	/**备注*/
	private String remark;
	/**1:组 2:模块*/
	private java.lang.Integer type = 1;
	/**1:允许被授权 0:不允许被授权*/
	private java.lang.Integer allowAuthz = 1;

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
	
	public void setParentId(String value) {
		this.parentId = value;
	}
	
	public String getParentId() {
		return this.parentId;
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
	
	public void setIcon(String value) {
		this.icon = value;
	}
	
	public String getIcon() {
		return this.icon;
	}
	
	public void setResourcePath(String value) {
		this.resourcePath = value;
	}
	
	public String getResourcePath() {
		return this.resourcePath;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setAllowAuthz(java.lang.Integer value) {
		this.allowAuthz = value;
	}
	
	public java.lang.Integer getAllowAuthz() {
		return this.allowAuthz;
	}
	
}