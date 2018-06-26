package com.insproject.web.admin.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insplatform.core.http.messager.Messager;
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.Operate;
import com.insproject.provider.system.service.OperateService;

@Controller
@RequestMapping("/system/operate")
public class OperateController extends InsBaseController{
	
	@Autowired
	@Qualifier("OperateServiceImpl")
	private OperateService operateService;
	
	
	
	/**
	 * 检查编码是否存在
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("/checkHasCode")
	public @ResponseBody Map<String, Object> checkHasCode(HttpServletRequest request){
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		boolean has = operateService.checkHasCode(code, id);
		return Messager.getJsonMessager().success().put("has", has);
	}
	
	/**
	 * 删除
	 * @param menu
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(HttpServletRequest request){
		String id = request.getParameter("id");
		operateService.deleteById(id);		
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 添加
	 * @param menu
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(Operate operate, HttpServletRequest request){				
		operateService.save(operate);		
		return  Messager.getJsonMessager().success();
	}
	
	/**
	 * 编辑
	 * @param menu
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(HttpServletRequest request){
		String id = request.getParameter("id");
		Operate operate = operateService.get(id);
		bindObject(request, operate);
		operateService.update(operate);			
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 加载单条数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(HttpServletRequest request){
		String id = request.getParameter("id");
		return  Messager.getJsonMessager().success().data(operateService.get(id));
	}
	
	/**
	 * 加载所有功能操作map，key:code value:object
	 * @return
	 */
	@RequestMapping("loadAllOperateMap")
	public @ResponseBody Map<String, Map<String, Object>> loadAllOperateMap(){
		return operateService.loadAllOperateMap();
	}
	
	
}
