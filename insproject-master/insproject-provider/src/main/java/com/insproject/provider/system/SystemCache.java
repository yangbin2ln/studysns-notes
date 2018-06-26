package com.insproject.provider.system;

import java.util.HashMap;

public class SystemCache {

	public static final String CACHE_SYSTEM_MENU_ALL_MAP = "CACHE_SYSTEM_MENU_ALL_MAP";
	public static final String CACHE_SYSTEM_OPERATE_ALL_MAP = "CACHE_SYSTEM_OPERATE_ALL_MAP";
	public static final String CACHE_SYSTEM_OPERATE_RESOURCE_LIST = "CACHE_SYSTEM_OPERATE_RESOURCE_LIST";
	
	private static HashMap<String, Object> cacheMap = new HashMap<String, Object>(); 
	
	private SystemCache() {} 
	
	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 */
	public static void add(String key, Object value){
		cacheMap.put(key, value);
	} 
	
	public static Object get(String key){
		if(contain(key)){
			return cacheMap.get(key);
		}
		return null;		
	}
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public static void remove(String key){
		cacheMap.remove(key);
	}
	
	/**
	 * 是否包含
	 * @param key
	 * @return
	 */
	public static boolean contain(String key){
		return cacheMap.containsKey(key);
	}
	
}
