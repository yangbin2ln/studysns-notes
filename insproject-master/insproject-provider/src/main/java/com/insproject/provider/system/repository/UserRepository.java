package com.insproject.provider.system.repository;

import java.io.Serializable;
import java.util.*;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insproject.provider.system.entity.User;


public interface UserRepository extends BaseRepository<User>{
	
	/**
	 * 获取查询sql语句
	 * @return
	 */
	public String getQuerySql(Condition condition);
	
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
	public User loadSysUserByAccountAndPassword(String userName,
			String password);	
	
	/**
	 * 检查旧密码
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean checkOldPwd(String account, String password);
	
	/**
	 * 修改密码
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean updatePwd(String account, String password);
	
	/**
	 * 检查账号是否存在
	 * @param account
	 * @param id
	 * @return
	 */
	public boolean checkHasAccount(String account, String id);
	
	
	
}
