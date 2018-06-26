package com.insproject.provider.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.spring.jdbc.JdbcAssistant;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Log;

/**
 * 日志服务
 * @author guom
 *
 */
@Service("LogService")
public class LogService {
	
	@Autowired
	private GridService gridService;
	
	@Autowired
	private JdbcAssistant jdbcAssistant;
	
	@Autowired
	private DataAuthzService dataAuthzService;
	
	private final String DATA_AUTHZ_CODE = "DATA_AUTHZ_SYSTEM_LOG_SYS";
	
	/**
	 * 删除日志
	 * @param ids
	 * @param type
	 */
	public void deleteLog(String [] ids, String type){
		if(ids.length > 0){
			String tableName = this.getTableNameByType(type);		
			String [] sqls = new String[ids.length];
			for(int i=0; i<ids.length; i++){
				sqls[i] = "delete from " + tableName + " where id = '"+ids[i]+"' ";			
			}		
			jdbcAssistant.batchUpdate(sqls);
		}
		
	}
	
	/**
	 * 清空日志
	 * @param type
	 */
	public void deleteAllLog(String type){
		String tableName = this.getTableNameByType(type);		
		String sql = "truncate Table " + tableName;
		jdbcAssistant.update(sql);
	}
		
	/**
	 * 添加日志
	 * @param log
	 */
	public void saveLog(Log log){
		//普通日志
		if(log.getType().equals(SystemConstant.LogConstant.SYS.getType())){			
			String sql = "insert into " + SystemConstant.LogConstant.SYS.getTableName()
					+ " (create_user_id, create_user_name, create_time, ip, operate_id, operate_code, params, exec_timemillis) "
					+ " values " + " (?, ?, ?, ?, ?, ?, ?, ?)";

			jdbcAssistant.update(sql, new Object[] {log.getCreateUserId(), log.getCreateUserName(), log.getCreateTime(), log.getIp(),
					log.getOperateId(), log.getOperateCode(), log.getParams(), log.getExecTimemillis() });			
		}
		//错误日志
		else if(log.getType().equals(SystemConstant.LogConstant.ERROR.getType())){
			String sql = "insert into " + SystemConstant.LogConstant.ERROR.getTableName()
					+ " (create_user_id, create_user_name, create_time, ip, operate_id, operate_code, params, exec_timemillis, error_details) "
					+ " values " + " (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			jdbcAssistant.update(sql, new Object[] {log.getCreateUserId(), log.getCreateUserName(), log.getCreateTime(), log.getIp(),
					log.getOperateId(), log.getOperateCode(), log.getParams(), log.getExecTimemillis(), log.getErrorDetails() });
		}
		//菜单日志
		else if(log.getType().equals(SystemConstant.LogConstant.MENU.getType())){
			String sql = "insert into " + SystemConstant.LogConstant.MENU.getTableName()
					+ " (create_user_id, create_user_name, create_time, ip, menu_id, menu_name) "
					+ " values (?, ?, ?, ?, ?, ?)";
			jdbcAssistant.update(sql, new Object[] {log.getCreateUserId(), log.getCreateUserName(), log.getCreateTime(), log.getIp(),
					log.getMenuId(), log.getMenuName()});
		}	
		
	}
	
	/**
	 * 加载日志
	 * @param condition
	 * @return
	 */
	public Map<String, Object> loadAll(Condition condition, String type, String userId){
		
		Assert.notNull(type);
		String sql = "select * from (" + getSql(type) + ") T_LOG where 1=1 ";
		List<Object> queryParams = new ArrayList<Object>();
		if(condition.containsKey("menuName")){
			sql += " and menu_name like ? ";
			queryParams.add("%" + condition.get("menuName") + "%");
		}		
		if(condition.containsKey("operateName")){
			sql += " and operate_name like ? ";
			queryParams.add("%" + condition.get("operateName") + "%");
		}	
		if(condition.containsKey("createUserName")){
			sql += " and create_user_name like ? ";
			queryParams.add("%" + condition.get("createUserName") + "%");
		}
		if(condition.containsKey("account")){
			sql += " and account like ? ";
			queryParams.add("%" + condition.get("account") + "%");
		}		
		if(condition.containsKey("orgName")){
			sql += " and (department_name like ? or company_name like ?) ";
			queryParams.add("%" + condition.get("orgName") + "%");
			queryParams.add("%" + condition.get("orgName") + "%");
		}
		
		//数据权限
		sql = dataAuthzService.filter(userId, DATA_AUTHZ_CODE, sql);
		
		sql += " order by create_time desc ";
		
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}
	
	/**
	 * 获取查询sql
	 * @param type
	 * @return
	 */
	private String getSql(String type){
		String sql = null;
		if(type.equals(SystemConstant.LogConstant.SYS.getType())){
			sql = "select t.id, t.create_user_id, t.create_user_name, t.create_time, t.ip, u.account,"
					 + " t.operate_id, t.operate_code, t.params, t.exec_timemillis, "
					 + " o.name as operate_name, m.name as menu_name, "
					 + " u.department_name,u.department_id, " 
					 + " u.company_name,u.company_id "
					 + " from "+SystemConstant.LogConstant.SYS.getTableName()+" t "
					 + " left join sys_operate o on t.operate_id = o.id "
					 + " left join sys_menu m on o.menu_id = m.id "
					 + " left join v_sys_user_info u on t.create_user_id = u.id where 1=1";
		}else if(type.equals(SystemConstant.LogConstant.ERROR.getType())){
			sql = "select t.id, t.create_user_id, t.create_user_name, t.create_time, t.ip, u.account, t.error_details, "
					 + " t.operate_id, t.operate_code, t.params, t.exec_timemillis, "
					 + " o.name as operate_name, m.name as menu_name, "
					 + " u.department_name,u.department_id, " 
					 + " u.company_name,u.company_id "
					 + " from "+SystemConstant.LogConstant.ERROR.getTableName()+" t "
					 + " left join sys_operate o on t.operate_id = o.id "
					 + " left join sys_menu m on o.menu_id = m.id "
					 + " left join v_sys_user_info u on t.create_user_id = u.id where 1=1";
		}else if(type.equals(SystemConstant.LogConstant.MENU.getType())){
			sql = "select t.id,t.create_user_id,t.create_user_name,t.create_time,t.ip,t.menu_id,t.menu_name, "
					 + " u.account,u.department_name,u.department_id,u.company_name,u.company_id "
					 + " from "+SystemConstant.LogConstant.MENU.getTableName()+" t "
					 + " left join v_sys_user_info u on t.create_user_id = u.id where 1=1" ;
					
		}
		return sql;
	}
	
		
	/**
	 * 根据日志类型获取表名	
	 * @param type
	 * @return
	 */
	private String getTableNameByType(String type){
		if(type.equals(SystemConstant.LogConstant.SYS.getType())){
			return SystemConstant.LogConstant.SYS.getTableName();
		}else if(type.equals(SystemConstant.LogConstant.ERROR.getType())){
			return SystemConstant.LogConstant.ERROR.getTableName();
		}else if(type.equals(SystemConstant.LogConstant.MENU.getType())){
			return SystemConstant.LogConstant.MENU.getTableName();
		}else{
			return null;
		}		
	}
	
}
