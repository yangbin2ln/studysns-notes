package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Role;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.repository.RoleRepository;
import com.insproject.provider.system.repository.UserRepository;

@Repository("RoleRepositoryImpl")
public class RoleRepositoryImpl extends BaseRepositoryImpl<Role> implements RoleRepository{
	
	@Autowired
	private GridService grdiService;	
	
	@Autowired
	@Qualifier("UserRepositoryImpl")
	private UserRepository UserRepository;	
	
	@Override
	public String getQuerySql(Condition condition) {
		String sql = "SELECT t.* FROM sys_role t WHERE 1=1 ";
		return sql;
	}	

	@Override
	public Map<String, Object> loadList(Condition condition) {	
		String sql = getQuerySql(condition);
		List<Object> queryParams = new ArrayList<Object>();		
		if(condition.containsKey("userId")){
			String userId = condition.get("userId").toString();
			boolean isAdmin = checkUserisSysAdmin(userId);
			if(!isAdmin){
				sql += " and allow_authz = 1 ";
			}
		}
		condition.pager(false);
		sql += " order by order_index ";
		return grdiService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.* FROM sys_role t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}	
	
	@Override
	public boolean checkUserRoleByUserIdAndRoleCode(Serializable userId,
			String roleCode) {
		String sql = "select count(1) from "
				+ " sys_role_user where "
				+ " role_id = (select id from sys_role where code = ?) and user_id = ? ";
		int count = jdbcAssistant.queryAsInt(sql, new Object[]{roleCode, userId});
		if(count > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean checkUserisSysAdmin(Serializable userId) {		
		return checkUserRoleByUserIdAndRoleCode(userId, SystemConstant.SYS_ROLE_CODE_SYSADMIN);
	}

	@Override
	public List<String> loadUserRoleIds(Serializable userId) {			
		String sql = "select t.id from sys_role_user r " +
				" left join SYS_ROLE t on r.role_id = t.id " +
				" where r.user_id = ? " +
				" order by t.order_index ";
		List<String> roleIds = jdbcAssistant.queryAsList(sql, String.class, new Object[]{userId});	
		//如果用户是机构管理员，则加入机构管理员角色
		User user = UserRepository.get(userId);
		if(user.getIsOrgadmin() != null && user.getIsOrgadmin().equals(1)){
			Map<String, Object> orgAdmin = loadRoleByCode(SystemConstant.SYS_ROLE_CODE_ORGADMIN);
			if(orgAdmin != null){
				roleIds.add(orgAdmin.get("id").toString());
			}
		}
		//加入默认角色
		Map<String, Object> def = loadRoleByCode(SystemConstant.SYS_ROLE_CODE_DEFAULT);
		if(def != null){
			roleIds.add(def.get("id").toString());
		}
		return roleIds;
	}

	@Override
	public Map<String, Object> loadRoleByCode(String code) {
		String sql = "select t.* from sys_role t where t.code = ? ";		
		return jdbcAssistant.queryOne(sql, new Object[]{code});
	}

	@Override
	public void addRoleUser(final String roleId, final String [] userIds) {
		String sql = "insert into sys_role_user values (?, ? )";
		jdbcAssistant.batchUpdate(sql, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, roleId);
				ps.setObject(2, userIds[i]);
			}			
			@Override
			public int getBatchSize() {			
				return userIds.length;
			}
		});		
	}

	@Override
	public void deleteRoleUser(String id, String [] userIds) {
		String sql = "delete from sys_role_user where role_id = '"+id+"' ";
		//如果用户id存在，则删除相关用户
		if(userIds!= null && userIds.length > 0){
			String[] sqls = new String[userIds.length];
			for(int i=0; i<userIds.length; i++){
				sqls[i] = sql + " and user_id = '"+userIds[i]+"' ";				
			}
			jdbcAssistant.batchUpdate(sqls);
		}
		//用户id不存在，删除此角色的所有用户
		else{			
			jdbcAssistant.update(sql);
		}			
	}

	@Override
	public boolean checkNameAndCode(String name, String code, String id) {
		String sql = "select count(1) from sys_role t where (t.name=? or t.code=?) ";
		//如果id不为空，排除当前id的数据
		if(TextUtil.isNotEmpty(id)){
			sql += " and t.id != '"+id+"' ";		
		}
		int count = jdbcAssistant.queryAsInt(sql, new Object[]{name, code});
		if(count > 0) return true;
		return false;
	}

	@Override
	public void addRoleAuthz(String roleId, String[] authzIds) {
		String sql = "insert into sys_role_authz values (?, ? )";
		jdbcAssistant.batchUpdate(sql, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setObject(1, roleId);
				ps.setObject(2, authzIds[i]);
			}			
			@Override
			public int getBatchSize() {			
				return authzIds.length;
			}
		});
	}

	@Override
	public void deleteRoleAuthz(String roleId, String[] authzIds) {
		String sql = "delete from sys_role_authz where role_id = '"+roleId+"' ";
		//如果权限id存在，则删除相关权限
		if(authzIds != null && authzIds.length > 0){
			String[] sqls = new String[authzIds.length];
			for(int i=0; i<authzIds.length; i++){
				sqls[i] = sql + " and authz_id = '"+authzIds[i]+"' ";				
			}
			jdbcAssistant.batchUpdate(sqls);
		}
		//如果权限id不存在，删除此角色的所有权限
		else{			
			jdbcAssistant.update(sql);
		}
	}

	
	
	
	
}
