package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.common.context.App;
import com.insproject.provider.system.entity.Role;
import com.insproject.provider.system.repository.DataAuthzRepository;
import com.insproject.provider.system.repository.RoleRepository;
import com.insproject.provider.system.service.RoleService;

@Service("RoleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{	
	
	@Autowired
	@Qualifier("RoleRepositoryImpl")
	private RoleRepository roleRepository;
	
	@Autowired
	@Qualifier("DataAuthzRepositoryImpl")
	private DataAuthzRepository dataAuthzRepository;

	@Override
	public BaseRepository<Role> getRepository() {		
		return roleRepository;
	}
	
	@Override
	public Map<String, Object> loadList(Condition condition) {			
		Map<String, Object> map = roleRepository.loadList(condition);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
		for(Map<String, Object> record : data){
			record.put("text", record.get("name"));
			int allowAuthz = (Integer)record.get("allowAuthz");
			if(allowAuthz == 0){
				record.put("icon", App.STATIC_PATH + "/common/image/icon/group_error.png");
			}else{
				record.put("icon", App.STATIC_PATH + "/common/image/icon/group.png");
			}
		}
		map.put("data", data);
		return map;
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return roleRepository.load(id);
	}
	
	/**
	 * 重写父类get方法
	 */
	@Override
	public Role get(Serializable id) {		
		return roleRepository.get(id);
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(Role entity) {	
		Serializable returnValue = roleRepository.save(entity);
		return returnValue;
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(Role entity) {
		int returnValue = roleRepository.update(entity);
		return returnValue;
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {	
		int returnValue = roleRepository.deleteById(id);
		return returnValue;
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {	
		int [] returnValue = new int[ids.length];
		returnValue = roleRepository.deleteByIds(ids);
		return returnValue;
	}

	@Override
	public boolean checkUserRoleByUserIdAndRoleCode(Serializable userId,
			String roleCode) {		
		return roleRepository.checkUserRoleByUserIdAndRoleCode(userId, roleCode);
	}

	@Override
	public void addRoleUser(String roleId, String [] userIds) {
		roleRepository.addRoleUser(roleId, userIds);
	}

	@Override
	public void deleteRoleUser(String id, String [] userIds) {
		roleRepository.deleteRoleUser(id, userIds);
	}

	@Override
	public boolean checkNameAndCode(String name, String code, String id) {
		return roleRepository.checkNameAndCode(name, code, id);
	}

	@Override
	public void updateRoleAuthz(String roleId, String [] authzIds) {
		//先删除此角色所有权限
		roleRepository.deleteRoleAuthz(roleId, null);
		//后添加
		roleRepository.addRoleAuthz(roleId, authzIds);
	}

	@Override
	public void updateRoleDataAuthz(String id, int level,
			String[] dataAuthzIds) {
		dataAuthzRepository.updateRoleDataAuthz(id, level, dataAuthzIds);		
	}

}
