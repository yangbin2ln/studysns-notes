package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insplatform.spring.locale.LocaleTools;
import com.insplatform.spring.mapper.BeanPropertyRowMapper;
import com.insplatform.spring.mapper.ValueMapper;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.repository.UserRepository;
import com.insproject.provider.system.service.DataAuthzService;
import com.insproject.provider.system.service.RoleService;


@Repository("UserRepositoryImpl")
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository{
	
	@Autowired
	private GridService grdiService;	
	
	@Autowired
	private LocaleTools localeTools;
	
	@Autowired
	@Qualifier("DataAuthzServiceImpl")
	private DataAuthzService dataAuthzService;
	
	@Autowired
	@Qualifier("RoleServiceImpl")
	private RoleService roleServicel;
	
	private String DATA_AUTHZ_CODE = "DATA_AUTHZ_SYSTEM_USER";
	
	@Override
	public String getQuerySql(Condition condition) {
		String sql = "SELECT t.* FROM v_sys_user_info t WHERE 1=1 and t.is_delete=0 ";
		return sql;
	}	

	@Override
	public Map<String, Object> loadList(Condition condition) {	
		
		Assert.notNull(condition.get("userId"));
		String userId = condition.get("userId").toString();		
		boolean isAdmin = roleServicel.checkUserRoleByUserIdAndRoleCode(userId, SystemConstant.SYS_ROLE_CODE_SYSADMIN);
		
		String sql = getQuerySql(condition);
		List<Object> queryParams = new ArrayList<Object>();
		//用户管理过滤查询条件
		if(condition.containsKey("name")){
			sql += " and name like ? ";
			queryParams.add("%" + condition.get("name") + "%");
		}
		if(condition.containsKey("account")){
			sql += " and account like ? ";
			queryParams.add("%" + condition.get("account") + "%");
		}		
		if(condition.containsKey("enabled")){
			sql += " and enabled = ? ";
			queryParams.add(condition.get("enabled"));
		}
		if(condition.containsKey("orgName")){
			sql += " and (department_name like ? or company_name like ?) ";
			queryParams.add("%" + condition.get("orgName") + "%");
			queryParams.add("%" + condition.get("orgName") + "%");
		}
		
		//组织结构管理时需要用到的参数
		if(condition.containsKey("orgId")){
			sql += " and department_id = ?";
			queryParams.add(condition.get("orgId"));
		}
		if(condition.containsKey("notOrg")){			
			sql += " and department_id is null ";
			if(!isAdmin){
				sql += " and create_user_id = ? ";
				queryParams.add(userId);
			}
		}	
		
		//角色管理时需要用到的参数
		if(condition.containsKey("roleId")){
			String roleId = condition.get("roleId").toString();			
			if(!roleId.equals(SystemConstant.SYS_ROLE_CODE_DEFAULT)){
				if(roleId.equals(SystemConstant.SYS_ROLE_CODE_ORGADMIN)){
					sql += " and (id in (select distinct user_id from sys_role_user where role_id = ? ) or is_orgadmin=1)";
				}else{
					sql += " and id in (select distinct user_id from sys_role_user where role_id = ?) ";
				}			
				queryParams.add(condition.get("roleId"));
			}else{
				sql += " and is_orgadmin = 0 ";
			}			
		}
		if(condition.containsKey("notRoleId")){
			sql += " and id not in (select distinct user_id from sys_role_user where role_id = ? ) ";
			queryParams.add(condition.get("notRoleId"));
		}
		
		//不是超级管理员不显示机构管理员角色的用户和admin用户
		if(!isAdmin){			
			sql += " and t.is_orgadmin=0 and t.account!='admin' ";
		}		
		
		//数据权限	
		sql = dataAuthzService.filter(userId, DATA_AUTHZ_CODE, sql);		
		
		//排序
		sql += " order by account ";		
		
		//查询并格式化生日字段
		return grdiService.loadData(condition.getRequest(), sql, queryParams.toArray(), new ValueMapper() {			
			@Override
			public RETURN_CODE map(Map<String, Object> record) throws Exception {
				if(record.get("birthday") != null){
					if(record.get("birthday") instanceof Timestamp){
						Timestamp birthday = (Timestamp) record.get("birthday");
						DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
						record.put("birthday", sdf.format(birthday));
					}
				}
				Object sex = record.get("sex");
				if(sex == null || sex.toString().equals("1")){
					record.put("sex", "男");
				}else{
					record.put("sex", "女");
				}
				return RETURN_CODE.NEXT;
			}
		});
	}
	
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.* FROM v_sys_user_info t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
			
	@Override
	public int[] deleteByIds(Serializable[] ids) {
		int [] returnValue = null;
		if(ids.length > 0){
			String [] sqls = new String[ids.length];
			for(int i=0; i<ids.length; i++){
				sqls[i] = "update sys_user set is_delete=1 where id = '"+ids[i]+"'";
			}
			returnValue = jdbcAssistant.batchUpdate(sqls);
		}
		return returnValue;
	}

	@Override
	public User loadSysUserByAccountAndPassword(String userName,
			String password) {
		Assert.notNull(userName);
		String sql = "SELECT t.* FROM SYS_USER t WHERE t.account = ? and t.password = ? and t.is_delete = 0 ";
		User sysUser = null;
		try{			
			sysUser = jdbcAssistant.queryAsObject(
						sql, new Object[]{userName, password}, 
						BeanPropertyRowMapper.newInstance(User.class));
		}catch(Exception ex){
			logger.error("Login Error!");
		}
		return sysUser;
	}

	@Override
	public boolean checkOldPwd(String account, String password) {
		String sql = "SELECT t.id FROM sys_user t WHERE t.account = ? AND t.password = ?";
		return jdbcAssistant.queryOne(sql, new Object[]{
				account, password
		})!=null;
	}

	@Override
	public boolean updatePwd(String account, String password) {
		String sql = "update sys_user set password = ? where account = ?";
		jdbcAssistant.update(sql, new Object[]{password, account});
		return true;
	}

	@Override
	public boolean checkHasAccount(String account, String id) {
		String sql = "select count(1) from sys_user t where upper(t.account)='"+account.toUpperCase()+"' ";
		//如果id不为空，排除当前id的数据
		if(TextUtil.isNotEmpty(id)){
			sql += " and t.id != '"+id+"' ";		
		}
		int count = jdbcAssistant.queryAsInt(sql);
		if(count > 0) return true;
		return false;
	}

	
}
