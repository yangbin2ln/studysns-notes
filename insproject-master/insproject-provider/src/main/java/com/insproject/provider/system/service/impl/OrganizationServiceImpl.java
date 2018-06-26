package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.insplatform.component.service.ext.tree.TreeService;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insproject.common.context.App;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.Organization;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.repository.DataAuthzRepository;
import com.insproject.provider.system.repository.OrganizationRepository;
import com.insproject.provider.system.service.OrganizationService;
import com.insproject.provider.system.service.RoleService;
import com.insproject.provider.system.service.UserService;

@Service("OrganizationServiceImpl")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService{	
	
	@Autowired
	@Qualifier("OrganizationRepositoryImpl")
	private OrganizationRepository organizationRepository;
	
	@Autowired
	@Qualifier("UserServiceImpl")
	private UserService userService;
	
	@Autowired
	private TreeService treeService;
	
	@Autowired
	@Qualifier("RoleServiceImpl")
	private RoleService roleService;
	
	@Autowired
	@Qualifier("DataAuthzRepositoryImpl")
	private DataAuthzRepository dataAuthzRepository;
	
	private String DATA_AUTHZ_CODE = "DATA_AUTHZ_SYSTEM_ORGANIZATION";

	@Override
	public BaseRepository<Organization> getRepository() {		
		return organizationRepository;
	}
	
	@Override
	public Map<String, Object> loadTree(String userId) {			
		List<Map<String, Object>> data = null;
		//判断当前用户是否为超级管理员
		boolean isAdmin = roleService.checkUserRoleByUserIdAndRoleCode(userId, SystemConstant.SYS_ROLE_CODE_SYSADMIN);
		//检查当前用户所拥有的数据权限
		int dataAuthzLevel = dataAuthzRepository.loadUserDataAuthzLevelByCode(userId, DATA_AUTHZ_CODE);
		//超级管理员查全部
		if(isAdmin || dataAuthzLevel==SystemConstant.SYS_DATA_AUTHZ_LEVEL_ALL){
			data = organizationRepository.loadTreeData();
		}
		//其它用户根据公司进行过滤
		else{			
			Organization org = null;
			if(dataAuthzLevel == SystemConstant.SYS_DATA_AUTHZ_LEVEL_DEPT 
					|| dataAuthzLevel == SystemConstant.SYS_DATA_AUTHZ_LEVEL_SELF){
				//查出用户所在部门
				org = loadDepartmentByUserId(userId);
			}else if(dataAuthzLevel == SystemConstant.SYS_DATA_AUTHZ_LEVEL_COMPANY){
				//查出用户所在公司
				org = loadCompanyByUserId(userId);
			}			
			if(org == null){
				throw new RuntimeException("Can't Find User Organization");
			}
			//查出向上的节点
			data = organizationRepository.loadCascadeOrgs(String.valueOf(org.getId()), true, true);
			//加入向下的节点
			data.addAll(organizationRepository.loadCascadeOrgs(String.valueOf(org.getId()), false, false));
		}	
		//设置图标
		for(Map<String, Object> node: data){
			if(((Integer)node.get("isCompany")).equals(1)){				
				node.put("icon", App.STATIC_PATH + "/common/image/icon/company1.png");
			}else{
				node.put("icon", App.STATIC_PATH + "/common/image/icon/dept1.png");
			}
		}
		List<Map<String, Object>> treeList = treeService.parse(data, null, true, false);	
		return treeService.bulidFinalTree(treeList);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return organizationRepository.load(id);
	}
	
	
	/**
	 * 重写父类save方法
	 */
	@Override
	public Serializable save(Organization entity) {	
		Assert.notNull(entity.getParentId());		
		Serializable returnValue = organizationRepository.save(entity);
		entity.setId(returnValue);				
		//保存公司
		saveCompany(entity);	
		//保存机构管理员
		if(entity.getIsCompany().equals(1) && TextUtil.isNotEmpty(entity.getCompanyAdmin())){
			addOrgAdmin(entity);
		}		
		return returnValue;
	}
	
	/**
	 * 重写父类update方法
	 */
	@Override
	public int update(Organization entity) {
		Assert.notNull(entity.getParentId());
		int returnValue = organizationRepository.update(entity);
		//保存公司
		saveCompany(entity);
		//保存机构管理员
		if(entity.getIsCompany().equals(1) && TextUtil.isNotEmpty(entity.getCompanyAdmin())){
			User currentOrgAdmin = loadOrgAdmin(String.valueOf(entity.getId()));
			if(currentOrgAdmin == null){
				addOrgAdmin(entity);
			}else{				
				if(currentOrgAdmin.getAccount() != entity.getCompanyAdmin()){
					currentOrgAdmin.setAccount(entity.getCompanyAdmin());
					userService.update(currentOrgAdmin);
				}
			}
		}else{
			deleteOrgAdmin(entity.getId());
		}
		return returnValue;
	}
	
	@Override
	public void addOrgUser(String id, String[] userIds) {
		organizationRepository.addOrgUser(id, userIds);
	}

	@Override
	public void deleteOrgUser(String id, String[] userIds) {
		organizationRepository.deleteOrgUser(id, userIds);
	}
	
	@Override
	public boolean checkHasAccount(String account, String id) {		
		User currentOrgAdmin = loadOrgAdmin(id);
		if(currentOrgAdmin == null){
			return userService.checkHasAccount(account, null);
		}else{			
			return userService.checkHasAccount(account, String.valueOf(currentOrgAdmin.getId()));
		}
	}
	
	@Override
	public User loadOrgAdmin(String orgId) {
		List<String> orgAdminIds = loadOrgAdminIds(orgId);
		if(orgAdminIds.size() > 0){
			return userService.get(orgAdminIds.get(0));
		}
		return null;
	}
	
	
	
	@Override
	public int deleteById(Serializable id) {
		//删除机构管理员
		deleteOrgAdmin(id);
		return super.deleteById(id);
	}
	
	@Override
	public Organization loadCompanyByUserId(String userId) {		
		Organization dept = organizationRepository.loadDepartmentByUserId(userId);
		if(dept != null && dept.getCompanyId() != null){
			return get(dept.getCompanyId());
		}
		return null;
	}

	@Override
	public Organization loadDepartmentByUserId(String userId) {
		return organizationRepository.loadDepartmentByUserId(userId);
	}
	
	@Override
	public List<Map<String, Object>> loadChildren(String orgId, boolean b) {		
		List<Map<String, Object>> children = organizationRepository.loadCascadeOrgs(orgId, b, false);
		return children;
	}


	/**
	 * 根据机构id查询公司
	 * @param orgId
	 * @return
	 */
	private Organization getCompanyByOrg(Organization org){		
		if(org.getIsCompany().equals(1) || org.getParentId() == null){
			return org;
		}else{
			return get(org.getParentId());
		}
	}	
	
	/**
	 * 保存公司
	 * @param entity
	 */
	private void saveCompany(Organization entity){		
		Organization company = getCompanyByOrg(entity);		
		entity.setCompanyId(company.getId());		
		organizationRepository.update(entity);		
	}
	
	/**
	 * 添加机构管理员
	 * @param entity
	 */
	private void addOrgAdmin(Organization entity){
		if(entity.getIsCompany().equals(1) && TextUtil.isNotEmpty(entity.getCompanyAdmin())){
			User user = new User();
			user.setAccount(entity.getCompanyAdmin());
			user.setIsOrgadmin(1);			
			user.setName("机构管理员");
			Serializable userId = userService.save(user);
			//添加机构和用户对应关系
			addOrgUser(String.valueOf(entity.getId()), new String[]{String.valueOf(userId)});
		}
	}
	
	/**
	 * 删除机构管理员
	 * @param entity
	 */
	private void deleteOrgAdmin(Serializable orgId){
		String sql = "delete from sys_user where id in " + TextUtil.tansferList2SqlString(loadOrgAdminIds(orgId));
		jdbcAssistant.update(sql);
	}
	
	/**
	 * 获取机构管理员
	 * @param orgId
	 * @return
	 */
	private List<String> loadOrgAdminIds(Serializable orgId){
		String sql = " select t.id from sys_user t "
				+ " left join sys_organization_user ou on t.id = ou.user_id "
				+ " where ou.organization_id = ? and t.is_orgadmin is not null and t.is_orgadmin = 1";
		return jdbcAssistant.queryAsList(sql, String.class, new Object[]{orgId});
	}

	@Override
	public List<Map<String, Object>> loadAuthzOrg(String deptId, String userId) {
		//判断当前用户是否为超级管理员
		boolean isAdmin = roleService.checkUserRoleByUserIdAndRoleCode(userId, SystemConstant.SYS_ROLE_CODE_SYSADMIN);
		//检查当前用户所拥有的数据权限
		int dataAuthzLevel = dataAuthzRepository.loadUserDataAuthzLevelByCode(userId, DATA_AUTHZ_CODE);
		//超级管理员查全部
		List<Map<String, Object>> data = null;
		if(isAdmin || dataAuthzLevel==SystemConstant.SYS_DATA_AUTHZ_LEVEL_ALL){
			data = organizationRepository.loadTreeData();
		}else{
			String orgId = null;
			if(dataAuthzLevel == SystemConstant.SYS_DATA_AUTHZ_LEVEL_DEPT 
					|| dataAuthzLevel == SystemConstant.SYS_DATA_AUTHZ_LEVEL_SELF){
				orgId = deptId;
			}else if(dataAuthzLevel == SystemConstant.SYS_DATA_AUTHZ_LEVEL_COMPANY){
				orgId = get(deptId).getCompanyId().toString();
			}
			if(TextUtil.isEmpty(orgId)){
				throw new RuntimeException();
			}	
			data = loadChildren(orgId, true);		
		}
		return data;
	}

	
	
	
}
