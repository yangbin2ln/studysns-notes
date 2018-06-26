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
import com.insproject.common.controller.InsBaseController;
import com.insproject.provider.system.entity.DictClassify;
import com.insproject.provider.system.service.DictClassifyService;



@Controller
@RequestMapping("/system/dict/classify")
public class DictClassifyController extends InsBaseController{
	
	@Autowired
	@Qualifier("DictClassifyServiceImpl")
	private DictClassifyService dictClassifyService;
	
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
		return dictClassifyService.loadList(condition);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(dictClassifyService.get(id));		
	}
	
	/**
	 * 新增
	 * @param dict
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(com.insproject.provider.system.entity.DictClassify dictclassify){
		dictClassifyService.save(dictclassify);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 编辑
	 * @param dict
	 * @return
	 */
	@RequestMapping("/update")	
	public @ResponseBody Map<String, Object> update(
		   @RequestParam("id") String id,
		   HttpServletRequest request){		
		DictClassify dictclassify = dictClassifyService.get(id);
		this.bindObject(request, dictclassify);
		dictClassifyService.update(dictclassify);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")	
	public @ResponseBody Map<String, Object> delete(@RequestParam("id") String id){
		dictClassifyService.deleteById(id);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 检查名称或编码是否重复
	 * @param name
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkNameAndCode")
	public @ResponseBody Map<String, Object> checkNameAndCode(
			@RequestParam("name") String name,
			@RequestParam("code") String code,
			@RequestParam(value="id", required=false) String id){
		boolean has = dictClassifyService.checkNameAndCode(name, code, id);		
		return Messager.getJsonMessager().success().put("has", has);		
		
	}

	
}
