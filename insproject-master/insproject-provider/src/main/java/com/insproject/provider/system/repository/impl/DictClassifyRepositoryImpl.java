package com.insproject.provider.system.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.http.Condition;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.system.entity.DictClassify;
import com.insproject.provider.system.repository.DictClassifyRepository;

@Repository("DictClassifyRepositoryImpl")
public class DictClassifyRepositoryImpl extends BaseRepositoryImpl<DictClassify> implements DictClassifyRepository{
	
	@Autowired
	private GridService gridService;	
	
	
	@Override
	public String getQuerySql(Condition condition) {
		String sql = "SELECT t.* FROM sys_dict_classify t WHERE 1=1 and is_delete=0 order by name ";
		return sql;
	}	

	@Override
	public Map<String, Object> loadList(Condition condition) {	
		String sql = getQuerySql(condition);
		List<Object> queryParams = new ArrayList<Object>();
		/* 如果不分页*/
		condition.pager(false);		
		return gridService.loadData(condition.getRequest(), sql, queryParams.toArray());
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {	
		String sql = "SELECT t.* FROM sys_dict_classify t WHERE t.id = ? ";
		return jdbcAssistant.queryOne(sql, new Object[]{id});
	}
		
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {		
		String sql = "update sys_dict_classify set is_delete = 1 where id = ?";
		return jdbcAssistant.update(sql, new Object[]{id});
	}

	@Override
	public boolean checkNameAndCode(String name, String code, String id) {
		String sql = "select count(1) from sys_dict_classify t where (t.name=? or code=?) ";
		if(TextUtil.isNotEmpty(id)){
			sql += " and id != '"+id+"' ";		
		}
		int count = jdbcAssistant.queryAsInt(sql, new Object[]{name, code});
		if(count > 0) return true;
		return false;
	}
	
	
}
