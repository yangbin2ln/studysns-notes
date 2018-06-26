package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insplatform.spring.persistence.constant.DataBaseType;
import com.insproject.provider.system.entity.Organization;
import com.insproject.provider.system.repository.OrganizationRepository;

@Repository("OrganizationRepositoryImpl")
public class OrganizationRepositoryImpl extends BaseRepositoryImpl<Organization> implements OrganizationRepository{
	
	@Autowired
	private GridService grdiService;	
	
	@Override
	public List<Map<String, Object>> loadTreeData() {	
		String sql = "SELECT t.*,name as text,t.parent_id as pid,t.name as text FROM sys_organization t order by order_index ";
		return jdbcAssistant.query(sql);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.*,name as text,t.parent_id as pid FROM sys_organization t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}

	@Override
	public void addOrgUser(String id, String[] userIds) {
		String [] sqls = new String [userIds.length];
		for(int i=0; i<userIds.length; i++){			
			sqls[i] = "insert into sys_organization_user values ('"+id+"', '"+userIds[i]+"') ";			
		}			
		jdbcAssistant.batchUpdate(sqls);
	}

	@Override
	public void deleteOrgUser(String id, String[] userIds) {
		String [] sqls = new String [userIds.length];
		for(int i=0; i<userIds.length; i++){			
			sqls[i] = "delete from sys_organization_user where organization_id = '"+id+"' and user_id = '"+userIds[i]+"' ";			
		}			
		jdbcAssistant.batchUpdate(sqls);
	}
	
	@Override
	public Map<String, Object> loadUserOrg(String userId) {
		String sql = "select * from sys_organization where id = " +
				"(select organization_id from sys_organization_user where user_id = ?)";		
		return jdbcAssistant.queryOne(sql, new Object[]{userId});
	}

	@Override
	public List<Map<String, Object>> loadCascadeOrgs(String id,
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
				 " (select *,parent_id as pid "
				 + "from sys_organization "
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
	
	
	@Override
	public Organization loadDepartmentByUserId(String userId) {
		String sql = "select organization_id from sys_organization_user where user_id = ? ";
		Map<String, Object> map = jdbcAssistant.queryOne(sql, new Object[]{userId});
		if(map != null){
			return get(map.get("organizationId").toString());
		}		
		return null;
	}
	
	
	/**
	 * 根据id查询所有父节点
	 * @param id
	 * @param includeSelf 是否包含自己
	 * @return
	 */
	private List<Map<String, Object>> loadSupByIdForMySql(String id,
			boolean includeSelf, String selfId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();	
		String sql = "select *,name as text,parent_id as pid from sys_organization where id = '"+id+"'";
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
	 * 根据id查询所有子节点(菜单和功能操作)
	 * @param id
	 * @param includeSelf 是否包含自己
	 * @return
	 */
	private List<Map<String, Object>> loadChildrenByIdForMySql(String id,
			boolean includeSelf, String selfId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();			
		String sql = "select *,name as text,parent_id as pid from sys_organization where parent_id = '"+id+"'";
		List<Map<String, Object>> children = jdbcAssistant.query(sql); 	
		list.addAll(children);
		if(children.size() > 0){				
			for(Map<String, Object> child : children){
				list.addAll(loadChildrenByIdForMySql(child.get("id").toString(), includeSelf, id));
			}
		}
		//加入自己
		if(includeSelf && id.equals(selfId)){
			list.add(load(id));
		}
		return list;
	}

	


	
}
