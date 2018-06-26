package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insplatform.spring.persistence.constant.DataBaseType;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Menu;
import com.insproject.provider.system.repository.MenuRepository;

@Repository("MenuRepositoryImpl")
public class MenuRepositoryImpl extends BaseRepositoryImpl<Menu> implements MenuRepository{
	
	@Autowired
	private GridService grdiService;	
	
	@Override
	public List<Map<String, Object>> loadSysTreeData(Map<String, Object> condition) {		
		//直接查询V_SYS_AUTHZ_MENUS视图
		String sql = "select t.*, t.name as text, t.parent_id as pid from V_SYS_AUTHZ_MENUS t where 1=1 ";		
		if(condition != null){
			//如果有是否启用，则过滤
			if(condition.containsKey("enabled")){
				sql += " and t.enabled = '"+condition.get("enabled")+"'";
			}	
			//如果有权限过滤,则进行过滤
			if(condition.containsKey("authzIds")){		
				@SuppressWarnings("unchecked")
				List<String> authzIds = (List<String>) condition.get("authzIds");
				sql += "and t.authz_id in " + TextUtil.tansferList2SqlString(authzIds);				
			}
		}		
		sql += "order by order_index asc";
		return jdbcAssistant.query(sql);
	}
	
	@Override
	public List<Map<String, Object>> loadMgtTreeData() {
		String sql = "select v.*,v.icon as view_icon from V_SYS_AUTHZ_MENUS_OPERATES v where 1=1 order by order_index asc";		
		return jdbcAssistant.query(sql);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.* FROM sys_menu t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> loadCascadeMenuOpers(String id,
			boolean includeSelf, boolean isSup) {
		List<Map<String,Object>> list = null;
		if(jdbcAssistant.getDatabaseType() == DataBaseType.ORACLE){
			String includeSelfSql = "";
			String isSupSql = "";
			if(!includeSelf) {
				includeSelfSql = " where id != '"+id+"'";
			}
			if(isSup){
				isSupSql = " pid = id ";
			}else{
				isSupSql = " id = pid ";
			}
			String sql = " select * from "+
				 " (select id,type "
				 + "from V_SYS_AUTHZ_MENUS_OPERATES "
				 + "start with id = '"+id+"' connect by prior "+isSupSql+") " + includeSelfSql;
			list = jdbcAssistant.query(sql);
		}else if(jdbcAssistant.getDatabaseType() == DataBaseType.MYSQL){
			if(isSup){
				list = loadSupByIdForMySql(id, includeSelf, id);
			}else{
				list = loadChildrenByIdForMySql(id, includeSelf, id);
			}			
		}
		return list;
	}
	
	/**
	 * 根据菜单id查询所有父节点
	 * @param id
	 * @param includeSelf 是否包含自己
	 * @return
	 */
	private List<Map<String, Object>> loadSupByIdForMySql(String id, boolean includeSelf, String selfId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		String sql = "select id,type,pid from V_SYS_AUTHZ_MENUS_OPERATES where id = '"+id+"'";
		List<Map<String, Object>> sup = jdbcAssistant.query(sql);
		if(includeSelf){
			list.addAll(sup);
		}else{
			if(!selfId.equals(id)){
				list.addAll(sup);
			}
		}	
		if(sup.size() > 0 && sup.get(0).get("pid") != null){
			list.addAll(loadSupByIdForMySql(sup.get(0).get("pid").toString(), includeSelf, selfId));			
		}				
		return list;
	}
	
	/**
	 * 根据菜单id查询所有子节点(菜单和功能操作)
	 * @param id
	 * @param includeSelf 是否包含自己
	 * @return
	 */
	private List<Map<String, Object>> loadChildrenByIdForMySql(String id, boolean includeSelf, String selfId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();			
		String sql = "select id,type from V_SYS_AUTHZ_MENUS_OPERATES where pid = '"+id+"'";
		List<Map<String, Object>> children = jdbcAssistant.query(sql); 	
		list.addAll(children);
		if(children.size() > 0){				
			for(Map<String, Object> child : children){
				if(Integer.parseInt(child.get("type").toString())!=SystemConstant.SYS_MENU_OPERATE){
					list.addAll(loadChildrenByIdForMySql(child.get("id").toString(), includeSelf, id));
				}					
			}
		}
		//加入自己
		if(includeSelf && id.equals(selfId)){
			sql = "select id,type,pid from V_SYS_AUTHZ_MENUS_OPERATES where id = '"+id+"'";
			list.add(jdbcAssistant.queryOne(sql));
		}
		return list;
	}
	
}
