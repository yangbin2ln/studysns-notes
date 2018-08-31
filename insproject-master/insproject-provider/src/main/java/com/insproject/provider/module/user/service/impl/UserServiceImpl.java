package com.insproject.provider.module.user.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.module.user.entity.User;
import com.insproject.provider.module.user.repository.UserRepository;
import com.insproject.provider.module.user.service.UserService;

@Service("WebUserServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public BaseRepository<User> getRepository() {		
		return userRepository;
	}
	
	@Override
	public List<Map<String, Object>> loadAllList(Condition condition) {			
		return userRepository.loadAllList(condition);
	}
	
	@Override
	public Map<String, Object> load(String id) {			
		return userRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public User get(Serializable id) {		
		return userRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(User entity) {	
		return userRepository.save(entity);
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(User entity) {		
		return userRepository.update(entity);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		return userRepository.deleteById(id);
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return userRepository.deleteByIds(ids);
	}
	
	@Override
	public  Map<String, Object> loadAllGrid(Condition condition) {
		return userRepository.loadAllGrid(condition);
	}


}
