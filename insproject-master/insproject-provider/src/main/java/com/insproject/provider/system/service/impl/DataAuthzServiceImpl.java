package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.DataAuthz;
import com.insproject.provider.system.repository.DataAuthzRepository;
import com.insproject.provider.system.repository.RoleRepository;
import com.insproject.provider.system.service.DataAuthzService;

@Service("DataAuthzServiceImpl")
public class DataAuthzServiceImpl extends BaseServiceImpl<DataAuthz> implements DataAuthzService{	
	
	@Autowired
	@Qualifier("DataAuthzRepositoryImpl")
	private DataAuthzRepository dataAuthzRepository;
	
	@Autowired
	@Qualifier("RoleRepositoryImpl")
	private RoleRepository roleRepository;


	@Override
	public BaseRepository<DataAuthz> getRepository() {		
		return dataAuthzRepository;
	}
	
	@Override
	public Map<String, Object> loadList(Condition condition) {			
		return dataAuthzRepository.loadList(condition);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return dataAuthzRepository.load(id);
	}	
	

	@Override
	public boolean checkHasCode(String code, String id) {		
		return dataAuthzRepository.checkHasCode(code, id);
	}

	@Override
	public void updateLevel(String id, String level) {
		Assert.notNull(id);
		Assert.notNull(level);
		DataAuthz entity = get(id);
		entity.setDefaultLevel(Integer.valueOf(level));
		update(entity);
	}
	
	@Override
	public DataAuthz loadDataAuthzByCode(String code) {
		return dataAuthzRepository.loadDataAuthzByCode(code);
	}
	
	@Override
	public int loadUserDataAuthzLevelByCode(String userId, String code) {		
		return dataAuthzRepository.loadUserDataAuthzLevelByCode(userId, code);
	}
	

	@Override
	public String filter(String userId, String dataAuthzCode, String sql) {		
		boolean isAdmin = roleRepository.checkUserisSysAdmin(userId);
		if(!isAdmin){
			int level = loadUserDataAuthzLevelByCode(userId, dataAuthzCode);			
			switch (level) {
			case SystemConstant.SYS_DATA_AUTHZ_LEVEL_SELF:	
				sql = filterSelf(userId, sql);
				break;
			case SystemConstant.SYS_DATA_AUTHZ_LEVEL_DEPT:					
				sql = filterDept(userId, sql);
				break;	
			case SystemConstant.SYS_DATA_AUTHZ_LEVEL_COMPANY:
				sql = filterCompany(userId, sql);					
				break;	
			case SystemConstant.SYS_DATA_AUTHZ_LEVEL_ALL:			
				break;	
			default:
				throw new IllegalArgumentException("Error Level");
			}
		}
		return sql;
	}

	private String filterSelf(String userId, String sql){
		sql = "select * from ( " +sql+ " ) T_DATA_AUTHZ where create_user_id = '"+userId+"' and create_user_id is not null";
		return sql;
	}
	
	private String filterDept(String userId, String sql){
		sql =  "select * from ( " +sql+ " ) T_DATA_AUTHZ where (department_id in "
				+ "(select organization_id from sys_organization_user where user_id = '"+userId+"')"
				+ " and department_id is not null) or (create_user_id = '"+userId+"' and create_user_id is not null)";
		return sql;
	}
	
	private String filterCompany(String userId, String sql){
		sql = "select * from ( " +sql+ " ) T_DATA_AUTHZ where (company_id in "
				+ " (select o.company_id from sys_organization_user ou "
				+ " left join sys_organization o on ou.organization_id = o.id "
				+ " where ou.user_id = '"+userId+"') "
						+ " and company_id is not null) or (create_user_id = '"+userId+"' and create_user_id is not null)";	
		return sql;
	}
}
