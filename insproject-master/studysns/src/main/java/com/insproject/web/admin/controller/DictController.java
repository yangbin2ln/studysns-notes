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
import com.insproject.provider.system.entity.Dict;
import com.insproject.provider.system.service.DictService;

@Controller
@RequestMapping("/system/dict")
public class DictController extends InsBaseController{
	
	@Autowired
	@Qualifier("DictServiceImpl")
	private DictService dictService;
	
	/**
	 * 加载数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadList")
	public @ResponseBody Map<String, Object> loadList(
			HttpServletRequest request, HttpServletResponse response){
		Condition condition = new Condition(request, "classifyId", "name");
		return dictService.loadList(condition);
	}
	
	
	/**
	 * 加载单条数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/load")
	public @ResponseBody Map<String, Object> load(@RequestParam("id") String id){		
		return Messager.getJsonMessager().success().data(dictService.load(id));		
	}
	
	/**
	 * 新增
	 * @param dict
	 * @return
	 */
	@RequestMapping("/add")	
	public @ResponseBody Map<String, Object> add(Dict dict){
		dictService.save(dict);
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
		Dict dict = dictService.get(id);
		bindObject(request, dict);
		dictService.update(dict);
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
		dictService.deleteByIds(ids);
		return Messager.getJsonMessager().success();
	}
	
	/**
	 * 根据分类名称查询数据字典(对外使用)
	 * @return
	 */
	@RequestMapping("/loadDictByClassify")
	public @ResponseBody Map<String, Object> loadDictByCategory(HttpServletRequest request,@RequestParam(value="code", required=false) String code){
		return Messager.getJsonMessager().success().data(dictService.loadDictByClassify(code));
	}

	
}
