package com.insproject.provider.module.queue.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件实体类
 * @author Administrator
 *
 */
public class MailEntity {
	
	private String[] receiveMailAccounts;//收件人地址数组
	private String title;//邮件标题
	private String content;//不使用模板是为此属性赋值

	private String templateFile;//模板地址
	private Map<String,String> dataParams = new HashMap<String,String>();//使用模板时为此对象赋值 key value形式，和模板中的参数对应
	
	
	public String[] getReceiveMailAccounts() {
		return receiveMailAccounts;
	}
	public void setReceiveMailAccounts(String[] receiveMailAccounts) {
		this.receiveMailAccounts = receiveMailAccounts;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Map<String, String> getDataParams() {
		return dataParams;
	}
	public void setDataParams(Map<String, String> dataParams) {
		this.dataParams = dataParams;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	
}
