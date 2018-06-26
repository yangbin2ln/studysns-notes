package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insplatform.spring.persistence.annotation.Transient;

@Table("sys_organization")
public class Organization implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	/***/
	private Serializable id;
	/***/
	private String name;
	/***/
	private java.lang.Integer parentId;
	/***/
	private java.lang.Double orderIndex = 100d;
	/***/
	private String remark;
	/**1:公司 2:部门*/
	private java.lang.Integer isCompany = 0;
	/**所属公司id，如果自身为公司，就是自身，如果自身不为公司，一级一级找，新增和更新时就存入数据库了*/
	private Serializable companyId;
	//不在数据库里保存
	@Transient
	private String companyAdmin;

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
	
	public void setParentId(java.lang.Integer value) {
		this.parentId = value;
	}
	
	public java.lang.Integer getParentId() {
		return this.parentId;
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
	
	public void setIsCompany(java.lang.Integer value) {
		this.isCompany = value;
	}
	
	public java.lang.Integer getIsCompany() {
		return this.isCompany;
	}

	public Serializable getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Serializable companyId) {
		this.companyId = companyId;
	}

	public String getCompanyAdmin() {
		return companyAdmin;
	}

	public void setCompanyAdmin(String companyAdmin) {
		this.companyAdmin = companyAdmin;
	}
	
	
	
}