package com.insproject.provider.system.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.insplatform.core.http.Condition;
import com.insplatform.core.utils.SecurityUtil;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.baseclass.repository.BaseRepository;
import com.insplatform.spring.baseclass.service.impl.BaseServiceImpl;
import com.insplatform.spring.locale.LocaleTools;
import com.insproject.provider.system.SystemConstant;
import com.insproject.provider.system.entity.User;
import com.insproject.provider.system.repository.OrganizationRepository;
import com.insproject.provider.system.repository.UserRepository;
import com.insproject.provider.system.service.UserService;

@Service("UserServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{	
	
	@Autowired
	private LocaleTools localeTools;
	
	@Autowired
	@Qualifier("UserRepositoryImpl")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("OrganizationRepositoryImpl")
	private OrganizationRepository organizationRepository;

	@Override
	public BaseRepository<User> getRepository() {		
		return userRepository;
	}
	
	@Override
	public Map<String, Object> loadList(Condition condition) {			
		return userRepository.loadList(condition);
	}
	
	@Override
	public Map<String, Object> load(Serializable id) {			
		return userRepository.load(id);
	}	
	
	@Override
	public int[] deleteByIds(Serializable[] ids) {		
		return userRepository.deleteByIds(ids);
	}

	@Override
	public User loadSysUserByAccountAndPassword(String userName,
			String password) {		
		password = getSecurityPassword(password);
		return userRepository.loadSysUserByAccountAndPassword(userName, password);
	}

	@Override
	public boolean checkOldPwd(String account, String password) {	
		password = getSecurityPassword(password);
		return userRepository.checkOldPwd(account, password);
	}

	@Override
	public boolean updatePwd(String account, String password) {	
		password = getSecurityPassword(password);
		return userRepository.updatePwd(account, password);
	}
	
	@Override
	public Serializable save(User entity) {
		if(TextUtil.isEmpty(entity.getPassword())){
			entity.setPassword(getSecurityPassword(SystemConstant.SYS_DEFAULT_PASSWORD));
		}		
		Serializable returnValue = super.save(entity);
		//设置机构
		if(entity.getDepartmentId() != null){
			saveUserOrg(String.valueOf(returnValue), String.valueOf(entity.getDepartmentId()));		
		}			
		return returnValue;
	}	
	
	@Override
	public int update(User entity) {		
		int returnValue = userRepository.update(entity);
		//设置机构
		if(entity.getDepartmentId() != null){
			saveUserOrg(String.valueOf(entity.getId()), String.valueOf(entity.getDepartmentId()));		
		}		
		return returnValue;
	}
	
	
	
	/**
	 * 设置用户机构
	 * @param userId
	 * @param orgId
	 */
	private void saveUserOrg(String userId, String orgId){	
		if(TextUtil.isNotEmpty(userId) && TextUtil.isNotEmpty(orgId)){
			Map<String, Object> userOrg = organizationRepository.loadUserOrg(userId);
			if(userOrg!=null){
				organizationRepository.deleteOrgUser(userOrg.get("id").toString(), new String[]{userId});		
			}		
			organizationRepository.addOrgUser(orgId, new String[]{userId});		
		}		
	}
	
	@Override
	public boolean checkHasAccount(String account, String id) {		
		return userRepository.checkHasAccount(account, id);
	}
	
	/**
	 * 生成md5密码
	 * @param password
	 * @return
	 */
	private String getSecurityPassword(String password){
		password = SecurityUtil.getMD5Str(password);
		return password;
	}

	@Override
	public void updateResetPwd(String[] ids) {
		if(ids.length > 0){
			String[] sqls = new String[ids.length];
			for(int i=0; i<sqls.length; i++){
				sqls[i] = "update sys_user set password = '"+getSecurityPassword(SystemConstant.SYS_DEFAULT_PASSWORD)+"' where id = " + ids[i];
			}
			jdbcAssistant.batchUpdate(sqls);
		}		
	}
	
	
}
