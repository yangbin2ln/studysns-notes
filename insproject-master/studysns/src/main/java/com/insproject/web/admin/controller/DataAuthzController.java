package com.insproject.web.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.Condition;
import com.insplatform.core.http.messager.Messager;
import com.insplatform.core.utils.TextUtil;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.DataAuthz;
import com.insproject.provider.system.service.DataAuthzService;

@Controller
@RequestMapping("/system/dataauthz")
public class DataAuthzController extends InsBaseController{
	
	@Autowired
	@Qualifier("DataAuthzServiceImpl")
	private DataAuthzService dataAuthzService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadList")
	public @ResponseBody Map<String, Object> loadList(
			HttpServletRequest request, HttpServletResponse response){
		Condition condition = new Condition(request);
		//角色管理时用到的参数
		if(TextUtil.isNotEmpty(request.getParameter("roleId"))){
			condition.put("roleId", request.getParameter("roleId"));
		}
		return dataAuthzService.loadList(condition);
	}
	
	
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(dataAuthzService.load(id));		
	}
	
	/**
	 * 新增
	 * @param dataauthz
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(DataAuthz dataauthz){
		dataAuthzService.save(dataauthz);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 编辑
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(
		   @RequestParam("id") String id,
		   HttpServletRequest request){		
		DataAuthz dataauthz = dataAuthzService.get(id);
		bindObject(request, dataauthz);
		dataAuthzService.update(dataauthz);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		String [] ids = getSelectedItems(request, null);	
		dataAuthzService.deleteByIds(ids);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 更新默认级别
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateLevel")
	public @ResponseBody Map<String, Object> updateLevel(HttpServletRequest request){
		String id = request.getParameter("id");
		String level = request.getParameter("level");		
		dataAuthzService.updateLevel(id, level);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 检查code是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkHasCode")
	public @ResponseBody Map<String, Object> checkHasCode(HttpServletRequest request){
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		boolean has = dataAuthzService.checkHasCode(code, id);
		return Messager.getJsonMessager().success().put("has", has);
	}

	
}
