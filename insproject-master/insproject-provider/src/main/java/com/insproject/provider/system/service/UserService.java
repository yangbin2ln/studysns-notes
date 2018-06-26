package com.insproject.provider.system.service;

import java.io.Serializable;
import java.util.Map;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.service.BaseService;
import com.insproject.provider.system.entity.User;

public interface UserService extends BaseService<User>{
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> loadList(Condition condition);
	
	/**
	 * 加载单条数据
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> load(Serializable id);
	
	/**
	 * 根据用户名和密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	User loadSysUserByAccountAndPassword(String userName, String password);	
	
	/**
	 * 检查旧密码
	 * @param account
	 * @param password
	 * @return
	 */
	boolean checkOldPwd(String account, String password);
	
	/**
	 * 修改密码
	 * @param account
	 * @param password
	 * @return
	 */
	boolean updatePwd(String account, String password);
	
	/**
	 * 检查账号是否存在
	 * @param account
	 * @param id
	 * @return
	 */
	boolean checkHasAccount(String account, String id);
	
	/**
	 * 重置密码
	 * @param selectedItems
	 */
	void updateResetPwd(String[] ids);
	
	
}
