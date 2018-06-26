package com.insproject.common.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.insplatform.core.http.messager.Messager;
import com.insplatform.core.utils.TextUtil;
import com.insplatform.spring.locale.LocaleTools;

@Controller
@RequestMapping("/locale")
public class LocaleController {
	
	Logger logger = Logger.getLogger(LocaleController.class);

	@Autowired
	private LocaleTools localeTools;

	@RequestMapping(value="/change/{lang}", method=RequestMethod.GET)
	public void change(@PathVariable("lang") String lang, HttpServletRequest request, HttpServletResponse response) {		
		localeTools.setLocale(request, response, this.getLangByStr(lang));		
		Messager.getTextMessager().data("success").send(response);		
	}
	
	private Locale getLangByStr(String lang) {
		if (TextUtil.isNotEmpty(lang)) {
			if ("zh_CN".equalsIgnoreCase(lang)) {
				return Locale.CHINA;
			} else if ("en_US".equalsIgnoreCase(lang)) {
				return Locale.US;
			} else {
				return Locale.CHINA;
			}
		} else {
			return Locale.CHINA;
		}
	}


}
