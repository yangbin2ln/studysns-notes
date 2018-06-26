package com.insproject.provider.module.queue.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

public class  MailSendUtil { 
	 // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）, 
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "1819995223@qq.com";
    public static String myEmailPassword = "pevcjjurhwvscdfj";
    public static String myEmailSMTPHost = "smtp.qq.com";
    
    private static String sendPerson = "陕西省文物保护单位管理规划应用平台";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com

    public static void main(String[] args) throws Exception {
    	String templateFile = "E://temp//1.txt";
    	Map<String,String> dataParams = new HashMap<String,String>();
    	dataParams.put("content", "啦啦啦");
    	String content = getContentByTemplate(dataParams, templateFile);
    	System.out.println(content);
    }
    
    public static<E extends MailEntity> void sendMail(E poll) throws Exception{
    	String content = poll.getContent();
    	if(!StringUtils.isEmpty(poll.getTemplateFile())){
    		content = getContentByTemplate(poll.getDataParams(), poll.getTemplateFile());
    	}
    	
    	sendMail(poll.getReceiveMailAccounts(), poll.getTitle(), content);
    }

    private static String getContentByTemplate(Map<String, String> dataParams, String templateFile) {
    	String reader = reader(templateFile);
    	Pattern p = Pattern.compile("\\$\\{(.*?)\\}");
    	Matcher m = p.matcher(reader);
    	StringBuffer sb = new StringBuffer();		
    	while (m.find()) {
    				String value;
    				String key = m.group(1);
    				if(dataParams.containsKey(key)){
    					value = dataParams.get(key);
    					m.appendReplacement(sb, value);
    				}
    			}
    	m.appendTail(sb);// 将匹配到的最后一个字符串之后的数据拼接上
    	
		return sb.toString();
	}

    public static void sendMail(String[] receiveMailAccounts, String title, String content) throws Exception{
    	
		Transport transport = null;
		try {
			Session session = createSession();                               

			MimeMessage message = createMimeMessage(session, receiveMailAccounts, title, content);

			// 2. 根据 Session 获取邮件传输对象
			transport = session.getTransport();
 
			transport.connect(myEmailAccount, myEmailPassword);

			// 3. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());

			// 4. 关闭连接
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发送邮件失败", e);
		}finally{
			if(transport != null){
				transport.close();
			}
		}
	}

	private static Session createSession() {
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(false);
		return session;
	}

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String[] receiveMails, String title, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(myEmailAccount, sendPerson, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        List<Address> address = new ArrayList<Address>();
        for(String receiveMail: receiveMails){
        	address.add(new InternetAddress(receiveMail));
        }
//        message.setRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(myEmailAccount));
//        message.setRecipients(MimeMessage.RecipientType.TO, address.toArray(new Address[]{}));
        message.setRecipients(MimeMessage.RecipientType.TO,  address.toArray(new Address[]{}));
        /*for(String receiveMail: receiveMails){
        }*/

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(title, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
    
    public static String reader(String path) {
		SAXReader reader = new SAXReader();
		String str = null;
		try {
			Document d = reader.read(new File(path));
			Element root = d.getRootElement();
			str = root.asXML();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return str;
	}
    
}