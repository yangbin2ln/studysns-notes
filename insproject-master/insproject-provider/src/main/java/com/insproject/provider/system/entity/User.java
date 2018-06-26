package com.insproject.provider.system.entity;

import java.io.Serializable;

import com.insplatform.spring.persistence.annotation.Id;
import com.insplatform.spring.persistence.annotation.Table;
import com.insplatform.spring.persistence.annotation.Transient;

@Table("sys_user")
public class User implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	/**id*/
	private Serializable id;
	/**姓名*/
	private String name;
	/**账号*/
	private String account;
	/**密码*/
	private String password;
	/**身份证号*/
	private String idCard;
	/**性别 1:男 2:女*/
	private java.lang.Integer sex = 1;
	/**手机*/
	private String phone;
	/**生日*/
	private java.sql.Timestamp birthday;
	/**备注*/
	private String remark;
	/**头像照片*/
	private String photo;
	/**1:启用 0:禁用*/
	private java.lang.Integer enabled = 1;
	/**1:删除 0:未删除*/
	private java.lang.Integer isDelete = 0;
	/**1/0 是否是机构管理员*/
	private java.lang.Integer isOrgadmin = 0;	
	/**创建人id*/
	private Serializable createUserId;	
	
	@Transient
	private Serializable departmentId;

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
	
	public void setAccount(String value) {
		this.account = value;
	}
	
	public String getAccount() {
		return this.account;
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setIdCard(String value) {
		this.idCard = value;
	}
	
	public String getIdCard() {
		return this.idCard;
	}
	
	public void setSex(java.lang.Integer value) {
		this.sex = value;
	}
	
	public java.lang.Integer getSex() {
		return this.sex;
	}
	
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setBirthday(java.sql.Timestamp value) {
		this.birthday = value;
	}
	
	public java.sql.Timestamp getBirthday() {
		return this.birthday;
	}
	
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setPhoto(String value) {
		this.photo = value;
	}
	
	public String getPhoto() {
		return this.photo;
	}
	
	public void setEnabled(java.lang.Integer value) {
		this.enabled = value;
	}
	
	public java.lang.Integer getEnabled() {
		return this.enabled;
	}
	
	public void setIsDelete(java.lang.Integer value) {
		this.isDelete = value;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	
	public void setIsOrgadmin(java.lang.Integer value) {
		this.isOrgadmin = value;
	}
	
	public java.lang.Integer getIsOrgadmin() {
		return this.isOrgadmin;
	}

	public Serializable getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Serializable createUserId) {
		this.createUserId = createUserId;
	}

	public Serializable getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Serializable departmentId) {
		this.departmentId = departmentId;
	}
	
	
	
}