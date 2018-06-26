package com.insproject.provider.system;


public class SystemConstant {	
		
	//权限类型
	public static final int SYS_AUTHZ_TYPE_MENU = 1;		//菜单
	public static final int SYS_AUTHZ_TYPE_OPERATE = 2;		//功能操作
	
	//菜单类型
	public static final int SYS_MENU_GROUP = 1;		//组
	public static final int SYS_MENU_MODULE = 2;	//模块	
	public static final int SYS_MENU_OPERATE = 3;	//操作	
	
	//系统角色id	
	public static final String SYS_ROLE_CODE_SYSADMIN = "sysadmin";	//系统管理员  必须在数据库中做管理
	public static final String SYS_ROLE_CODE_ORGADMIN = "orgadmin";	//机构管理员  sys_user表中is_orgadmin为1的是真实机构管理员,每个机构只有一个人，不需要在数据库中关联。。机构管理员角色也可以用数据库关联的方式分配给其它用户
	public static final String SYS_ROLE_CODE_DEFAULT = "def";	//默认角色  不需要在数据库中关联，程序会判断除了真实机构管理员以外的用户都会加入该角色	
	
	//数据权限级别
	public static final int SYS_DATA_AUTHZ_LEVEL_SELF = 1;		//自己
	public static final int SYS_DATA_AUTHZ_LEVEL_DEPT = 2;		//部门
	public static final int SYS_DATA_AUTHZ_LEVEL_COMPANY = 3;	//公司
	public static final int SYS_DATA_AUTHZ_LEVEL_ALL = 4;		//全部
	
	//默认密码
	public static final String SYS_DEFAULT_PASSWORD = "111111";		
	
	
	/**
	 * 日志
	 * @author guoming
	 *
	 */
	public enum LogConstant {		
		SYS("1", "sys_log"),
		ERROR("2", "sys_log_error"),
		MENU("3", "sys_log_menu");
		
		// 构造方法
	    private LogConstant(String type, String tableName) {
	        this.type = type;
	        this.tableName = tableName;
	    }
		
		private final String type;
		private final String tableName;
		
		
		public String getType() {
			return type;
		}
		public String getTableName() {
			return tableName;
		}		
	}
	
}
