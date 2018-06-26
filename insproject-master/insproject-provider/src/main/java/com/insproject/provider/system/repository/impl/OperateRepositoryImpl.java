package com.insproject.provider.system.repository.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.insplatform.component.service.ext.grid.GridService;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.impl.BaseRepositoryImpl;
import com.insproject.provider.system.entity.Operate;
import com.insproject.provider.system.repository.OperateRepository;

@Repository("OperateRepositoryImpl")
public class OperateRepositoryImpl extends BaseRepositoryImpl<Operate> implements OperateRepository{
	
	@Autowired
	private GridService grdiService;	
	
	
	@Override
	public boolean checkHasCode(String code, String id) {
		String sql = "select count(1) from sys_operate t where upper(t.code)='"+code.toUpperCase()+"' ";
		//如果id不为空，排除当前id的数据
		if(TextUtil.isNotEmpty(id)){
			sql += " and t.id != '"+id+"' ";		
		}
		int count = jdbcAssistant.queryAsInt(sql);
		if(count > 0) return true;
		return false;
	}

	@Override
	public List<Map<String, Object>> loadByMenuId(String menuId) {
		String sql = "select * from SYS_OPERATE where menu_id = ? ";
		return jdbcAssistant.query(sql, new Object[]{menuId});
	}

	
}
