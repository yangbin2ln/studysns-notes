package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Authz;
import com.insproject.provider.system.repository.AuthzRepository;
import com.insproject.provider.system.repository.RoleRepository;

@Repository("AuthzRepositoryImpl")
public class AuthzRepositoryImpl extends BaseRepositoryImpl<Authz> implements AuthzRepository{
	
	@Autowired
	private RoleRepository roleRepository;	

	@Override
	public void addAuthzMenu(String menuId) {
		Authz authz = new Authz();		
		authz.setType(SystemConstant.SYS_AUTHZ_TYPE_MENU);
		Serializable authzId = super.save(authz);
		String sql = "insert into sys_authz_menu (authz_id, menu_id) values (?, ?)";		
		jdbcAssistant.update(sql, new Object[]{authzId, menuId});		
	}

	@Override
	public void addAuthzOperate(String operateId) {
		Authz authz = new Authz();		
		authz.setType(SystemConstant.SYS_AUTHZ_TYPE_OPERATE);
		Serializable authzId = super.save(authz);
		String sql = "insert into sys_authz_operate (authz_id, operate_id) values (?, ?)";		
		jdbcAssistant.update(sql, new Object[]{authzId, operateId});		
	}

	@Override
	public void deleteAuthzByMenuId(String menuId) {
		String sql = "delete from sys_authz where type = ? and id in "
				+ "(select authz_id from SYS_AUTHZ_MENU where menu_id = ?)";
		jdbcAssistant.update(sql, new Object[]{SystemConstant.SYS_AUTHZ_TYPE_MENU, menuId});
	}

	@Override
	public void deleteAuthzByOperateId(String operateId) {
		String sql = "delete from sys_authz where type = ? and id in "
				+ "(select authz_id from SYS_AUTHZ_OPERATE where operate_id = ?)";
		jdbcAssistant.update(sql, new Object[]{SystemConstant.SYS_AUTHZ_TYPE_OPERATE, operateId});
	}
	
	@Override
	public List<String> loadAuthzIdsByRoleId(String roleId) {
		String sql = "select authz_id from sys_role_authz where role_id = ?";
		return jdbcAssistant.queryAsList(sql, String.class, new Object[]{roleId});
	}

	@Override
	public List<String> loadUserAllAuthzIds(Serializable userId) {
		List<String> userRoleIds = roleRepository.loadUserRoleIds(userId);
		Set<String> authzIds = new HashSet<String>();
		for(String roleId : userRoleIds){
			authzIds.addAll(loadAuthzIdsByRoleId(roleId));
		}	
		List<String> finalList = new ArrayList<String>();
		finalList.addAll(authzIds);
		return finalList;
	}
	
}
