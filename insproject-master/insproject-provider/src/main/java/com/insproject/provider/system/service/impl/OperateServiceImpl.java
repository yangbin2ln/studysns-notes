package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.provider.system.SystemCache;
import com.insproject.provider.system.entity.Operate;
import com.insproject.provider.system.repository.AuthzRepository;
import com.insproject.provider.system.repository.OperateRepository;
import com.insproject.provider.system.repository.RoleRepository;
import com.insproject.provider.system.service.OperateService;

@Service("OperateServiceImpl")
public class OperateServiceImpl extends BaseServiceImpl<Operate> implements OperateService{	
	
	@Autowired
	@Qualifier("OperateRepositoryImpl")
	private OperateRepository operateRepository;
	
	@Autowired
	@Qualifier("AuthzRepositoryImpl")
	private AuthzRepository authzRepository;
	
	@Autowired
	@Qualifier("RoleRepositoryImpl")
	private RoleRepository roleRepository;

	@Override
	public BaseRepository<Operate> getRepository() {		
		return operateRepository;
	}
	
	@Override
	public boolean checkHasCode(String code, String id) {		
		return operateRepository.checkHasCode(code, id);
	}
	
	/**
	 * 重写父类deleteById方法
	 */
	@Override
	public int deleteById(Serializable id) {
		//删除权限
		authzRepository.deleteAuthzByOperateId((String)id);
		//删除操作
		int returnValue =  super.deleteById(id);	
		//清除缓存中的所有操作列表
		SystemCache.remove(SystemCache.CACHE_SYSTEM_OPERATE_ALL_MAP);
		SystemCache.remove(SystemCache.CACHE_SYSTEM_OPERATE_RESOURCE_LIST);
		return returnValue;
	}
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(Operate entity) {		
		//保存操作
		String id = (String) super.save(entity);
		//保存权限
		authzRepository.addAuthzOperate(id);
		//清除缓存中的所有操作列表
		SystemCache.remove(SystemCache.CACHE_SYSTEM_OPERATE_ALL_MAP);
		SystemCache.remove(SystemCache.CACHE_SYSTEM_OPERATE_RESOURCE_LIST);
		return id;
	}
	
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(Operate entity) {
		int returnValue = super.update(entity);
		//清除缓存中的所有操作列表
		SystemCache.remove(SystemCache.CACHE_SYSTEM_OPERATE_ALL_MAP);
		SystemCache.remove(SystemCache.CACHE_SYSTEM_OPERATE_RESOURCE_LIST);
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Map<String, Object>> loadAllOperateMap() {		
		Map<String, Map<String, Object>> map = null;
		if(SystemCache.contain(SystemCache.CACHE_SYSTEM_OPERATE_ALL_MAP)){			
			map = 
				(Map<String, Map<String, Object>>) SystemCache.get(SystemCache.CACHE_SYSTEM_OPERATE_ALL_MAP);
		}else{
			String sql = "select v.* from v_sys_authz_operates v";			
			map = getOperateMap(sql);
			SystemCache.add(SystemCache.CACHE_SYSTEM_OPERATE_ALL_MAP, map);
		}		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadAllOperateResources() {
		List<String> list = null;
		if(SystemCache.contain(SystemCache.CACHE_SYSTEM_OPERATE_RESOURCE_LIST)){
			list = 
				(List<String>) SystemCache.get(SystemCache.CACHE_SYSTEM_OPERATE_RESOURCE_LIST);
		}else{			
			Map<String, Map<String, Object>> operateMap = loadAllOperateMap();
			list = getOperateResourcesByMap(operateMap);
			SystemCache.add(SystemCache.CACHE_SYSTEM_OPERATE_RESOURCE_LIST, list);
		}
		return list;
	}

	@Override
	public Map<String, Map<String, Object>> loadUserOperateMap(Serializable userId) {
		Map<String, Map<String, Object>> map = null;
		boolean isAdmin = roleRepository.checkUserisSysAdmin(userId);
		if(isAdmin){
			map = loadAllOperateMap();
		}else{
			List<String> authzIds = authzRepository.loadUserAllAuthzIds(userId);
			String sql = "select v.* from v_sys_authz_operates v where v.enabled=1 and v.authz_id in " + TextUtil.tansferList2SqlString(authzIds);
			map = getOperateMap(sql);
		}
		return map;
	}

	@Override
	public List<String> loadUserOperateResources(Serializable userId) {
		Map<String, Map<String, Object>> operateMap = loadUserOperateMap(userId);
		List<String> list = getOperateResourcesByMap(operateMap);
		return list;
	}
	
	
	
	/**
	 * 根据sql获取操作map
	 * @param sql
	 * @return
	 */
	private Map<String, Map<String, Object>> getOperateMap(String sql){
		List<Map<String, Object>> list = jdbcAssistant.query(sql);
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for(Map<String, Object> item : list){
			if(item.get("code") != null){				
				map.put(item.get("code").toString(), item);
			}
		}
		return map;
	}
	
	/**
	 * 根据操作map获取操作资源集合
	 * @return
	 */
	private List<String> getOperateResourcesByMap(Map<String, Map<String, Object>> map){
		List<String> list = new ArrayList<String>();		
		Set<String> keys = map.keySet();
		for(String key : keys){
			Map<String, Object> operate = map.get(key);
			String operUrls = operate.get("resourcePath").toString();
			if(operUrls.indexOf(",") == -1){
				list.add(operUrls);
			}else{
				for(String operUrl : operUrls.split(",")){
					list.add(operUrl);
				}
			}				
		}
		return list;
	}
	

}
