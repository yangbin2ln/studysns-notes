package com.insproject.provider.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;


public class Log implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Serializable id;	
	private Serializable createUserId;	
	private String createUserName;	
	private java.sql.Timestamp createTime = new Timestamp(System.currentTimeMillis());	
	private String ip;	
	
	private String type;	//1:普通日志， 2:错误日志  3:菜单访问日志
	
	private Serializable operateId;	
	private String operateCode;	
	private String params;	
	private java.lang.Long execTimemillis;
	
	private Integer isLog = 0;
	
	private String errorDetails;
	
	private String menuId;
	private String menuName;
	
	
	public Serializable getId() {
		return id;
	}
	public void setId(Serializable id) {
		this.id = id;
	}
	public Serializable getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Serializable createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Serializable getOperateId() {
		return operateId;
	}
	public void setOperateId(Serializable operateId) {
		this.operateId = operateId;
	}
	public String getOperateCode() {
		return operateCode;
	}
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public java.lang.Long getExecTimemillis() {
		return execTimemillis;
	}
	public void setExecTimemillis(java.lang.Long execTimemillis) {
		this.execTimemillis = execTimemillis;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Integer getIsLog(){
		return isLog;
	}
	public void setIsLog(Integer isLog){
		this.isLog = isLog;
	}
	
	
	
}