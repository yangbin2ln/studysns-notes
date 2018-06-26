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
import com.insplatform.spring.mapper.BeanPropertyRowMapper;
import com.insplatform.spring.mapper.ValueMapper;
import com.insproject.provider.system.entity.DataAuthz;
import com.insproject.provider.system.repository.DataAuthzRepository;
import com.insproject.provider.system.repository.RoleRepository;

@Repository("DataAuthzRepositoryImpl")
public class DataAuthzRepositoryImpl extends BaseRepositoryImpl<DataAuthz> implements DataAuthzRepository{
	
	@Autowired
	private GridService grdiService;	
	
	@Autowired
	@Qualifier("RoleRepositoryImpl")
	private RoleRepository roleRepository;
	
	
	@Override
	public String getQuerySql(Condition condition) {
		String sql = "SELECT t.* FROM sys_data_authz t WHERE 1=1 ";
		return sql;
	}	

	@Override
	public Map<String, Object> loadList(Condition condition) {	
		String sql = getQuerySql(condition);
		List<Object> queryParams = new ArrayList<Object>();			
		sql += " order by order_index asc ";
		condition.pager(false);		
		if(condition.containsKey("roleId")){
			String roleDataAuthzSql = "select * from sys_data_authz_role where role_id = ?";
			List<Map<String, Object>> roleDataAuthzList = jdbcAssistant.query(roleDataAuthzSql, new Object[]{condition.get("roleId")});
			return grdiService.loadData(condition.getRequest(), sql, queryParams.toArray(), new ValueMapper() {				
				@Override
				public RETURN_CODE map(Map<String, Object> record) throws Exception {
					for(Map<String, Object> roleDataAuthz : roleDataAuthzList){
						if(roleDataAuthz.get("dataAuthzId").toString().equals(record.get("id").toString())){
							record.put("roleLevel", roleDataAuthz.get("level"));
							break;
						}
					}
					return RETURN_CODE.NEXT;
				}
			});
		}else{
			return grdiService.loadData(condition.getRequest(), sql, queryParams.toArray());
		}		
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.* FROM sys_data_authz t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
	

	@Override
	public boolean checkHasCode(String code, String id) {
		String sql = "select count(1) from sys_data_authz t where upper(t.code)='"+code.toUpperCase()+"' ";
		//如果id不为空，排除当前id的数据
		if(TextUtil.isNotEmpty(id)){
			sql += " and t.id != '"+id+"' ";		
		}
		int count = jdbcAssistant.queryAsInt(sql);
		if(count > 0) return true;
		return false;
	}

	@Override
	public DataAuthz loadDataAuthzByCode(String code) {
		String sql = "select * from sys_data_authz t where t.code = ? ";
		DataAuthz dataAuthz = jdbcAssistant.queryAsObject(
				sql, new Object[]{code}, 
				BeanPropertyRowMapper.newInstance(DataAuthz.class));		
		return dataAuthz;
	}

	@Override
	public int loadUserDataAuthzLevelByCode(String userId, String code){
		DataAuthz dataAuthz = loadDataAuthzByCode(code);
		String sql = "select * from sys_data_authz_user t where t.user_id=? and data_authz_id=? ";
		//如果单独给用户设置了此数据权限，使用给用户设置的
		Map<String, Object> authzUser = jdbcAssistant.queryOne(sql, new Object[]{userId, dataAuthz.getId()});
		if(authzUser != null){
			return (Integer)authzUser.get("level");
		}
		
		int level = dataAuthz.getDefaultLevel();
		//如果没有单独给用户设置数据权限，则去用户的角色中找，取最大的
		List<String> roleIds = roleRepository.loadUserRoleIds(userId);
		sql = "select level from sys_data_authz_role where data_authz_id = ? and role_id in " + TextUtil.tansferList2SqlString(roleIds);
		List<Integer> roleLevels = jdbcAssistant.queryAsList(sql, Integer.class, new Object[]{dataAuthz.getId()});
		for(Integer roleLevel : roleLevels){
			if(roleLevel > level){
				level = roleLevel;
			}
		}		
		return level;
	}

	@Override
	public void updateRoleDataAuthz(String roleId, int level,
			String[] dataAuthzIds) {
		//如果level为0，表示恢复默认，删除相关数据
		if(level == 0){
			String sql = "delete from sys_data_authz_role where role_id = ? and data_authz_id = ? ";
			jdbcAssistant.batchUpdate(sql, new BatchPreparedStatementSetter() {				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, roleId);
					ps.setObject(2, dataAuthzIds[i]);
				}				
				@Override
				public int getBatchSize() {					
					return dataAuthzIds.length;
				}
			});
		}else{
			for(int i=0; i<dataAuthzIds.length; i++){
				String sql = "select role_id from sys_data_authz_role where role_id = ? and data_authz_id = ? ";
				Map<String, Object> map = jdbcAssistant.queryOne(sql, new Object[]{roleId, dataAuthzIds[i]});
				if(map == null){
					sql = "insert into sys_data_authz_role values (?, ?, ? )";
					jdbcAssistant.update(sql, new Object[]{roleId, dataAuthzIds[i], level});
				}else{
					sql = "update sys_data_authz_role set level = ? where role_id = ? and data_authz_id = ?  ";
					jdbcAssistant.update(sql, new Object[]{level, roleId, dataAuthzIds[i]});
				}
			}
		}
	}
	
}
