package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.system.entity.Dict;
import com.insproject.provider.system.repository.DictRepository;

@Repository("DictRepositoryImpl")
public class DictRepositoryImpl extends BaseRepositoryImpl<Dict> implements DictRepository{
	
	@Autowired
	private GridService gridService;	
	
	
	@Override
	public String getQuerySql(Condition condition) {
		String sql = "SELECT t.* FROM sys_dict t WHERE 1=1 and is_delete=0 ";
		return sql;
	}	

	@Override
	public Map<String, Object> loadList(Condition condition) {	
		String sql = getQuerySql(condition);
		List<Object> queryParams = new ArrayList<Object>();
		if(condition.containsKey("classifyId")){
			sql += " and t.classify_id = ? ";
			queryParams.add(condition.get("classifyId"));
		}
		if(condition.containsKey("name")){
			sql += " and t.name like ? ";
			queryParams.add(condition.get("%" + condition.get("name") + "%"));
		}
		/* 如果不分页
		condition.pager(false);
		*/
		sql += " order by order_index ";
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.* FROM sys_dict t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
	
	/**
	 * 重写父类deleteByIds方法
	 */
	@Override
	public int[] deleteByIds(Serializable[] ids) {	
		final Serializable[] fids = ids;
		String sql = "update sys_dict set is_delete=1 where id = ?";		
		return jdbcAssistant.batchUpdate(sql, new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				 ps.setObject(1, fids[i]);  			
			}			
			@Override
			public int getBatchSize() {
				return fids.length;
			}
		});
	}

	@Override
	public List<Map<String, Object>> loadDictByClassify(String code) {
		String sql = "select t.*,t.name as text,t.id as value from sys_dict t " +
				" where t.is_delete = 0 and t.classify_id = " +
				" (select id from sys_dict_classify where code = ?) " +
				" order by t.order_index";
		return jdbcAssistant.query(sql, new Object[]{code});
	}
	
}
